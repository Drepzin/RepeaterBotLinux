package org.example.ui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {

    private final ImageIcon imageIcon = new ImageIcon("/main/resources/IMGS/iconImage.jog");
    //initialization of the principal components
    public void init(){
        this.setIconImage(imageIcon.getImage());
        this.setTitle("TepezRepeater-beta v0.1         |        Feito com <3 por @Drepz.");
        this.setContentPane(new MainPanel());
        this.setSize(800, 500);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //constructor
    public MainFrame(){
        init();
    }

    //opening the frame
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale","1.0");
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new MainFrame());
    }
}