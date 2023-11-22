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
    private ArrayList<Integer> xPoints;
    private ArrayList<Integer> yPoints;
    private boolean showLines;
    private boolean linesIntersect;

    public SlopeMethod() {
        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
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
                    for (int i = 0; i < xPoints.size() - 1; i++) {
                        int x1 = xPoints.get(i);
                        int y1 = yPoints.get(i);

                        int x2 = xPoints.get(i + 1);
                        int y2 = yPoints.get(i + 1);

                        g.setColor(Color.YELLOW);
                        g.drawLine(x1, y1, x2, y2);
                        i++;
                    }
                }

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

        startButton = new JButton("Check Intersections");
        startButton.setFocusable(false);
        startButton.setBackground(new Color(0, 19, 23));
        startButton.setFont(new Font("Arial", Font.BOLD, 15));
        startButton.setForeground(new Color(181, 255, 0));
        startButton.setBorderPainted(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLines = true;
                linesIntersect = checkForIntersections();

                if (linesIntersect) {
                    JOptionPane.showMessageDialog(SlopeMethod.this, "Intersect");
                } else {
                    JOptionPane.showMessageDialog(SlopeMethod.this, "Lines don't intersect");
                }
                drawingPanel.repaint();
                printPoints();
            }
        });
        add(startButton, BorderLayout.SOUTH);

        clearButton = new JButton("Clear Points");
        clearButton.setFocusable(false);
        clearButton.setBackground(new Color(0, 19, 23));
        clearButton.setFont(new Font("Arial", Font.BOLD, 15));
        clearButton.setForeground(new Color(181, 255, 0));
        clearButton.setBorderPainted(false);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPoints();
                drawingPanel.repaint();
                JOptionPane.showMessageDialog(SlopeMethod.this, "Points cleared");
            }
        });

        add(clearButton, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void clearPoints() {
        xPoints.clear();
        yPoints.clear();
        showLines = false;
        linesIntersect = false;
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (xPoints.size() < 4) {
                int x = e.getX();
                int y = e.getY();
                xPoints.add(x);
                yPoints.add(y);
                drawingPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(SlopeMethod.this, "You can only place four points.");
            }
        }
    }

    private void printPoints() {
        System.out.println("Stored Points:");
        for (int i = 0; i < xPoints.size(); i++) {
            System.out.println("(" + xPoints.get(i) + ", " + yPoints.get(i) + ")");
        }
    }

    private boolean checkForIntersections() {
        if (xPoints.size() < 4) {
            return false;
        } else {
            double l1slope = calculateSlope(xPoints.get(0), yPoints.get(0), xPoints.get(1), yPoints.get(1));
            double l2slope = calculateSlope(xPoints.get(2), yPoints.get(2), xPoints.get(3), yPoints.get(3));

            // Round off the slope values
            long roundedL1Slope = Math.round(l1slope);
            long roundedL2Slope = Math.round(l2slope);

            if (roundedL1Slope != roundedL2Slope) {
                return true; // Lines are not parallel, they may intersect
            } else {
                // Check if the lines have different y-intercepts
                double l1Intercept = calculateIntercept(xPoints.get(0), yPoints.get(0), l1slope);
                double l2Intercept = calculateIntercept(xPoints.get(2), yPoints.get(2), l2slope);

                // Round off the intercept values
                long roundedL1Intercept = Math.round(l1Intercept);
                long roundedL2Intercept = Math.round(l2Intercept);

                return roundedL1Intercept != roundedL2Intercept;
            }
        }
    }

    private double calculateIntercept(int x, int y, double slope) {
        return y - slope * x;
    }

    private double calculateSlope(int x1, int y1, int x2, int y2) {
        return (double) (y2 - y1) / (x2 - x1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SlopeMethod().setVisible(true);
            }
        });
    }
}
