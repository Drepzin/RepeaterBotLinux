package org.example.ui;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.example.ui.functions.RunCommandFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class RunPanel extends JPanel implements NativeKeyListener {
/*
right panel, made to choose a json file to run for some time
 */
    private final RecordPanel recordPanel = new RecordPanel();

    //method to inject the principal components of the application;
    private void init(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.decode("#232222"));
        title.setFont(new Font("Lucida Console", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        this.add(Box.createVerticalStrut(165));
        this.add(title);
        this.add(directoryPanel());
        this.add(runPanel());
        this.add(Box.createVerticalStrut(150));
    }

    //directory part of the panel, to choose the files.
    private JPanel directoryPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#232222"));
        filePath.setPreferredSize(new Dimension(200, 35));
        directoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("/home/");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.showSaveDialog(null);
                File file = fileChooser.getSelectedFile();
                filePath.setText(file.getAbsolutePath());
            }
        });
        panel.add(directoryButton);
        panel.add(filePath);
        return panel;
    }

    //buttons of the panel and they functions
    private JPanel runPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#232222"));
        panel.add(runButton);
        stopRun.setPreferredSize(new Dimension(60, 30));
        stopRun.addItem(0);
        stopRun.addItem(1);
        stopRun.addItem(5);
        stopRun.addItem(10);
        stopRun.addItem(20);
        panel.add(stopRun);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = filePath.getText();
                startRunning();
                try {
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());
                    if (parentFrame != null) {
                        parentFrame.setState(JFrame.ICONIFIED); // Minimiza o JFrame
                    }
                    runCommands(path);
                }
                catch (Exception a){
                    throw new RuntimeException(a.getMessage());
                }
            }
        });
        return panel;
    }


    private void runCommands(String path){
        new Thread(() -> {
            switch ((Integer) stopRun.getSelectedItem()){
                case 0:
                    while (running){
                        RunCommandFile.runCommandFile(path);
                    }break;
                case 1:
                        RunCommandFile.runCommandFile(path);
                        break;
                case 5:
                    for (int i = 0; i < 5 && running; i++) {
                        RunCommandFile.runCommandFile(path);
                    }
                    break;

                case 10:
                    for (int i = 0; i < 10 && running; i++) {
                        RunCommandFile.runCommandFile(path);
                    }
                    break;

                case 20:
                    for (int i = 0; i < 20 && running; i++) {
                        RunCommandFile.runCommandFile(path);
                    }
                    break;
            }
        }).start();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

    }

    private void startRunning(){
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            running = true;
        }
        catch (NativeHookException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public RunPanel(){
        init();
    }

    private JLabel title = new JLabel("Executar");
    private JButton directoryButton = new JButton("diretorio");
    private JButton runButton = new JButton("Executar");
    private JComboBox<Integer> stopRun = new JComboBox<>();
    private volatile boolean running = false;
    private JTextField filePath = new JTextField();
}

