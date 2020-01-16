package eg.edu.alexu.csd.oop.Shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Ellipse extends Rectangle implements Shape  {
    public Ellipse(){
        position = new Point(0,0);
        color = Color.black;
        fillColor = Color.black;
        this.properties.put("type",4d);
        this.properties.put("released",1d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties=properties;
        this.properties.put("type",4d);
        this.properties.putIfAbsent("selected", 0.0D);
        this.properties.put("released",1d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
    }

    @Override
    public void draw(Graphics canvas) {
        Point p1 = new Point(getProperties().get("x2").intValue(),getProperties().get("y2").intValue());
        Point p3 = Correct(position,p1);
        double l = Math.abs(p1.x - position.x);
        double w = Math.abs(p1.y - position.y);
        canvas.setColor(getFillColor());
        canvas.fillOval(p3.x,p3.y, (int) l, (int) w);
        canvas.setColor(getColor());
        canvas.drawOval(p3.x,p3.y, (int) l, (int) w);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Ellipse c = new Ellipse();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
}
