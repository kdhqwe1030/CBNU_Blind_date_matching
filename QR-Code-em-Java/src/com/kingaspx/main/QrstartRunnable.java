package com.kingaspx.main;

import java.awt.event.ActionListener;

public class QrstartRunnable implements Runnable {
    private ActionListener actionListener;
    private String qrcode;
    public QrstartRunnable(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void run() {
        qrcode = Qrstart.gostart(actionListener);
    }

    // qrcode 값을 반환하는 메서드 추가
    public String getQrcode() {
        return qrcode;
    }
}