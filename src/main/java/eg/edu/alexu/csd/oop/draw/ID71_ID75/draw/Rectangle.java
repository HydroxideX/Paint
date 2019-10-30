package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Rectangle implements Shape  {
    private Point position;
    private Map<String, Double> properties= new HashMap<>();
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
        Point p2 = new Point();
        p2.x = position.x;
        p2.y = position.y;
        Correct(p2,p1);
        Double l = Double.valueOf(p1.x-p2.x);
        Double w = Double.valueOf(p1.y-p2.y);
        canvas.setColor(getFillColor());
        canvas.fillRect(getPosition().x,getPosition().y,l.intValue(),w.intValue());
        canvas.setColor(getColor());
        canvas.drawRect(getPosition().x,getPosition().y,l.intValue(),w.intValue());
    }

    public Object clone() throws CloneNotSupportedException {
        Rectangle c=new Rectangle();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
    void Correct(Point p1,Point p2){
        Point p3 = new Point();
        if(p1.x <= p2.x && p1.y <= p2.y) return;
        else if(p1.x >= p2.x && p2.y <= p1.y) {
            p3.x = p1.x;
            p3.y = p1.y;
            p1.x = p2.x;
            p1.y = p2.y;
            p2.x = p3.x;
            p2.y = p3.y;
        }
        else if(p1.x >= p2.x && p2.y >= p1.y) {
            p3.x = p1.x;
            p1.x = p2.x;
            p2.x = p3.x;
        }
        else if(p1.x <= p2.x && p2.y <= p1.y) {
            p3.y = p1.y;
            p1.y = p2.y;
            p2.y = p3.y;
        }
    }

    Double max(Double v,Double r){
        if(v >= r) return v;
        return r;
    }
}
