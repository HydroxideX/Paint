package eg.edu.alexu.csd.oop.shapes;

import java.awt.*;
import java.util.Map;

public class line extends TwoPointShapes{
    public line(){
        position = new Point(0,0);
        color = Color.black;
        this.properties.put("type",1d);
        this.properties.put("released",1d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
        this.properties.put("type",1d);
        this.properties.put("released",1d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
    }

    @Override
    public void setFillColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getFillColor() {
        return getColor();
    }

    public void draw (Graphics canvas) {
        canvas.setColor(getFillColor());
        canvas.drawLine(getPosition().x,getPosition().y,getProperties().get("x2").intValue(),getProperties().get("y2").intValue());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        line c=new line();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
}
