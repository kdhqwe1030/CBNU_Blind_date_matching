package Window;

import com.kingaspx.main.QrstartRunnable;

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
import photo.*;
public class JoinForm extends JDialog {
    private LoginForm owner;
    private Connection conn;
    private JLabel lblTitle;
    private JLabel lblName;
    private JLabel lblPw;
    private JLabel lblRe;
    private JLabel lblBirthday;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JLabel lblPhone;
    private JRadioButton rbtnMale;
    private JRadioButton rbtnFemale;
    private JTextField tfName;
    private JPasswordField tfPw;
    private JPasswordField tfRe;
    private JTextField tfPhone;
    private JButton btnJoin;
    private JButton btnCancel;
    String yearOfBirth = "1995", monthOfBirth = "01", dayOfBirth = "01";
    public String pass = "";
    public String passRe = "";
    public static String name = "";
    public String sex = "Male";
    public String phone = "";
    public String studentID = "0000000000";
    String qr;
    static String student_ID;

    public JoinForm(LoginForm owner, Connection conn) {
        super(owner, "Join", true);
        this.owner = owner;
        this.conn = conn;

        init();
        setDisplay();
        addListeners();
        showFrame();
    }
    private void init() {
        // 크기 고정
        int tfSize = 10;
        Dimension lblSize = new Dimension(80, 35);
        setPreferredSize(new Dimension(300, 400));
        Dimension btnSize = new Dimension(100 ,25);


        lblTitle = new JLabel("- Input your information");
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        lblName = new JLabel("Name", JLabel.LEFT);
        lblName.setPreferredSize(lblSize);
        lblPw = new JLabel("Password", JLabel.LEFT);
        lblPw.setPreferredSize(lblSize);
        lblRe = new JLabel("Retry", JLabel.LEFT);
        lblRe.setPreferredSize(lblSize);
        lblBirthday = new JLabel("Birthday", JLabel.LEFT);
        lblBirthday.setPreferredSize(lblSize);
        lblPhone = new JLabel("Phone", JLabel.LEFT);
        lblPhone.setPreferredSize(lblSize);

        yearComboBox = new JComboBox<String>(
                new String[] { "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004" });
        monthComboBox = new JComboBox<String>(
                new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
        dayComboBox = new JComboBox<String>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31" });

        tfName = new JTextField(tfSize);
        tfPw = new JPasswordField(tfSize);
        tfRe = new JPasswordField(tfSize);
        tfPhone = new JTextField(tfSize);

        rbtnMale = new JRadioButton("Male", true);
        rbtnFemale = new JRadioButton("Female");
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnMale);
        group.add(rbtnFemale);

        btnJoin = new JButton("Join");
        btnJoin.setPreferredSize(btnSize);
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(btnSize);

    }
    private void setDisplay() {
        // FlowLayout 왼쪽 정렬
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        // pnlMain(pnlMNorth / pnlMCenter / pnlMSouth)
        JPanel pnlMain = new JPanel(new BorderLayout());

        // pnlMNorth(lblTitle)
        JPanel pnlMNorth = new JPanel(flowLeft);
        pnlMNorth.add(lblTitle);

        // pnlMCenter(pnlId / pnlPw / pnlRe / pnlName / pnlNickName)
        JPanel pnlMCenter = new JPanel(new GridLayout(0, 1));

        JPanel pnlName = new JPanel(flowLeft);
        pnlName.add(lblName);
        pnlName.add(tfName);

        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.add(lblPw);
        pnlPw.add(tfPw);

        JPanel pnlRe = new JPanel(flowLeft);
        pnlRe.add(lblRe);
        pnlRe.add(tfRe);

        JPanel pnlBirthday = new JPanel(flowLeft);
        pnlBirthday.add(lblBirthday);
        pnlBirthday.add(yearComboBox);
        pnlBirthday.add(monthComboBox);
        pnlBirthday.add(dayComboBox);

        JPanel pnlPhone = new JPanel(flowLeft);
        pnlPhone.add(lblPhone);
        pnlPhone.add(tfPhone);

        pnlMCenter.add(pnlName);
        pnlMCenter.add(pnlPw);
        pnlMCenter.add(pnlRe);
        pnlMCenter.add(pnlBirthday);
        pnlMCenter.add(pnlPhone);

        // pnlMSouth(rbtnMale / rbtnFemale)
        JPanel pnlMSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlMSouth.add(rbtnMale);
        pnlMSouth.add(rbtnFemale);
        pnlMSouth.setBorder(new TitledBorder("Gender"));

        rbtnMale.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                sex = e.getActionCommand();
            }
        });
        rbtnFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                sex = e.getActionCommand();
            }
        });
        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == yearComboBox) {
                    JComboBox yearBox = (JComboBox) e.getSource();
                    yearOfBirth = (String) yearBox.getSelectedItem();
                }
            }
        });
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == monthComboBox) {
                    JComboBox monthBox = (JComboBox) e.getSource();
                    monthOfBirth = (String) monthBox.getSelectedItem();
                }
            }
        });
        dayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == dayComboBox) {
                    JComboBox dayBox = (JComboBox) e.getSource();
                    dayOfBirth = (String) dayBox.getSelectedItem();
                }
            }
        });

        // pnlMain
        pnlMain.add(pnlMNorth, BorderLayout.NORTH);
        pnlMain.add(pnlMCenter, BorderLayout.CENTER);
        pnlMain.add(pnlMSouth, BorderLayout.SOUTH);

        // pnlSouth(btnJoin / btnCancel)
        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnJoin);
        pnlSouth.add(btnCancel);

        // 화면 테두리의 간격을 주기 위해 설정 (insets 사용 가능)
        pnlMain.setBorder(new EmptyBorder(0, 20, 0, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(pnlMain, BorderLayout.NORTH);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                name = tfName.getText();
                pass = new String(tfPw.getPassword());
                passRe = new String(tfRe.getPassword());
                phone = tfPhone.getText();

                Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"); //8자 영문+특문+숫자
                Matcher passMatcher = passPattern1.matcher(pass);
                // 정보 하나라도 비어있으면
                if (isBlank()) {
                    JOptionPane.showMessageDialog(
                            JoinForm.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Pw가 조건에 맞지 않을 때
                    if (!passMatcher.find()) {
                        JOptionPane.showMessageDialog(null, "비밀번호는 영문+특수문자+숫자 8자로 구성되어야 합니다");
                    }
                    // Pw와 Re가 일치하지 않았을 때
                    else if (!String.valueOf(tfPw.getPassword()).equals(String.valueOf(tfRe.getPassword()))) {
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "Password와 Retry가 일치하지 않습니다."
                        );
                        tfPw.requestFocus();
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "<html><div style='text-align: center;'>"
                                        + "QRcode 인증을 진행합니다.<br>"
                                        + "충북대학교 어플의 모바일 학생증을 제시해주세요.<br>"
                                        + "(5분 이내로 생성된 QRcode만 가능합니다.)"
                                        + "</div></html>"
                        );


                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                final QrstartRunnable[] qrstartRunnable = {null};
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        qrstartRunnable[0] = new QrstartRunnable(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) { /* 함수 없으면 오류 뜸 */ }
                                        });
                                        qrstartRunnable[0].run();
                                        qr = qrstartRunnable[0].getQrcode();

                                        student_ID = qr.substring(7, 17);
                                        JOptionPane.showMessageDialog(
                                                JoinForm.this,
                                                "회원가입이 완료되었습니다."
                                        );
                                        openMytype();
                                        PutData();

                                        owner.setVisible(true);
                                    }
                                });
                                thread.start();
                            }
                        });
                    }
                }
            }
        });
    }
    private void openMytype() {
        new Mytype(owner, conn);
        dispose();
    }
    public boolean isBlank() {
        boolean result = false;
        if(tfName.getText().isEmpty()) {
            tfName.requestFocus();
            return true;
        }
        if(String.valueOf(tfPw.getPassword()).isEmpty()) {
            tfPw.requestFocus();
            return true;
        }
        if(String.valueOf(tfRe.getPassword()).isEmpty()) {
            tfRe.requestFocus();
            return true;
        }
        if(tfPhone.getText().isEmpty()) {
            tfPhone.requestFocus();
            return true;
        }
        return result;
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
        String sql = "insert into users(name, password, birthday, sex, phoneNumber, student_id) values (?,?,?,?,?,?)";
        try {
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);

            String date = yearOfBirth + "-" + monthOfBirth + "-" + dayOfBirth;

            pstmt.setString(1, tfName.getText());
            pstmt.setString(2, pass);
            pstmt.setString(3, date);
            pstmt.setString(4, sex);
            pstmt.setString(5, tfPhone.getText());
            pstmt.setString(6, student_ID);

            pstmt.executeUpdate(); //데이터베이스에 변경을 반영

        } catch (SQLException e1) {
            System.out.println("SQL error" + e1.getMessage());
            if (e1.getMessage().contains("PRIMARY")) {
                JOptionPane.showMessageDialog(null, "아이디 중복!", "아이디 중복 오류", 1);
            } else
                JOptionPane.showMessageDialog(null, "정보를 제대로 입력해주세요!", "오류", 1);
        } // try ,catch
    }
}