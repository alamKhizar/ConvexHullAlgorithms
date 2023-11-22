package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {
    JButton LINE_INTERSECTIONS,
            BRUTE_FORCE_BUTTON,
            GRAHAM_SCAN_BUTTON,
            JARVIS_BUTTON,
            LINE_ELIMINATION,
            RESEARCH_BUTTON;

    HomeScreen() {
        setTitle("CONVEX HULL K213868");
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel p = new JPanel();
        p.setBackground(Color.red);
        p.setLayout(null);
        p.setBackground(new Color(1, 28, 35));
        add(p);

        JLabel l = new JLabel("CONVEX HULL");
        l.setBounds(200, 30, 200, 100);
        l.setFont(new Font("AERIAL", Font.BOLD, 25));
        l.setForeground(new Color(181, 255, 0));
        p.add(l);

        LINE_INTERSECTIONS = new JButton("Check Intersections");
        BRUTE_FORCE_BUTTON = new JButton("Brute Force");
        GRAHAM_SCAN_BUTTON = new JButton("Graham Scan");
        GRAHAM_SCAN_BUTTON.addActionListener(e -> { new GrahamScan();});
        JARVIS_BUTTON = new JButton("Jarvis Marsh");
        JARVIS_BUTTON.addActionListener(e -> {new JarvisHull();});
        LINE_ELIMINATION = new JButton("Elimination");
        RESEARCH_BUTTON = new JButton("Research");

        LINE_INTERSECTIONS.setFocusable(false);
        LINE_INTERSECTIONS.setBackground(new Color(0, 19, 23));
        LINE_INTERSECTIONS.setFont(new Font("AERIAL", Font.BOLD, 15));
        LINE_INTERSECTIONS.setForeground(new Color(181, 255, 0));
        LINE_INTERSECTIONS.setBorderPainted(false);
        LINE_INTERSECTIONS.addActionListener(e -> {
            String[] options = {"CCW Method", "SLOPE Method","VECTOR Method"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Choose a method:",
                    "Line Intersect Options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                // Open the EnterPoint class
                DrawingApp drawingApp = new DrawingApp();
                drawingApp.setVisible(true);

            } else if (choice == 1) {
                // Open the ConvexHullVisualization class
                new SlopeMethod();
            }else if (choice == 2) {
                // Open the ConvexHullVisualization class
                new ConvexHullVisualization();
            }

        });


        BRUTE_FORCE_BUTTON.setFocusable(false);
        BRUTE_FORCE_BUTTON.setBackground(new Color(0, 19, 23));
        BRUTE_FORCE_BUTTON.setFont(new Font("AERIAL", Font.BOLD, 15));
        BRUTE_FORCE_BUTTON.setForeground(new Color(181, 255, 0));
        BRUTE_FORCE_BUTTON.setBorderPainted(false);
        BRUTE_FORCE_BUTTON.addActionListener(e -> {
            String[] options = {"Enter Point Method", "Touch Method"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Choose a method:",
                    "Brute Force Options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                new EnterPoints();
            } else if (choice == 1) {
                new ConvexHullVisualization();
            }
        });


        GRAHAM_SCAN_BUTTON.setFocusable(false);
        GRAHAM_SCAN_BUTTON.setBackground(new Color(0, 19, 23));
        GRAHAM_SCAN_BUTTON.setFont(new Font("AERIAL", Font.BOLD, 15));
        GRAHAM_SCAN_BUTTON.setForeground(new Color(181, 255, 0));
        GRAHAM_SCAN_BUTTON.setBorderPainted(false);

        JARVIS_BUTTON.setFocusable(false);
        JARVIS_BUTTON.setBackground(new Color(0, 19, 23));
        JARVIS_BUTTON.setFont(new Font("AERIAL", Font.BOLD, 15));
        JARVIS_BUTTON.setForeground(new Color(181, 255, 0));
        JARVIS_BUTTON.setBorderPainted(false);

        LINE_ELIMINATION.setFocusable(false);
        LINE_ELIMINATION.setBackground(new Color(0, 19, 23));
        LINE_ELIMINATION.setFont(new Font("AERIAL", Font.BOLD, 15));
        LINE_ELIMINATION.setForeground(new Color(181, 255, 0));
        LINE_ELIMINATION.setBorderPainted(false);

        RESEARCH_BUTTON.setFocusable(false);
        RESEARCH_BUTTON.setBackground(new Color(0, 19, 23));
        RESEARCH_BUTTON.setFont(new Font("AERIAL", Font.BOLD, 15));
        RESEARCH_BUTTON.setForeground(new Color(181, 255, 0));
        RESEARCH_BUTTON.setBorderPainted(false);



        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 20, 10));
        p1.setBounds(100, 150, 400, 200);
        p1.setBackground(new Color(1, 28, 35));
        p.add(p1);


        p1.add(LINE_INTERSECTIONS);
        p1.add(BRUTE_FORCE_BUTTON);
        p1.add(GRAHAM_SCAN_BUTTON);
        p1.add(JARVIS_BUTTON);
        p1.add(LINE_ELIMINATION);
        p1.add(RESEARCH_BUTTON);


        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HomeScreen();
    }
}
