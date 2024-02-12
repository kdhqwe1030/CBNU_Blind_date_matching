package Window;

import Chatting.ChatClientObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Home extends JFrame {
    private JButton btnRandom;
    private JButton btnOptional;

    public Home() {
        init();
        showFrame();
    }

    public void init() {

        setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
        btnRandom = new JButton("        랜덤 매칭      ");
        btnRandom.setBackground(Color.PINK);

        btnRandom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        btnOptional = new JButton("   이상형 매칭     ");
        btnOptional.setBackground(Color.YELLOW);

        btnOptional.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // btnRandom 버튼에 ActionListener 추가
        btnRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 버튼이 클릭되었을 때 실행할 코드
                JOptionPane.showMessageDialog(
                        Home.this,
                        "랜덤 매칭을 시작합니다."
                );
                openLoading();
            }
        });
        // btnOptional 버튼에 ActionListener 추가
        btnOptional.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 버튼이 클릭되었을 때 실행할 코드
                JOptionPane.showMessageDialog(
                        Home.this,
                        "이상형 매칭을 시작합니다."
                );
                openLoading();
            }
        });

        // 생성한 컴포넌트들을 프레임에 추가
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(btnRandom);
        getContentPane().add(btnOptional);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                        Home.this,
                        "프로그램을 종료합니다.",
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

    private void openLoading() {
        //setVisible(false);
        dispose();
        //new Loading();
        ChatClientObject.main(new String[]{});


    }

    public void showFrame() {
        setTitle("매칭 선택");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Home();
    }
}