package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ConvexHullApp extends JFrame {

    private ArrayList<Point2D.Double> points;
    private ArrayList<Point2D.Double> convexHull;
    private int animationIndex;

    public ConvexHullApp() {
        points = new ArrayList<>();
        convexHull = new ArrayList<>();
        animationIndex = 0;

        setTitle("Convex Hull Visualization");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton generateButton = new JButton("Generate Convex Hull");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomPoints(20);
                computeConvexHull();
                animationIndex = 0; // Reset animation index
                startAnimation();
            }
        });

        // Button settings
        generateButton.setFocusable(false);
        generateButton.setBackground(new Color(0, 19, 23));
        generateButton.setFont(new Font("Arial", Font.BOLD, 15));
        generateButton.setForeground(new Color(181, 255, 0));
        generateButton.setBorderPainted(false);

        add(generateButton, BorderLayout.SOUTH);

        // Panel color
        getContentPane().setBackground(new Color(0, 19, 23));

        setVisible(true);
    }

    private void generateRandomPoints(int numPoints) {
        points.clear();
        convexHull.clear();

        int minX = 50;
        int maxX = 450;
        int minY = 50;
        int maxY = 450;

        for (int i = 0; i < numPoints; i++) {
            double x = minX + Math.random() * (maxX - minX);
            double y = minY + Math.random() * (maxY - minY);
            points.add(new Point2D.Double(x, y));
        }

        // Sort points by x-coordinate
        Collections.sort(points, Comparator.comparingDouble(Point2D.Double::getX));
    }

    private void computeConvexHull() {
        convexHull.clear();

        // Construct upper hull
        for (Point2D.Double point : points) {
            while (convexHull.size() >= 2 &&
                    crossProduct(convexHull.get(convexHull.size() - 2), convexHull.get(convexHull.size() - 1), point) <= 0) {
                convexHull.remove(convexHull.size() - 1);
            }
            convexHull.add(point);
        }

        // Construct lower hull
        int lowerHullSize = convexHull.size();
        for (int i = points.size() - 2; i >= 0; i--) {
            Point2D.Double point = points.get(i);
            while (convexHull.size() > lowerHullSize &&
                    crossProduct(convexHull.get(convexHull.size() - 2), convexHull.get(convexHull.size() - 1), point) <= 0) {
                convexHull.remove(convexHull.size() - 1);
            }
            convexHull.add(point);
        }

        // Remove the last point, which is a duplicate of the first point in the upper hull
        convexHull.remove(convexHull.size() - 1);
    }

    private double crossProduct(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
        return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
    }

    private void startAnimation() {
        Timer timer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animationIndex < convexHull.size()) {
                    repaint();
                    animationIndex++;
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when animation is complete
                }
            }
        });

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw points
        g2d.setColor(Color.RED);  // Circle color
        for (Point2D.Double point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            g2d.fillOval(x - 5, y - 5, 10, 10);
        }

        // Draw convex hull (only up to the current animation index)
        g2d.setColor(new Color(181, 255, 0));  // Line color
        int[] xPoints = new int[animationIndex];
        int[] yPoints = new int[animationIndex];
        for (int i = 0; i < animationIndex; i++) {
            xPoints[i] = (int) convexHull.get(i).getX();
            yPoints[i] = (int) convexHull.get(i).getY();
        }
        g2d.drawPolygon(xPoints, yPoints, animationIndex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConvexHullApp());
    }
}
