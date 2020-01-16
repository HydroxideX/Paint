package eg.edu.alexu.csd.oop.Utils;

import java.awt.*;

public class shapesDetector {
    public double[] pointsToLine(Point p1, Point p2) {
        double[] ar = new double[3];
        ar[0] = (double) (p2.y - p1.y) / (p2.x - p1.x);
        ar[1] = 1;
        ar[2] = (p1.y - ar[0] * p1.x);
        return ar;
    }


    private static double area(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    public static boolean isInside(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y) {
        double A = area(x1, y1, x2, y2, x3, y3);
        double A1 = area(x, y, x2, y2, x3, y3);
        double A2 = area(x1, y1, x, y, x3, y3);
        double A3 = area(x1, y1, x2, y2, x, y);
        return (A == A1 + A2 + A3);
    }
}
