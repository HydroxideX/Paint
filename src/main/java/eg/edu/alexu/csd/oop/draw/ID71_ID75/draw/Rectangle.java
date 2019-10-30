package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Rectangle implements Shape  {
    Point position;
    Map<String, Double> properties=new HashMap<String, Double>();
    Color color;
    Color fillColor;
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
        canvas.fillRect(getPosition().x,getPosition().y,getProperties().get("width").intValue(),getProperties().get("height").intValue());
        canvas.setColor(getColor());
        canvas.drawRect(getPosition().x,getPosition().y,getProperties().get("width").intValue(),getProperties().get("height").intValue());
    }

    public Object clone() throws CloneNotSupportedException {
        return null;
    }
}
