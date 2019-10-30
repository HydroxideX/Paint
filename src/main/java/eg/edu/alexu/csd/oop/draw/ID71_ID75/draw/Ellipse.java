package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Ellipse implements Shape  {
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
        canvas.setColor(getFillColor());
        canvas.fillOval(getPosition().x,getPosition().y,getProperties().get("width").intValue(),getProperties().get("height").intValue());
        canvas.setColor(getColor());
        canvas.drawOval(getPosition().x,getPosition().y,getProperties().get("width").intValue(),getProperties().get("height").intValue());
    }

    public Object clone() throws CloneNotSupportedException {
        Ellipse c=new Ellipse();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;    }
}
