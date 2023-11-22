package SCD.GUI_TASK.ConvexHullAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//point plotting from fetched array list
class PlotPointOnPanel extends JPanel{
    ArrayList<Integer> xArray = new ArrayList<>();
    ArrayList<Integer> yArray = new ArrayList<>();

    int minPointToBeDrawn;

    public PlotPointOnPanel(ArrayList<Integer> xArray, ArrayList<Integer> yArray) {
        this.xArray = xArray;
        this.yArray = yArray;
        this.minPointToBeDrawn = Math.min(xArray.size(),yArray.size());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d= (Graphics2D)g;
        g2d.setColor(Color.BLACK);
        float thickness = 1.0f; // You can adjust this value for the desired line thickness
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawLine(xArray.get(0),yArray.get(0),xArray.get(2),yArray.get(2));


        int circleSize = 7;
        for (int i = 0; i < minPointToBeDrawn; i++) {
            g2d.fillOval(xArray.get(i) - circleSize / 2, yArray.get(i) - circleSize / 2, circleSize, circleSize);
        }
    }
}
public class ConvexHullBruteForce extends JFrame {

    ConvexHullBruteForce(){
        JFrame frame = new JFrame("Coordinate Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<Integer> xValues = new ArrayList<>();
        ArrayList<Integer> yValues = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("X") || line.contains("Y")){

                }else {
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
            e.printStackTrace();
        }

        System.out.println("X values: " + xValues);
        System.out.println("Y values: " + yValues);


        PlotPointOnPanel plot = new PlotPointOnPanel(xValues,yValues);

        //finding min max
        if (!xValues.isEmpty()) {
            Integer min = Collections.min(xValues);
            Integer max = Collections.max(xValues);

            //to track the comparison just get indexes
            int xIndex = xValues.indexOf(min);

            System.out.println("Minimum value: " + min);
            System.out.println("Maximum value: " + max);
            System.out.println("X Index      : " + xIndex);

        } else {
            System.out.println("The ArrayList is empty.");
        }

        int slope= (yValues.get(2)-yValues.get(0))/(xValues.get(2)-xValues.get(0));
        System.out.println("slope = "+slope);
        String equation = "y-y1 = slope (x-x1)";


        int higher = Math.max(xValues.get(0),xValues.get(2));
        System.out.println("Higher x " + higher);

        //calculate c
        /*
        *3,4 a =-1 b -1
        * */
        int a = yValues.get(2)-yValues.get(0);
        int b = xValues.get(0)-xValues.get(2);
        int c = ((a)*(xValues.get(0)))+((b)*(yValues.get(0)));
        System.out.println("C value = "+c);

        int minPointToBeDrawn = Math.min(xValues.size(),yValues.size());
/***
        for (int i = 0; i < minPointToBeDrawn ; i++) {
            for (int j = 0; j < minPointToBeDrawn; j++) {
                for (int k = 0; k <minPointToBeDrawn ; k++) {

                }
            }
        }*/
        JLabel l = new JLabel();
        l.setLocation(0,0);
        l.setSize(new Dimension(50,25));
        l.setBackground(Color.black);
        frame.add(l);

//        for (int i = 0; i < minPointToBeDrawn; i++) {
//
//            JLabel l = new JLabel();
//            l.setLocation(xValues.get(i) - 7 / 2,yValues.get(i) - 7 / 2);
//            l.setSize(new Dimension(50,25));
//            l.setBackground(Color.black);
//            frame.add(l);
//        }
////////
        frame.add(plot);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
       new ConvexHullBruteForce();
    }
}
