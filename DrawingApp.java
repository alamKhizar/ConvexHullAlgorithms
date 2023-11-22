package SCD.GUI_TASK.ConvexHullAlgorithms;

//ccw method
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DrawingApp extends JFrame {
    private JPanel drawingPanel;
    private JButton startButton;
    public ArrayList<Integer> xPoints;
    public ArrayList<Integer> yPoints;
    private boolean showLines; // Flag to control whether to show lines or not

    public DrawingApp() {
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
        showLines = false;

        setTitle("Drawing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw lines if the flag is set
                if (showLines) {
                    for (int i = 0; i < xPoints.size() - 1; i++) {
                        int x1 = xPoints.get(i);
                        int y1 = yPoints.get(i);

                        int x2 = xPoints.get(i + 1);
                        int y2 = yPoints.get(i + 1);

                        if (doLinesIntersect(x1, y1, x2, y2)) {
                            g.setColor(Color.RED); // Set color to blue if lines intersect
                        } else {
                            g.setColor(Color.GREEN); // Set color to green if lines do not intersect
                        }

                        g.drawLine(x1, y1, x2, y2);
                        i++;
                    }
                }

                // Draw points as circles
                for (int i = 0; i < xPoints.size(); i++) {
                    int x = xPoints.get(i);
                    int y = yPoints.get(i);
                    g.setColor(Color.RED);
                    g.fillOval(x, y, 7, 7);
                }
            }
        };
        drawingPanel.setPreferredSize(new Dimension(400, 400));
        drawingPanel.setBackground(new Color(1, 28, 35));
        drawingPanel.addMouseListener(new MyMouseListener());
        add(drawingPanel, BorderLayout.CENTER);

        startButton = new JButton("Click me first");
        startButton.setFocusable(false);
        startButton.setBackground(new Color(0, 19, 23));
        startButton.setFont(new Font("Arial", Font.BOLD, 15));
        startButton.setForeground(new Color(181, 255, 0));
        startButton.setBorderPainted(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLines = true;
                drawingPanel.repaint();
                printPoints();
            }
        });
        add(startButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int x = e.getX();
            int y = e.getY();
            xPoints.add(x);
            yPoints.add(y);
            drawingPanel.repaint();
        }
    }

    private void printPoints() {
        System.out.println("Stored Points:");
        for (int i = 0; i < xPoints.size(); i++) {
            System.out.println("(" + xPoints.get(i) + ", " + yPoints.get(i) + ")");
        }
    }

    private boolean doLinesIntersect(int x1, int y1, int x2, int y2) {
        // Iterate over each pair of consecutive lines
        for (int i = 0; i < xPoints.size() - 3; i += 2) {
            int x3 = xPoints.get(i);
            int y3 = yPoints.get(i);
            int x4 = xPoints.get(i + 1);
            int y4 = yPoints.get(i + 2);

            // Check for intersection
            if (doIntersect(x1, y1, x2, y2, x3, y3, x4, y4)) {
                return true;
            }
        }

        return false;
    }

    // Helper method to check if two line segments intersect
    private boolean doIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int o1 = orientation(x1, y1, x2, y2, x3, y3);
        int o2 = orientation(x1, y1, x2, y2, x4, y4);
        int o3 = orientation(x3, y3, x4, y4, x1, y1);
        int o4 = orientation(x3, y3, x4, y4, x2, y2);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special cases
        if (o1 == 0 && onSegment(x1, y1, x2, y2, x3, y3)) {
            return true;
        }
        if (o2 == 0 && onSegment(x1, y1, x2, y2, x4, y4)) {
            return true;
        }
        if (o3 == 0 && onSegment(x3, y3, x4, y4, x1, y1)) {
            return true;
        }
        if (o4 == 0 && onSegment(x3, y3, x4, y4, x2, y2)) {
            return true;
        }

        return false;
    }

    // Helper method to compute orientation using cross product
    private int orientation(int x1, int y1, int x2, int y2, int x3, int y3) {
        int val = (y2 - y1) * (x3 - x2) - (x2 - x1) * (y3 - y2);
        if (val == 0) {
            return 0; // Collinear
        }
        return (val > 0) ? 1 : 2; // Clockwise or counterclockwise
    }

    // Helper method to check if point (x, y) lies on the line segment (x1, y1)-(x2, y2)
    private boolean onSegment(int x1, int y1, int x2, int y2, int x, int y) {
        return (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) &&
                y >= Math.min(y1, y2) && y <= Math.max(y1, y2));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DrawingApp().setVisible(true);
            }
        });
    }
}
