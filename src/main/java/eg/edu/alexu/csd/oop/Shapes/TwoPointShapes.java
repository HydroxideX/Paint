package eg.edu.alexu.csd.oop.Shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TwoPointShapes implements Shape {
    protected Point position;
    protected Map<String, Double> properties= new HashMap<>();
    protected Color color;
    protected Color fillColor;

    public void setPosition(Point position) {
        this.position=position;
    }

    public Point getPosition() {
        return position;
    }

    public void setProperties(Map<String, Double> properties) {

    }

    public Map<String, Double> getProperties() {
        return properties;
    }

    public void setColor(Color color) {
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public void setFillColor(Color color) {
        this.fillColor=color;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void draw(Graphics canvas) {

    }

    public Object clone() throws CloneNotSupportedException {
        return null;
    }
    protected Point Correct(Point p1, Point p2){
        Point p3 = new Point();
        p3.x = min(p1.x,p2.x);
        p3.y = min(p1.y,p2.y);
        return p3;
    }
    protected int min(int a, int b){
        return Math.min(a, b);
    }
    protected int max(int a, int b){
        return Math.max(a,b);
    }
}
