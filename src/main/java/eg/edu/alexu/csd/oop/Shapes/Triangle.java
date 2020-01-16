package eg.edu.alexu.csd.oop.Shapes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Triangle implements Shape  {
    private Point position;
    private Map<String, Double> properties= new HashMap<String, Double>();
    private Color color;
    private Color fillColor;
    public Triangle(){
        position = new Point(0,0);
        color = Color.black;
        fillColor = Color.black;
        this.properties.putIfAbsent("type",6d);
        this.properties.putIfAbsent("released",0d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
        this.properties.putIfAbsent("x3", (double) position.x);
        this.properties.putIfAbsent("y3", (double) position.y);
    }
    public void setPosition(Point position) {
        this.position=position;
    }

    public Point getPosition() {
        return position;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties=properties;
        this.properties.putIfAbsent("type",6d);
        this.properties.putIfAbsent("type",6d);
        this.properties.putIfAbsent("released",0d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
        this.properties.putIfAbsent("x3", (double) position.x);
        this.properties.putIfAbsent("y3", (double) position.y);
        this.properties.putIfAbsent("selected", 0.0D);
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
        int []x= new int[3];
        x[0]=getPosition().x;
        x[1]=getProperties().get("x2").intValue();
        x[2]=getProperties().get("x3").intValue();
        int []y= new int[3];
        y[0]=getPosition().y;
        y[1]=getProperties().get("y2").intValue();
        y[2]=getProperties().get("y3").intValue();
        canvas.setColor(getFillColor());
        canvas.fillPolygon(x,y,3);
        canvas.setColor(getColor());
        canvas.drawPolygon(x,y,3);
    }

    public Object clone() throws CloneNotSupportedException {
        Triangle c=new Triangle();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
}
