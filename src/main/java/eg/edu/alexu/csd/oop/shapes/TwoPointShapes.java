package eg.edu.alexu.csd.oop.shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TwoPointShapes implements Shape {
    protected Point position;
    protected Map<String, Double> properties= new HashMap<>();
    protected Color color;
    protected Color fillColor;

    @Override
    public void setPosition(Point position) {
        this.position=position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
    }

    @Override
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) {
        this.color=color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor=color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
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
