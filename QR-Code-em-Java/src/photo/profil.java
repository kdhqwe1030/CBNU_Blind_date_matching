package photo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class profil extends JFrame {
    Container contentPane;
    JLabel imageLabel = new JLabel();

    profil() {
        setTitle("프로필 사진 업로드");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.add(imageLabel);
        createMenu();
        setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");

        // Open 메뉴아이템에 Action 리스너를 등록한다.
        openItem.addActionListener(new OpenActionListener());
        fileMenu.add(openItem);
        mb.add(fileMenu);
        this.setJMenuBar(mb);
    }

    // Open 메뉴아이템이 선택되면 호출되는 Action 리스너
    class OpenActionListener implements ActionListener {
        JFileChooser chooser;

        OpenActionListener() {
            chooser= new JFileChooser(); // 파일 다이얼로그 생성
        }
        public void actionPerformed(ActionEvent e) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG Images", // 파일 이름에 창에 출력될 문자열
                    "jpg"); // 파일 필터로 사용되는 확장자. *.jpg. *.gif만 나열됨
            chooser.setFileFilter(filter); // 파일 다이얼로그에 파일 필터 설정

            // 파일 다이얼로그 출력
            int ret = chooser.showOpenDialog(null);
            if(ret != JFileChooser.APPROVE_OPTION) { // 사용자가  창을 강제로 닫았거나 취소 버튼을 누른 경우
                JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",

                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 사용자가 파일을 선택하고 "열기" 버튼을 누른 경우
            String filePath = chooser.getSelectedFile().getPath(); // 파일 경로명을 알아온다.
//            imageLabel.setIcon(new ImageIcon(filePath)); // 파일을 로딩하여 이미지 레이블에 출력한다.
            // 이미지 아이콘 생성 및 크기 조절
            ImageIcon originalIcon = new ImageIcon(filePath);
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            // 여기서 width와 height는 원하는 이미지의 가로 및 세로 크기입니다.

            imageLabel.setIcon(new ImageIcon(resizedImage));
            pack(); // 이미지의 크기에 맞추어 프레임의 크기 조절

//            Path sourcePath = Paths.get(filePath); // 복사할 파일의 경로
//            Path destinationPath = Paths.get("src/input", "Photo.jpg"); // 복사될 위치의 경로
//
//            try {
//                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING); // 파일 복사
//                System.out.println("파일 복사가 완료되었습니다.");
//            } catch (Exception e2) {
//                System.out.println("파일 복사 중 오류가 발생하였습니다.");
//                e2.printStackTrace();
//
//                //이 창을 꺼야 생기네
//            }
            int result = JOptionPane.showConfirmDialog(null, "사진을 업로드 하시겠습니까?", "사진 업로드", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                // "Yes"를 선택한 경우
                Path sourcePath = Paths.get(filePath); // 복사할 파일의 경로
                Path destinationPath = Paths.get("QR-Code-em-java/src/input", "Photo.jpg"); // 복사될 위치의 경로
                //openLoadingForm();
                try {
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING); // 파일 복사
                    System.out.println("파일 복사가 완료되었습니다.");
                    PhotoChange photoChange = new PhotoChange();
                    photoChange.requestApi();
                    dispose();
                } catch (Exception e2) {
                    System.out.println("파일 복사 중 오류가 발생하였습니다.");
                    e2.printStackTrace();
                }




            } else if (result == JOptionPane.NO_OPTION) {
                // "No"를 선택한 경우
                System.out.println("파일 복사가 취소되었습니다.");
            }
        }
        private void openLoadingForm() {
            Loading.main(new String[]{});
            dispose();
        }
    }
    public static void main(String [] args) {new profil();}
}