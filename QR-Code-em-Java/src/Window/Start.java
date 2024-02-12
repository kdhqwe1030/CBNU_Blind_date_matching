package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Start extends JFrame {

    public JLabel lbllogo;
    public JLabel lblphoto;
    public JButton btnStart;

    public Start() {
        init();
        showFrame();
    }

    public void init() {
        setPreferredSize(new Dimension(300, 400));

        // 이미지 아이콘 생성
        ImageIcon originalIcon = createImageIcon("StartPhoto.jpg");
        ImageIcon resizedIcon = resizeImageIcon(originalIcon, 300, 400);

        // 이미지를 표시할 JLabel 생성
        lblphoto = new JLabel(resizedIcon);
        lblphoto.setHorizontalAlignment(SwingConstants.CENTER); //없애도 변경사항 x
        lblphoto.setBounds(0, 0, 300, 400);


        // null 레이아웃 설정
        setLayout(null);
        Font lineSeedFont = new Font("LINE Seed Sans KR Regular", Font.BOLD, 12);
        btnStart = new JButton("시작하기");
        btnStart.setFont(lineSeedFont);
        btnStart.setBounds(95, 275, 100, 25);  // 높이를 40으로 변경
        btnStart.setHorizontalAlignment(SwingConstants.CENTER);
        btnStart.setVerticalAlignment(SwingConstants.CENTER);
        btnStart.setBackground(new Color(211, 208, 200));
        btnStart.setBorderPainted(false);
        btnStart.setFocusPainted(false);

        Insets margin = new Insets(15, 10, 10, 10); // 상, 좌, 하, 우 여백 조절
        btnStart.setMargin(margin);

        // btnStart 버튼에 ActionListener 추가
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 버튼이 클릭되었을 때 실행할 코드
                openLoginForm();
            }
        });

        // 생성한 컴포넌트들을 프레임에 추가
        add(btnStart);
        add(lblphoto);
//"LINE Seed Sans KR Regular"
        setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
    }

    private void openLoginForm() {
        // LoginForm 클래스를 실행하는 코드 추가
        LoginForm.main(new String[]{});
        dispose();
    }

    public void showFrame() {
        setTitle("Cb&u");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setVisible(true); //이부분 안보이게 나중에
    }

    public static void main(String[] args) {
        new Start();
    }

    // 이미지 아이콘 생성 메서드
    protected ImageIcon createImageIcon(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            return new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}