package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LineAnimation extends JPanel implements ActionListener {
    private int x1, y1, x2, y2; // The coordinates of the two points
    private int animationProgress = 0; // Animation progress
    private Timer timer;

    public LineAnimation() {
        x1 = 0;
        y1 = 0;
        x2 = 300;
        y2 = 300;
        timer = new Timer(50, this); // Timer fires every 50 milliseconds
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int animatedX = x1 + (x2 - x1) * animationProgress / 50;
        int animatedY = y1 + (y2 - y1) * animationProgress / 50;

        g2d.drawLine(x1, y1, animatedX, animatedY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (animationProgress < 100) {
            animationProgress++;
        } else {
            timer.stop(); // Stop the timer when animation is complete
        }
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Line Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 300);
            frame.add(new LineAnimation());
            frame.setVisible(true);
        });
    }
}

