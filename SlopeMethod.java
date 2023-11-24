package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SlopeMethod extends JFrame {
    private JPanel drawingPanel;
    private JButton startButton;
    private JButton clearButton;
    private ArrayList<Point> points;
    private boolean showLines;
    private boolean linesIntersect;

    public SlopeMethod() {
        points = new ArrayList<>();
        showLines = false;
        linesIntersect = false;

        setTitle("Drawing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (showLines) {
                    g.setColor(Color.YELLOW);
                    for (int i = 0; i < points.size() - 1; i += 2) {
                        Point p1 = points.get(i);
                        Point p2 = points.get(i + 1);
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }

                g.setColor(Color.RED);
                for (Point point : points) {
                    g.fillOval(point.x, point.y, 7, 7);
                }
            }
        };
        drawingPanel.setPreferredSize(new Dimension(400, 400));
        drawingPanel.setBackground(new Color(1, 28, 35));
        drawingPanel.addMouseListener(new MyMouseListener());
        add(drawingPanel, BorderLayout.CENTER);

        startButton = createButton("Check Intersections", this::startButtonAction);
        add(startButton, BorderLayout.SOUTH);

        clearButton = createButton("Clear Points", this::clearButtonAction);
        add(clearButton, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBackground(new Color(0, 19, 23));
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(new Color(181, 255, 0));
        button.setBorderPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void clearButtonAction(ActionEvent e) {
        clearPoints();
        drawingPanel.repaint();
        JOptionPane.showMessageDialog(this, "Points cleared");
    }

    private void startButtonAction(ActionEvent e) {
        showLines = true;
        linesIntersect = checkForIntersections();

        if (linesIntersect) {
            JOptionPane.showMessageDialog(this, "Intersect");
        } else {
            JOptionPane.showMessageDialog(this, "Lines don't intersect");
        }
        drawingPanel.repaint();
        printPoints();
    }

    private void clearPoints() {
        points.clear();
        showLines = false;
        linesIntersect = false;
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (points.size() < 4) {
                points.add(e.getPoint());
                drawingPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(SlopeMethod.this, "You can only place four points.");
            }
        }
    }

    private void printPoints() {
        System.out.println("Stored Points:");
        for (Point point : points) {
            System.out.println("(" + point.x + ", " + point.y + ")");
        }
    }

    private boolean checkForIntersections() {
        if (points.size() < 4) {
            return false;
        } else {
            // Get the coordinates of the four points
            int x1 = points.get(0).x;
            int y1 = points.get(0).y;
            int x2 = points.get(1).x;
            int y2 = points.get(1).y;
            int x3 = points.get(2).x;
            int y3 = points.get(2).y;
            int x4 = points.get(3).x;
            int y4 = points.get(3).y;

            // Calculate slopes
            double m1 = (double) (y2 - y1) / (x2 - x1);
            double m2 = (double) (y4 - y3) / (x4 - x3);

            // Calculate y-intercepts
            double b1 = y1 - m1 * x1;
            double b2 = y3 - m2 * x3;

            // Check for intersection
            double intersectionX = (b2 - b1) / (m1 - m2);
            double intersectionY = m1 * intersectionX + b1;

            return intersectionX >= Math.min(x1, x2) && intersectionX <= Math.max(x1, x2)
                    && intersectionX >= Math.min(x3, x4) && intersectionX <= Math.max(x3, x4)
                    && intersectionY >= Math.min(y1, y2) && intersectionY <= Math.max(y1, y2)
                    && intersectionY >= Math.min(y3, y4) && intersectionY <= Math.max(y3, y4);
        }
    }


    private double calculateIntercept(Point point, double slope) {
        return point.y - slope * point.x;
    }

    private double calculateSlope(Point p1, Point p2) {
        return (double) (p2.y - p1.y) / (p2.x - p1.x);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SlopeMethod().setVisible(true));
    }
}
