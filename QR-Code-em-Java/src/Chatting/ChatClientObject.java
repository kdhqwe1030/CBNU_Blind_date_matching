package Chatting;

import Window.LoginForm;
import Window.Home;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class ChatClientObject extends JFrame implements ActionListener, Runnable {
    JScrollPane chatScrollPane;
    private final JTextPane output;
    private final JTextField input;
    private final JButton sendBtn;
    private Socket socket;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
    private String nickName;
    public static String url="https://ai-result-rapidapi.ailabtools.com/image/generateCartoonizedImage/2023-12-20/002510-13cb05ea-7443-1c99-8412-7d79d92f4676-1703031910.jpg";
    //private final JTextArea output;
    public String user1_url="";
    public String user2_url="";

    //public static void appointed_url(String url){this.url=url;}
    public ChatClientObject() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
        JPanel chatBoardPane = new JPanel();
        chatBoardPane.setBackground(Color.PINK);
        chatBoardPane.setBounds(0, 0, 300, 440);
        add(chatBoardPane);
        chatBoardPane.setLayout(null);

        JButton lblUserList = new JButton("Profile");
        JLabel leftLabel = new JLabel();
        JLabel rightLabel = new JLabel();
        lblUserList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 팝업창 생성
                JDialog dialog = new JDialog();
                dialog.setLayout(new BorderLayout());
                dialog.setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
                dialog.setTitle("프로필 확인");
                try {
                    URL url1 = new URL(user1_url); // 새로운 URL 객체를 생성합니다.
                    Image image1 = new ImageIcon(url1).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                    ImageIcon newIcon1 = new ImageIcon(image1);
                    leftLabel.setIcon(newIcon1); // 라벨에 새로운 이미지 아이콘을 설정합니다.
                    if(user2_url!=""){
                        URL url2 = new URL(user2_url);
                        Image image2 = new ImageIcon(url2).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);

                        ImageIcon newIcon2 = new ImageIcon(image2);
                        rightLabel.setIcon(newIcon2);
                    }


                } catch (MalformedURLException e12) {
                    e12.printStackTrace();
                }

                System.out.println(user1_url);
                leftLabel.revalidate();
                leftLabel.repaint();
                rightLabel.revalidate();
                rightLabel.repaint();

                // 라벨을 팝업창에 추가
                dialog.add(leftLabel, BorderLayout.WEST);
                dialog.add(rightLabel, BorderLayout.EAST);

                // 팝업창 크기 설정
                dialog.setSize(500, 400);
                // 팝업창이 화면 중앙에 위치하도록 설정
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        lblUserList.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        lblUserList.setBackground(Color.PINK);
        lblUserList.setBounds(12, 0, 80, 40);
        chatBoardPane.add(lblUserList);



        output = new JTextPane();
        output.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        output.setBackground(Color.PINK);
        output.setText("");
        chatScrollPane = new JScrollPane();
        chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setBounds(0, 45, 300, 395);
        chatBoardPane.add(chatScrollPane);
        chatScrollPane.setViewportView(output);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 450, 189, 70);
        add(scrollPane);



        input = new JTextField();
        scrollPane.setViewportView(input);
        sendBtn = new JButton("전송");
        sendBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        sendBtn.setBackground(Color.ORANGE);
        sendBtn.setBounds(211, 450, 65, 35);
        add(sendBtn);
        JLabel lblImage = new JLabel(new ImageIcon("images/image.png"));
        lblImage.setBounds(211, 490, 30, 30);
        add(lblImage);

        setBounds(400, 100, 300, 570);
        //setVisible(true);

        // 아래 부분을 추가하여 "x" 버튼 클릭 시 Home 화면을 열도록 설정
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    InfoDTO dto = new InfoDTO();
                    dto.setNickName(nickName);
                    dto.setCommand(Info.EXIT);
                    writer.writeObject(dto);
                    writer.flush();
                } catch (IOException io) {
                    io.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> {
                    Home home = new Home();
                    home.setVisible(true);
                });
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        //new ChatClientObject().service();
        SwingUtilities.invokeLater(() -> {
            new ChatClientObject().service();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String msg = input.getText();
            InfoDTO dto = new InfoDTO();
            if (msg.equals("exit")) {
                dto.setCommand(Info.EXIT);
            } else {
                dto.setCommand(Info.SEND);
                dto.setMessage(msg);
                dto.setNickName(nickName);
            }
            writer.writeObject(dto);
            writer.flush();
            input.setText("");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void service() {
        String serverIP = "192.168.1.138";
        nickName = "김도현";
        try {
            socket = new Socket(serverIP, 9500);
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("전송 준비 완료!");
        } catch (UnknownHostException e) {
            System.out.println("서버를 찾을 수 없습니다.");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("서버와 연결이 안되었습니다.");
            e.printStackTrace();
            System.exit(0);
        }
        try {
            InfoDTO dto = new InfoDTO();
            dto.setCommand(Info.JOIN);
            dto.setNickName(nickName);
            dto.setURL(url);
            writer.writeObject(dto);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(this);
        t.start();
        input.addActionListener(this);
        sendBtn.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void run() {
        InfoDTO dto = null;
        while (true) {
            try {
                if (socket.isClosed()) {
                    break; // Socket이 닫혀있으면 루프를 종료
                }
                dto = (InfoDTO) reader.readObject();
                if (dto.getCommand() == Info.EXIT) {
                    reader.close();
                    writer.close();
                    socket.close();
                    //System.exit(0);
                } else if (dto.getCommand() == Info.SEND) {
                    SimpleAttributeSet set = new SimpleAttributeSet();
                    StyleConstants.setBackground(set, Color.WHITE);
                    StyledDocument doc = output.getStyledDocument();


                    String str = dto.getMessage() ;
                    System.out.println(str);
                    String Message="";

                    int beginIndex = 1;
                    beginIndex = str.indexOf("@");

                    int endIndex = str.length();
                    if(beginIndex==-1){beginIndex=0;}

                    String image_url = str.substring(0, beginIndex);
                    Message = str.substring(beginIndex+1, endIndex);
                    receiveMessageFromServer(image_url);
                    System.out.println("\n분리된 url : "+image_url);
                    if(user1_url==""){user1_url=image_url;}//user1url이 비어있다면<-처음으로 들어온사람
                    else{if(user2_url==""){//user2url이 비어있다면?<-2번째로 들어온사람
                        if(user1_url!=image_url){user2_url=image_url;}}}//<-첫번째사람과 url이 다르면 입력


                    try {
                        doc.insertString(doc.getLength(), Message + "\n\n", set);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    int pos = output.getDocument().getLength();
                    output.setCaretPosition(pos);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void receiveMessageFromServer(String imageUrl) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(imageUrl);
                    ImageIcon imageIcon = new ImageIcon(url);
                    JLabel profilLabel=new JLabel();
                    profilLabel.setIcon(imageIcon);
                    profilLabel.revalidate();
                    profilLabel.repaint();
                    output.add(profilLabel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
}}
