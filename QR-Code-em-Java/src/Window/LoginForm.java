package Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginForm extends JFrame {
    private Connection conn;
    private JLabel lblId;
    private JLabel lblPw;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnJoin;
    public static String username;

    public LoginForm() {
        icon = new ImageIcon("login.jpg");


        JPanel background = new JPanel() {
            public void paintComponent(Graphics g) {
                // Approach 1: Dispaly image at at full size
                g.drawImage(icon.getImage(), 0, 0, null);

                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };


        int tfSize = 10;
        Dimension lblSize = new Dimension(120, 150);
        setPreferredSize(new Dimension(600, 600));
        Dimension btnSize = new Dimension(100, 25);

        lblId = new JLabel("Student ID");
        lblId.setPreferredSize(lblSize);
        lblPw = new JLabel("Password");
        lblPw.setPreferredSize(lblSize);

        tfId = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);

        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(btnSize);
        btnJoin = new JButton("Join");
        btnJoin.setPreferredSize(btnSize);
        setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
        background.add(lblId);
        background.add(lblPw);
        background.add(tfId);
        background.add(tfPw);
        background.add(btnLogin);
        background.add(btnJoin);

        scrollPane = new JScrollPane(background);
        setContentPane(scrollPane);
        try {
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.49:3306/example", "tester", "4216english");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 연결에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
        background.setLayout(new BorderLayout());
        FlowLayout flowLeft = new FlowLayout(FlowLayout.CENTER);

        // pnlNorth(pnlId, pnlPw)
        JPanel pnlNorth = new JPanel(new GridLayout(0, 1,0,-120));
        pnlNorth.setOpaque(false);

        JPanel pnlId = new JPanel(flowLeft);
        pnlId.setOpaque(false);
        pnlId.add(lblId);
        pnlId.add(tfId);

        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.setOpaque(false);
        pnlPw.add(lblPw);
        pnlPw.add(tfPw);

        pnlNorth.add(pnlId);
        pnlNorth.add(pnlPw);
        pnlNorth.setOpaque(false);
        JPanel pnlSouth = new JPanel();
        pnlSouth.setOpaque(false);
        pnlSouth.add(btnLogin);
        pnlSouth.add(btnJoin);

        pnlNorth.setBorder(new EmptyBorder(350, 20, -30, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 50, 0));


        background.add(pnlSouth, BorderLayout.SOUTH);
        background.add(pnlNorth, BorderLayout.CENTER);

        addListeners();
    }
    JScrollPane scrollPane;
    ImageIcon icon;
    private void openJoinForm() {
        try {
            // 데이터베이스 연결 설정
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.49:3306/example", "tester", "4216english");

            // JoinForm 객체를 생성할 때 Connection 객체를 전달
            JoinForm joinForm = new JoinForm(this, conn);

            // 창을 닫을 때 데이터베이스 연결을 종료하도록 설정
            joinForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 연결에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
    public String getTfId() {
        return tfId.getText();
    }

    public void addListeners() {

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new JoinForm(LoginForm.this, conn);
                //new Mytype(LoginForm.this, conn);
                tfId.setText("");
                tfPw.setText("");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredId = getTfId();
                String enteredPw = String.valueOf(tfPw.getPassword());
                try {
                    // 데이터베이스에서 사용자 정보 가져오기
                    String query = "SELECT * FROM example.users WHERE student_id = ? AND password = ?";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                        preparedStatement.setString(1, enteredId);
                        preparedStatement.setString(2, enteredPw);

                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                username = resultSet.getString("name");
                                JOptionPane.showMessageDialog(
                                        LoginForm.this,
                                        "로그인에 성공하였습니다."
                                );
                                openHome();
                                dispose();
                            } else {
                                // 아이디 또는 비밀번호가 일치하지 않음
                                JOptionPane.showMessageDialog(
                                        LoginForm.this,
                                        "아이디 또는 비밀번호가 일치하지 않습니다.",
                                        "로그인폼",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            LoginForm.this,
                            "데이터베이스 오류",
                            "로그인폼",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                int choice = JOptionPane.showConfirmDialog(
                        LoginForm.this,
                        "로그인 프로그램을 종료합니다.",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }



    private void openHome() {
        Home homeForm = new Home();
        homeForm.setVisible(true);
    }

    public static void main(String[] args) {

        LoginForm frame = new LoginForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 645);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Cb&u");
    }
}
//
//package Window;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//
//public class LoginForm extends JFrame {
//    private Connection conn;
//    private JLabel lblId;
//    private JLabel lblPw;
//    private JTextField tfId;
//    private JPasswordField tfPw;
//    private JButton btnLogin;
//    private JButton btnJoin;
//    public static String username;
//
//    public LoginForm() {
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.49:3306/example", "tester", "4216english");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "데이터베이스 연결에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//        }
//
//        init();
//        setDisplay();
//        addListeners();
//        showFrame();
//    }
//
//    public void init() {
//        // 사이즈 통합
//        int tfSize = 10;
//        Dimension lblSize = new Dimension(120, 150);
//        setPreferredSize(new Dimension(300, 400));
//        Dimension btnSize = new Dimension(100, 25);
//
//        lblId = new JLabel("Student ID");
//        lblId.setPreferredSize(lblSize);
//        lblPw = new JLabel("Password");
//        lblPw.setPreferredSize(lblSize);
//
//        tfId = new JTextField(tfSize);
//        tfPw = new JPasswordField(tfSize);
//
//        btnLogin = new JButton("Login");
//        btnLogin.setPreferredSize(btnSize);
//        btnJoin = new JButton("Join");
//        btnJoin.setPreferredSize(btnSize);
//        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\dmxth\\Desktop\\Synthesized(Revising)\\QR-Code-em-Java\\src\\Window\\photos\\heart.jpg"));
//    }
//    private void openJoinForm() {
//        try {
//            // 데이터베이스 연결 설정
//            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.49:3306/example", "tester", "4216english");
//
//            // JoinForm 객체를 생성할 때 Connection 객체를 전달
//            JoinForm joinForm = new JoinForm(this, conn);
//
//            // 창을 닫을 때 데이터베이스 연결을 종료하도록 설정
//            joinForm.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    try {
//                        if (conn != null && !conn.isClosed()) {
//                            conn.close();
//                        }
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            });
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "데이터베이스 연결에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//    public String getTfId() {
//        return tfId.getText();
//    }
//    public void setDisplay() {
//        // FlowLayout 왼쪽 정렬
//        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);
//
//        // pnlNorth(pnlId, pnlPw)
//        JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
//
//        JPanel pnlId = new JPanel(flowLeft);
//        pnlId.add(lblId);
//        pnlId.add(tfId);
//
//        JPanel pnlPw = new JPanel(flowLeft);
//        pnlPw.add(lblPw);
//        pnlPw.add(tfPw);
//
//        pnlNorth.add(pnlId);
//        pnlNorth.add(pnlPw);
//
//        JPanel pnlSouth = new JPanel();
//        pnlSouth.add(btnLogin);
//        pnlSouth.add(btnJoin);
//
//        pnlNorth.setBorder(new EmptyBorder(0, 20, 0, 20));
//        pnlSouth.setBorder(new EmptyBorder(0, 0, 10, 0));
//
//        add(pnlNorth, BorderLayout.NORTH);
//        add(pnlSouth, BorderLayout.SOUTH);
//    }
//
//    public void addListeners() {
//
//        btnJoin.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setVisible(false);
//                new JoinForm(LoginForm.this, conn);
//                //new Mytype(LoginForm.this, conn);
//                tfId.setText("");
//                tfPw.setText("");
//            }
//        });
//        btnLogin.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String enteredId = getTfId();
//                String enteredPw = String.valueOf(tfPw.getPassword());
//                try {
//                    // 데이터베이스에서 사용자 정보 가져오기
//                    String query = "SELECT * FROM example.users WHERE student_id = ? AND password = ?";
//                    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//                        preparedStatement.setString(1, enteredId);
//                        preparedStatement.setString(2, enteredPw);
//
//                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                            if (resultSet.next()) {
//                                username = resultSet.getString("name");
//                                JOptionPane.showMessageDialog(
//                                        LoginForm.this,
//                                        "로그인에 성공하였습니다."
//                                );
//                                openHome();
//                                dispose();
//                            } else {
//                                // 아이디 또는 비밀번호가 일치하지 않음
//                                JOptionPane.showMessageDialog(
//                                        LoginForm.this,
//                                        "아이디 또는 비밀번호가 일치하지 않습니다.",
//                                        "로그인폼",
//                                        JOptionPane.WARNING_MESSAGE);
//                            }
//                        }
//                    }
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                    JOptionPane.showMessageDialog(
//                            LoginForm.this,
//                            "데이터베이스 오류",
//                            "로그인폼",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent we) {
//                try {
//                    if (conn != null && !conn.isClosed()) {
//                        conn.close();
//                    }
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//                int choice = JOptionPane.showConfirmDialog(
//                        LoginForm.this,
//                        "로그인 프로그램을 종료합니다.",
//                        "종료",
//                        JOptionPane.OK_CANCEL_OPTION,
//                        JOptionPane.WARNING_MESSAGE
//                );
//                if (choice == JOptionPane.YES_OPTION) {
//                    System.exit(0);
//                }
//            }
//        });
//    }
//
//    public void showFrame() {
//        setTitle("Cb&u");
//        pack();
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        setResizable(false);
//        setVisible(true);
//    }
//
//    private void openHome() {
//        Home homeForm = new Home();
//        homeForm.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        new LoginForm();
//    }
//}