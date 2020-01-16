package eg.edu.alexu.csd.oop.shapes;

import java.awt.*;
import java.util.Map;

public class Rectangle extends TwoPointShapes  {
    public Rectangle(){
        position = new Point(0,0);
        color = Color.black;
        fillColor = Color.black;
        this.properties.put("type",3d);
        this.properties.put("released",1d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties=properties;
        this.properties.put("type",3d);
        this.properties.put("released",1d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
        this.properties.putIfAbsent("selected", 0.0D);
    }

    @Override
    public void draw(Graphics canvas) {
        Point p1 = new Point(getProperties().get("x2").intValue(),getProperties().get("y2").intValue());
        Point p3 = correct(position,p1);
        Double l = (double) Math.abs(p1.x - position.x);
        Double w = (double) Math.abs(p1.y - position.y);
        canvas.setColor(getFillColor());
        canvas.fillRect(p3.x,p3.y,l.intValue(),w.intValue());
        canvas.setColor(getColor());
        canvas.drawRect(p3.x,p3.y,l.intValue(),w.intValue());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Rectangle c=new Rectangle();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
}
