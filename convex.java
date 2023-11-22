package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarFile;

public class convex extends JFrame{
    convex(){
        setSize(500,500);

        setLayout(null);
        JPanel p = new JPanel();
        p.setBounds(0, 0, 500, 400); // Set the size and position of the panel
        p.setBackground(Color.red);

        JButton button = new JButton("Click me");
        button.setBounds(50, 50, 100, 30); // Set the size and position of the button

        p.add(button); // Add the button to the panel

        add(p);

        setVisible(true);
    }

    public static void main(String[] args) {
        new convex();
    }
}