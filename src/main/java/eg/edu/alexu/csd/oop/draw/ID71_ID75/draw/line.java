package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import javax.sound.sampled.Line;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class line implements Shape {
    Point position;
    Map<String, Double> properties=new HashMap<String, Double>();
    Color color;
    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    public Map<String, Double> getProperties() {
        return properties;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setFillColor(Color color) {
        this.color = color;
    }

    public Color getFillColor() {
        return getColor();
    }

    public void draw(Graphics canvas) {
        canvas.setColor(getFillColor());
        canvas.drawLine(getPosition().x,getPosition().y,getProperties().get("x2").intValue(),getProperties().get("y2").intValue());
    }

    public Object clone() throws CloneNotSupportedException {
        line c=new line();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
}
