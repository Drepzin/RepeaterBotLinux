package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public JPanel firstPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel title = new JLabel("gravar");
        title.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
        title.setForeground(Color.BLACK);
        panel.add(title);
        panel.setBackground(Color.decode("#dfd9d6"));
        panel.add(new Button("click"));
        return panel;
    }


    public void init(){
        this.add(new RecordPanel());
        this.add(new RunPanel());
        this.setLayout(new GridLayout());
        StopButtonWindow stopButtonWindow = new StopButtonWindow();
    }

    public MainPanel(){
        init();
    }
}
