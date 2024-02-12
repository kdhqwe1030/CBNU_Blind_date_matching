package photo;

import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import Window.LoginForm;
public class Loading extends JFrame {
    private JLabel lblLd;
    private JButton btnNext;

    public Loading() {
        init();
        showFrame();
    }

    public void init() {
        // 사이즈 통합
        Dimension lblSize = new Dimension(500, 500);
        setSize(new Dimension(300, 260));
        setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        ImageIcon img = new ImageIcon(new ImageIcon("logo.jpg").getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH));

        JLabel lblId = new JLabel(img);
        lblId.setHorizontalAlignment(SwingConstants.CENTER);
        lblId.setPreferredSize(lblSize);

        JLabel lblText = new JLabel("                          프로필 사진 변환 중...");
        lblText.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lblId);
        panel.add(lblText);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setVisible(true);
        // 파일 감시 기능을 별도의 스레드에서 실행
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    WatchService watchService = FileSystems.getDefault().newWatchService();

                    Path path = Paths.get("QR-Code-em-java/src/output"); // 감시할 디렉토리 경로
                    path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                    while (true) {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                                System.out.println("새로운 파일이 추가되었습니다: " + event.context());
                                //dispose(); // 새 파일이 추가되었으므로 로딩 화면 종료

                                openLoginForm();
                                break;

                            }
                        }
                        boolean valid = key.reset();
                        if (!valid) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //openLoginForm();






    }

    private void openLoginForm() {
        // LoginForm 클래스를 실행하는 코드 추가
        dispose();
        LoginForm.main(new String[]{});

    }
    public void showFrame() {
        setTitle("Loading");
        //pack();
        Dimension lblSize = new Dimension(500, 500);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        //setVisible(true);
    }

    public static void main(String[] args) {
        new Loading();
    }
}