package SCD.GUI_TASK.ConvexHullAlgorithms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class JarvisHull extends JFrame {
    private ArrayList<Point> dataPoints;
    private long startTime;
    private int animationStep = 0;
    private Timer animationTimer;

    public JarvisHull() {
        this.dataPoints = new ArrayList<>();

        setTitle("Convex Hull Visualization");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < dataPoints.size(); i++) {
                    int x = dataPoints.get(i).x;
                    int y = dataPoints.get(i).y;
                    g.setColor(Color.RED);
                    g.fillOval(x, y, 8, 8);
                }
                drawConvexHull(g);
            }
        };

        graphPanel.setLayout(null);
        graphPanel.setBackground(new Color(1, 28, 35));

        graphPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dataPoints.add(new Point(e.getX(), e.getY()));
                repaint();
            }
        });

        JButton runButton = new JButton("Run Convex Hull");
        runButton.setBounds(400, 395, 150, 25);
        runButton.setFocusable(false);
        runButton.setBackground(new Color(0, 19, 23));
        runButton.setFont(new Font("Arial", Font.BOLD, 15));
        runButton.setForeground(new Color(181, 255, 0));
        runButton.setBorderPainted(false);
        runButton.addActionListener(e -> startConvexHullAlgorithm());

        graphPanel.add(runButton);
        add(graphPanel);
        setVisible(true);
    }

    private void drawConvexHull(Graphics g) {
        int numPointsToInclude = Math.min(animationStep, dataPoints.size());

        Graphics2D g2d = (Graphics2D) g;
        if (numPointsToInclude < 3) {
            return;
        }


        Point anchorPoint = dataPoints.get(0);
        for (Point point : dataPoints) {
            if (point.x < anchorPoint.x || (point.x == anchorPoint.x && point.y < anchorPoint.y)) {
                anchorPoint = point;
            }
        }

        Point currentPoint = anchorPoint;
        Point nextPoint;

        do {
            nextPoint = dataPoints.get(0);

            for (int i = 1; i < numPointsToInclude; i++) {
                if (dataPoints.get(i) == currentPoint) {
                    continue;
                }

                int orientation = orientation(currentPoint, nextPoint, dataPoints.get(i));

                if (nextPoint == currentPoint || orientation == -1 ||
                        (orientation == 0 && distanceSquared(currentPoint, dataPoints.get(i)) >
                                distanceSquared(currentPoint, nextPoint))) {
                    nextPoint = dataPoints.get(i);
                }
            }

            g2d.setColor(new Color(231, 255, 0, 255));
            g.drawLine(currentPoint.x, currentPoint.y, nextPoint.x, nextPoint.y);
            currentPoint = nextPoint;

        } while (currentPoint != anchorPoint);

        boolean convexHullCompleted = (numPointsToInclude == dataPoints.size());
    }
    private int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : -1;
    }

    private int distanceSquared(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }


    private void startConvexHullAlgorithm() {
        if (dataPoints.size() < 3) {
            JOptionPane.showMessageDialog(null, "At least 3 points are required to form a convex hull.");
            return;
        }

        startTime = System.currentTimeMillis();

        animationTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                if (animationStep <= dataPoints.size()) {
                    repaint();
                } else {
                    animationTimer.stop();
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;

                    System.out.println("Convex Hull Computation Time: " + elapsedTime + " milliseconds");
                    JOptionPane.showMessageDialog(null, "Convex Hull Computation Time: " + elapsedTime + " milliseconds");
                }
            }
        });

        animationTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JarvisHull::new);
    }
}