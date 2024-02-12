package com.kingaspx.main;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Qrstart {
    public static String QRCODE;
    //public static void main(String[] args){
    public static synchronized String gostart(ActionListener actionListener){

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
       Menu qrcodeRead=new Menu();
        java.awt.EventQueue.invokeLater(() -> {
            qrcodeRead.setVisible(true);
        });

        String qrcode = null;

        while (true) {
            synchronized (qrcodeRead) {
                qrcode = qrcodeRead.returnQR();
            }
            if (qrcode != null){
                if (qrcode.substring(0, 2).equals("CB")) {
                    int year = Integer.parseInt(qrcode.substring(17, 21));
                    int month = Integer.parseInt(qrcode.substring(21, 23));
                    int day = Integer.parseInt(qrcode.substring(23, 25));
                    int hour = Integer.parseInt(qrcode.substring(25, 27));
                    int minute = Integer.parseInt(qrcode.substring(27, 29));

                    LocalDateTime currentDateTime = LocalDateTime.now(); // 현재 날짜와 시간

                    int minutesForTolerance = 5; // 5분 이내로 생성된 qrcode만 인식
                    LocalDateTime pastDateTime = currentDateTime.minusMinutes(minutesForTolerance);
                    LocalDateTime QRcodeDateTime = LocalDateTime.of(year, month, day, hour, minute); // qrcode가 생성된 날짜와 시간

                    if (QRcodeDateTime.isAfter(pastDateTime)){
                        break;
                    }
                }
            }
        }

        return qrcode;
    }
}