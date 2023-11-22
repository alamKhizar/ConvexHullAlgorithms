package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class EnterPoints extends JFrame {

    JButton check;

    EnterPoints() {
        setTitle("CONVEX HULL K213868");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel p = new JPanel();
        p.setBackground(Color.red);
        p.setLayout(null);
        p.setBackground(new Color(1, 28, 35));

        JLabel l = new JLabel("Enter X,Y points");
        l.setBounds(40, 30, 200, 100);
        l.setFont(new Font("AERIAL", Font.BOLD, 25));
        l.setForeground(new Color(181, 255, 0));
        p.add(l);


        JTextArea p1 = new JTextArea("X,Y");
        p1.setBounds(40, 110, 150, 130);
        p1.setBackground(new Color(0, 21, 24));
        p1.setFont(new Font("AERIAL", Font.BOLD, 15));
        p1.setForeground(new Color(181, 255, 0));
        JScrollPane scrollPane = new JScrollPane(p1);
        scrollPane.setBounds(40, 110, 150, 130);
        p.add(scrollPane);

        check = new JButton("CHECK");
        check.setBounds(40, 250, 150, 20);
        check.setFocusable(false);
        check.setBackground(new Color(255, 255, 255));
        check.setFont(new Font("AERIAL", Font.BOLD, 12));
        check.setForeground(new Color(1, 28, 35));
        check.setBorderPainted(false);
        p.add(check);


        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = p1.getText();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("example.txt"));
                    writer.write(text);
                    writer.close();
//                    JOptionPane.showMessageDialog(null, "Text added to the file.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }


                ArrayList<Integer> xValues = new ArrayList<>();
                ArrayList<Integer> yValues = new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.contains("X") && !line.contains("Y")) {
                            String[] parts = line.split(",");
                            if (parts.length == 2) {
                                int x = Integer.parseInt(parts[0]);
                                int y = Integer.parseInt(parts[1]);
                                xValues.add(x);
                                yValues.add(y);
                            }
                        }
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error reading the file: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (xValues.size() <= 2 || yValues.size() <= 2) {
                    JOptionPane.showMessageDialog(null, "Convex hull coordinates > 2, please cooperate", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    new buteForce(xValues, yValues);
                }
            }
        });

        add(p);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EnterPoints();
    }


}
