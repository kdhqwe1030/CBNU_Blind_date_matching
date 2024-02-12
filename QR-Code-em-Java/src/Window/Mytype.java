package Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mytype extends JDialog {
    private LoginForm owner;
    private Connection conn;
    private JCheckBox MBTI1, MBTI2, MBTI3, MBTI4, MBTI5, MBTI6, MBTI7, MBTI8, MBTI9, MBTI10, MBTI11, MBTI12, MBTI13, MBTI14, MBTI15, MBTI16;
    private JCheckBox Personality1, Personality2, Personality3, Personality4, Personality5, Personality6, Personality7, Personality8;
    private JButton btnEnd;
    private JLabel lblTitle;

    private int p1, p2, p3, p4, p5, p6, p7, p8;
    private int m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16 = 0;

    public Mytype(LoginForm owner, Connection conn) {
        super(owner, "MyType", true);
        this.owner = owner;
        this.conn = conn;

        init();
        setDisplay();
        addListeners();
        showFrame();
    }
    private void init() {
        setPreferredSize(new Dimension(300, 400));
        Dimension btnMBTISize = new Dimension(60 ,25);
        Dimension btnPersonalitySize = new Dimension(90 ,25);

        MBTI1 = new JCheckBox("esfp");
        MBTI1.setBounds(100,100,150,20);
        MBTI2 = new JCheckBox("esfj");
        MBTI2.setBounds(100,100,150,20);
        MBTI3 = new JCheckBox("estp");
        MBTI3.setBounds(100,100,150,20);
        MBTI4 = new JCheckBox("estj");
        MBTI4.setBounds(100,100,150,20);
        MBTI5 = new JCheckBox("enfp");
        MBTI5.setBounds(100,100,150,20);
        MBTI6 = new JCheckBox("enfj");
        MBTI6.setBounds(100,100,150,20);
        MBTI7 = new JCheckBox("entp");
        MBTI7.setBounds(100,100,150,20);
        MBTI8 = new JCheckBox("entj");
        MBTI8.setBounds(100,100,150,20);
        MBTI9 = new JCheckBox("isfp");
        MBTI9.setBounds(100,100,150,20);
        MBTI10 = new JCheckBox("isfj");
        MBTI10.setBounds(100,100,150,20);
        MBTI11 = new JCheckBox("istp");
        MBTI11.setBounds(100,100,150,20);
        MBTI12 = new JCheckBox("istj");
        MBTI12.setBounds(100,100,150,20);
        MBTI13 = new JCheckBox("infp");
        MBTI13.setBounds(100,100,150,20);
        MBTI14 = new JCheckBox("infj");
        MBTI14.setBounds(100,100,150,20);
        MBTI15 = new JCheckBox("intp");
        MBTI15.setBounds(100,100,150,20);
        MBTI16 = new JCheckBox("intj");
        MBTI16.setBounds(100,100,150,20);

        Personality1 = new JCheckBox("재미있는");
        Personality1.setBounds(100,100,150,20);
        Personality2 = new JCheckBox("애교많은");
        Personality2.setBounds(100,100,150,20);
        Personality3 = new JCheckBox("침착한");
        Personality3.setBounds(100,100,150,20);
        Personality4 = new JCheckBox("긍정적인");
        Personality4.setBounds(100,100,150,20);
        Personality5 = new JCheckBox("조용한");
        Personality5.setBounds(100,100,150,20);
        Personality6 = new JCheckBox("헌신적인");
        Personality6.setBounds(100,100,150,20);
        Personality7 = new JCheckBox("겸손한");
        Personality7.setBounds(100,100,150,20);
        Personality8 = new JCheckBox("배려깊은");
        Personality8.setBounds(100,100,150,20);

        btnEnd = new JButton("완료");
        btnEnd.setPreferredSize(btnMBTISize);
    }
    private void setDisplay() {
        JPanel pnlMain = new JPanel(new FlowLayout(FlowLayout.CENTER));


        lblTitle = new JLabel("당신은 어떤 사람인가요?");
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        JPanel pnlMNorth = new JPanel();
        pnlMNorth.add(lblTitle);

        pnlMain.add(MBTI1);
        pnlMain.add(MBTI2);
        pnlMain.add(MBTI3);
        pnlMain.add(MBTI4);
        pnlMain.add(MBTI5);
        pnlMain.add(MBTI6);
        pnlMain.add(MBTI7);
        pnlMain.add(MBTI8);
        pnlMain.add(MBTI9);
        pnlMain.add(MBTI10);
        pnlMain.add(MBTI11);
        pnlMain.add(MBTI12);
        pnlMain.add(MBTI13);
        pnlMain.add(MBTI14);
        pnlMain.add(MBTI15);
        pnlMain.add(MBTI16);
        pnlMain.add(Personality1);
        pnlMain.add(Personality2);
        pnlMain.add(Personality3);
        pnlMain.add(Personality4);
        pnlMain.add(Personality5);
        pnlMain.add(Personality6);
        pnlMain.add(Personality7);
        pnlMain.add(Personality8);
        pnlMain.add(btnEnd);

        // 화면 테두리의 간격을 주기 위해 설정 (insets 사용 가능)
        pnlMain.setBorder(new EmptyBorder(0, 20, 0, 20));

        add(pnlMain, BorderLayout.CENTER);
    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 체크박스의 상태를 확인하여 값을 1로 바꿈
                m1 = MBTI1.isSelected() ? 1 : 0;
                m2 = MBTI2.isSelected() ? 1 : 0;
                m3 = MBTI3.isSelected() ? 1 : 0;
                m4 = MBTI4.isSelected() ? 1 : 0;
                m5 = MBTI5.isSelected() ? 1 : 0;
                m6 = MBTI6.isSelected() ? 1 : 0;
                m7 = MBTI7.isSelected() ? 1 : 0;
                m8 = MBTI8.isSelected() ? 1 : 0;
                m9 = MBTI9.isSelected() ? 1 : 0;
                m10 = MBTI10.isSelected() ? 1 : 0;
                m11 = MBTI11.isSelected() ? 1 : 0;
                m12 = MBTI12.isSelected() ? 1 : 0;
                m13 = MBTI13.isSelected() ? 1 : 0;
                m14 = MBTI14.isSelected() ? 1 : 0;
                m15 = MBTI15.isSelected() ? 1 : 0;
                m16 = MBTI16.isSelected() ? 1 : 0;

                p1 = Personality1.isSelected() ? 1 : 0;
                p2 = Personality2.isSelected() ? 1 : 0;
                p3 = Personality3.isSelected() ? 1 : 0;
                p4 = Personality4.isSelected() ? 1 : 0;
                p5 = Personality5.isSelected() ? 1 : 0;
                p6 = Personality6.isSelected() ? 1 : 0;
                p7 = Personality7.isSelected() ? 1 : 0;
                p8 = Personality8.isSelected() ? 1 : 0;

                // PutData 메소드 호출
                PutData();
                dispose();
                //new Preference(owner, conn);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Preference(owner, conn);
                    }
                });
            }
        });
    }

    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public static Connection getConnection() throws SQLException {
        Connection conn = null;

        conn = DriverManager.getConnection("jdbc:mysql://192.168.1.49:3306/example", "tester", "4216english");

        return conn;
    }

    public void PutData(){
        // DB에 데이터 저장
        String sql = "insert into users_info(student_ID, esfp, esfj, estp, estj, enfp, enfj, entp, entj, isfp, isfj, istp, istj, infp, infj, intp, intj, p1, p2, p3, p4, p5, p6, p7, p8) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            //pstmt.setString(1, JoinForm.student_ID);
            pstmt.setString(1, JoinForm.student_ID);
            pstmt.setInt(2, m1);
            pstmt.setInt(3, m2);
            pstmt.setInt(4, m3);
            pstmt.setInt(5, m4);
            pstmt.setInt(6, m5);
            pstmt.setInt(7, m6);
            pstmt.setInt(8, m7);
            pstmt.setInt(9, m8);
            pstmt.setInt(10, m9);
            pstmt.setInt(11, m10);
            pstmt.setInt(12, m11);
            pstmt.setInt(13, m12);
            pstmt.setInt(14, m13);
            pstmt.setInt(15, m14);
            pstmt.setInt(16, m15);
            pstmt.setInt(17, m16);
            pstmt.setInt(18, p1);
            pstmt.setInt(19, p2);
            pstmt.setInt(20, p3);
            pstmt.setInt(21, p4);
            pstmt.setInt(22, p5);
            pstmt.setInt(23, p6);
            pstmt.setInt(24, p7);
            pstmt.setInt(25, p8);

            pstmt.executeUpdate(); //데이터베이스에 변경을 반영

        } catch (SQLException e1) {
            System.out.println("SQL error" + e1.getMessage());
        } // try ,catch
    }
}