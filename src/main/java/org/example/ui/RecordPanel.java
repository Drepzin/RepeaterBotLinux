package org.example.ui;

import com.github.kwhat.jnativehook.GlobalScreen;
import org.example.ui.functions.CommandListener;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecordPanel extends JPanel {

//panel destined to record the inputs of the user, like mouse and keyboard inputs

    private CommandListener commandListener = new CommandListener();

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.decode("#252525"));

        title.setFont(new Font("Lucida Console", Font.BOLD, 30));
        title.setForeground(Color.decode("#951919"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());
        this.add(Box.createVerticalStrut(150));
        this.add(title);
        this.add(Box.createVerticalStrut(10));
        this.add(subpanelDirectory());
        this.add(fileNamePanel());
        this.add(subpanelRun());
        this.add(Box.createVerticalGlue());
        this.add(Box.createVerticalStrut(150));
    }

    //panel to choose directory of the file
    private JPanel subpanelDirectory(){
        JPanel subPanel = new JPanel();
        subPanel.setBackground(Color.decode("#252525"));
        directory.setPreferredSize(new Dimension(200, 30));
        directoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser("/home");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.showSaveDialog(null);
                    File file = fileChooser.getSelectedFile();
                    directory.setText(file.getAbsolutePath());
                }
        });
        subPanel.add(directory);
        subPanel.add(directoryButton);
        return subPanel;
    }
//panel to the bottom buttons
    private JPanel subpanelRun(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#252525"));
        stopRecording.setPreferredSize(new Dimension(40, 30));
        ((AbstractDocument) stopRecording.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    if (string != null && fb.getDocument().getLength() + string.length() <= 1) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    if (text != null && fb.getDocument().getLength() - length + text.length() <= 1) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
        panel.add(recordButton);
        panel.add(stopRecording);
        recordButton.addActionListener(new ActionListener() {
            //init the recording if everything is alright
            @Override
            public void actionPerformed(ActionEvent e) {
                if (directory.getText() == null || directory.getText().trim().isEmpty() ||
                        fileName.getText() == null || fileName.getText().trim().isEmpty()
                || stopRecording.getText() == null || stopRecording.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Campos necessarios vazios cuidado!",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    try{
                        //minimize the app in the screen
                        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());
                        if (parentFrame != null) {
                            parentFrame.setState(JFrame.ICONIFIED); // Minimiza o JFrame
                        }
                        String path = directory.getText() + "/" + fileName.getText();
                        commandListener.setStopButton(stopRecording.getText());
                        commandListener.setPath(path);
                        GlobalScreen.registerNativeHook();
                        GlobalScreen.addNativeKeyListener(commandListener);
                        GlobalScreen.addNativeMouseListener(commandListener);
                        GlobalScreen.addNativeMouseMotionListener(commandListener);
                        System.out.println("captura iniciada precione o botao: " + stopRecording.getText() + " para parar!");
                    }
                    catch (Exception error){
                        throw new RuntimeException(error.getMessage());
                    }
                }
            }
        });
        panel.add(new JLabel("Botao de parada"));
        return panel;
    }

    private JPanel fileNamePanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#252525"));
        fileName.setPreferredSize(new Dimension(200, 30));
        fileName.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(fileName);
        return panel;
    }

    public RecordPanel(){
        init();
    }

    private JLabel title = new JLabel("Gravar");
    private JLabel fileNameLabel = new JLabel("nome do arquivo:");
    private JTextField directory = new JTextField();
    private JTextField fileName = new JTextField();
    private JButton directoryButton = new JButton("pasta");
    private JButton recordButton = new JButton("gravar");
    private JTextField stopRecording = new JTextField();
}
