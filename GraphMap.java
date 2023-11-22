package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GraphMap extends JFrame {
    private ArrayList<Integer> xArray;
    private ArrayList<Integer> yArray;

    private ArrayList<Point> dataPoints;
    ArrayList<Integer> x_Plotted_Array;
    ArrayList<Integer> y_Plotted_Array;

    public GraphMap(ArrayList<Integer> xArray, ArrayList<Integer> yArray) {
        this.xArray = xArray;
        this.yArray = yArray;
        this.dataPoints = new ArrayList<>();
        this.x_Plotted_Array = new ArrayList<>();
        this.y_Plotted_Array = new ArrayList<>();

        for (int i = 0; i < xArray.size(); i++) {
            dataPoints.add(new Point(xArray.get(i), yArray.get(i)));
        }

        setTitle("Graph Example");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };

        graphPanel.setBackground(new Color(255, 255, 255));
        add(graphPanel);
        setVisible(true);
    }

    private void drawGraph(Graphics g) {

        // Draw x-axis
        g.drawLine(50, 350, 550, 350);
        for (int x = 0; x <= 100; x += 5) {

            //50 is the starting of x-axis point
            int xCoord = 50 + x * 5;

            //vertical lines
            g.drawLine(xCoord, 345, xCoord, 355);

            //horizontal x number line
            g.drawString(Integer.toString(x), xCoord - 8, 370);
        }

        // Draw y-axis
        g.drawLine(50, 50, 50, 350);
        for (int y = 0; y <= 100; y += 5) {
            int yCoord = 350 - y * 3;
            g.drawLine(45, yCoord, 55, yCoord);
            g.drawString(Integer.toString(y), 15, yCoord + 5);
        }

        // Plot data points
        g.setColor(Color.BLUE);

        int count = 0;

        //Marking points on graph
        for (int i = 0; i < dataPoints.size(); i++) {
            int x = 50 + dataPoints.get(i).x * 5;
            int y = 350 - dataPoints.get(i).y * 3;
            g.fillOval(x - 3, y - 3, 6, 6);

        }

        //
        int n = dataPoints.size();
        for (int i = 0; i < n; i++) {
            int x = 50 + dataPoints.get(i).x * 5;
            x_Plotted_Array.add(x);
            System.out.println("x = " + x);
        }

        for (int i = 0; i < n; i++) {
            int y = 350 - dataPoints.get(i).y * 3;
            y_Plotted_Array.add(y);
            System.out.println("y = " + y);
        }


        System.out.println(x_Plotted_Array.size() / 2);
        System.out.println(y_Plotted_Array.size() / 2);


        //sketching the lines
        Integer min = Collections.min(x_Plotted_Array);
        int MIN_X_POINT_INDEX = x_Plotted_Array.indexOf(min);


        System.out.println("Min index = " + MIN_X_POINT_INDEX);
        System.out.println("Min index = " + MIN_X_POINT_INDEX);


        for (int i = MIN_X_POINT_INDEX; i < (x_Plotted_Array.size() / 2) - 2; i++) {
            for (int j = MIN_X_POINT_INDEX; j < (y_Plotted_Array.size() / 2) - 2; j++) {

//                if (CanIdrawLine(x_Plotted_Array.get(i), y_Plotted_Array.get(i),x_Plotted_Array.get(j+1), y_Plotted_Array.get(j+1),x_Plotted_Array.get(j+2), y_Plotted_Array.get(j+2))) {
                g.drawLine(x_Plotted_Array.get(i), y_Plotted_Array.get(i),
                        x_Plotted_Array.get(j + 1), y_Plotted_Array.get(j + 1));
//                }

            }
        }

    }

    /**
     * where a = y2 − y1, b = x1 − x2, c = x1y2 − y1x2.
     *
     * Second, such a line divides the plane into two half-planes: for all the points in one of them,
     * ax + by > c, while for all the points in the other, ax + by < c. (For the points on the line itself, of course, ax + by = c.)
     * Thus, to check whether certain points lie on the same side of the line, we can simply check whether the expression ax + by − c has
     * the same sign for each of these points. We leave the implementation details as an exercise.*/

//    boolean CanIdrawLine(int ax,int ay,int bx,int by,int cx,int cy) {
//        int val  = ((bx-ax)*(cy-ay))-((by-ay)*(cx-ax));
//        if (val < 0) {
//            return false;
//        }else{
//            return true;
//        }
//    }



    public static void main(String[] args) {
        ArrayList<Integer> xValues = new ArrayList<>();
        ArrayList<Integer> yValues = new ArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("X") && !line.contains("Y")) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        int x = Integer.parseInt(parts[0]);
                        int y = Integer.parseInt(parts[1]);
                        xValues.add(x);
                        yValues.add(y);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the application if an error occurs.
        }

        System.out.println("X values: " + xValues);
        System.out.println("Y values: " + yValues);

        SwingUtilities.invokeLater(() -> new GraphMap(xValues, yValues));
    }
}