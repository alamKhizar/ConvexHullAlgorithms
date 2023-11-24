package SCD.GUI_TASK.ConvexHullAlgorithms;

    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

     public class LineIntersectionUsingVectorProduct extends JFrame {
        private JPanel drawingPanel;
        private JButton startButton;
        private JButton clearButton;
        private ArrayList<Point> points;
        private boolean showLines;
        private boolean linesIntersect;

        public LineIntersectionUsingVectorProduct() {
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
                    JOptionPane.showMessageDialog(LineIntersectionUsingVectorProduct.this, "You can only place four points.");
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
                 Point p1 = points.get(0);
                 Point p2 = points.get(1);
                 Point p3 = points.get(2);
                 Point p4 = points.get(3);

                 // Check if the orientation of the points is different
                 return orientation(p1, p2, p3) != orientation(p1, p2, p4)
                         && orientation(p3, p4, p1) != orientation(p3, p4, p2);
             }
         }

         // Helper method to calculate the orientation of three points
         private int orientation(Point p1, Point p2, Point p3) {
             int val = (p2.y - p1.y) * (p3.x - p2.x) - (p2.x - p1.x) * (p3.y - p2.y);
             if (val == 0) return 0;  // Collinear
             return (val > 0) ? 1 : -1; // Clockwise or counterclockwise
         }


        private double calculateIntercept(Point point, double slope) {
            return point.y - slope * point.x;
        }

        private double calculateSlope(Point p1, Point p2) {
            return (double) (p2.y - p1.y) / (p2.x - p1.x);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new LineIntersectionUsingVectorProduct().setVisible(true));
        }
    }

