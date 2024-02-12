package Window;

import Chatting.ChatClientObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loading extends JFrame {
    private JLabel lblLd;
    private JButton btnNext;

    public Loading() {

        Dimension lblSize = new Dimension(300, 200);
        setSize(new Dimension(300, 260));
        setIconImage(Toolkit.getDefaultToolkit().getImage("heart.jpg"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        ImageIcon img = new ImageIcon(new ImageIcon("logo.jpg").getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH));

        JLabel lblId = new JLabel(img);
        lblId.setHorizontalAlignment(SwingConstants.CENTER);
        lblId.setPreferredSize(lblSize);
        lblId.revalidate();
        lblId.repaint();
        JLabel lblText = new JLabel("                                          매칭 중...");
        lblText.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lblId);
        panel.add(lblText);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        //setVisible(true);

        setTitle("Loading");
        //pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        openChatting();
    }


    public void openChatting() {
        dispose();
        ChatClientObject.main(new String[]{});
    }



    public static void main(String[] args) {
        new Loading();


    }
}