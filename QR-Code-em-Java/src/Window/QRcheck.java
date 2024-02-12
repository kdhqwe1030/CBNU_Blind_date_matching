package Window;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QRcheck extends JFrame {

    private JLabel lblId;
    private JButton btnStart;

    public QRcheck() {
        init();
        //showFrame();
    }

    public void init() {
        // 사이즈 통합
        /*
        Dimension lblSize = new Dimension(200, 200);
        setSize(new Dimension(338, 388));
        int tfSize = 20;
        Dimension btnSize = new Dimension(100, 25);

        lblId = new JLabel("안녕하세요");
        lblId.setHorizontalAlignment(SwingConstants.CENTER);
        lblId.setPreferredSize(lblSize);

        btnStart = new JButton("시작하기");
        btnStart.setPreferredSize(btnSize);

        // btnStart 버튼에 ActionListener 추가
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 버튼이 클릭되었을 때 실행할 코드
                openLoginForm();
            }
        });

        // 생성한 컴포넌트들을 프레임에 추가
        setLayout(new FlowLayout()); // 레이아웃 매니저 설정
        add(lblId);
        add(btnStart); */
    }

    // User 클래스 실행 메서드
    private void openLoginForm() {
        // LoginForm 클래스를 실행하는 코드 추가
        LoginForm.main(new String[]{});
        dispose();
    }

    public void showFrame() {
        setTitle("OpenSW Project");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new QRcheck();
    }
}