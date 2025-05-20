package org.example.ui;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StopButtonWindow extends JFrame {

    private void init(){
        this.setVisible(true);
        this.setSize(new Dimension(150, 150));
        this.setFocusableWindowState(false);
        this.setLocation(1400, 10);
        this.setCursor(Cursor.HAND_CURSOR);
        JButton stopButton = new JButton();
        stopButton.setBackground(Color.RED);
        stopButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
        this.add(stopButton);
    }

    public StopButtonWindow(){
        init();
    }
}
