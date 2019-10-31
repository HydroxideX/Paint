package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Circle implements Shape  {
    private Point position;
    public Map<String, Double> properties= new HashMap<>();
    private Color color;
    private Color fillColor;
    public void setPosition(Point position) {
        this.position=position;
    }

    public Point getPosition() {
        return position;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties=properties;
        this.properties.put("type",2d);
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
        Point p1 = new Point(getProperties().get("x2").intValue(),getProperties().get("y2").intValue());
        Point p3 = Correct(position,p1);
        Double l,w,mn;

        if(p3.x == position.x && p3.y == position.y) {
            l = Double.valueOf(Math.abs(p1.x - position.x));
            w = Double.valueOf(Math.abs(p1.y - position.y));
            mn = Double.valueOf(max(l.intValue(),w.intValue()));
        }
        else if(p3.x == position.x) {
            mn = Double.valueOf(position.y-p3.y);
        } else if (p3.y == position.y){
            mn = Double.valueOf(position.x-p3.x);
        } else {
            if(position.x-p3.x < position.y-p3.y) p3.x = position.y - position.x + p3.y;
            if(position.x-p3.x >position.y-p3.y) p3.x = position.x - position.y + p3.y;
            mn = Double.valueOf(position.x-p3.x);
        }
        if(properties.get("released")==1d){
            Double j = Double.valueOf(p3.x + mn.intValue());
            properties.put("x2",j);
            j = Double.valueOf(p3.y + mn.intValue());
            properties.put("y2",j);
            properties.put("released",0d);
            setPosition(p3);
        }
        canvas.setColor(getFillColor());
        canvas.fillOval(p3.x,p3.y,mn.intValue(),mn.intValue());
        canvas.setColor(getColor());
        canvas.drawOval(p3.x,p3.y,mn.intValue(),mn.intValue());
    }

    public Object clone() throws CloneNotSupportedException {
        Circle c=new Circle();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
    private Point Correct(Point p1, Point p2){
        Point p3 = new Point();
        p3.x = min(p1.x,p2.x);
        p3.y = min(p1.y,p2.y);
        return p3;
    }
    private int min(int a, int b){
        return Math.min(a, b);
    }
    private int max(int a, int b){
        if(a<b) return b;
        return b;
    }
}
