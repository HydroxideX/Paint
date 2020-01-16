package eg.edu.alexu.csd.oop.shapes;

import java.awt.*;
import java.util.Map;

public class Square extends TwoPointShapes {
    public Square(){
        position = new Point(0,0);
        color = Color.black;
        fillColor = Color.black;
        this.properties.put("type",5d);
        this.properties.put("released",0d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties=properties;
        this.properties.put("type",5d);
        this.properties.putIfAbsent("selected",0d);
        this.properties.putIfAbsent("released",1d);
        this.properties.putIfAbsent("x2", (double) position.x);
        this.properties.putIfAbsent("y2", (double) position.y);
    }

    @Override
    public void draw(Graphics canvas) {
        Point p1 = new Point(getProperties().get("x2").intValue(), getProperties().get("y2").intValue());
        Point p3 = Correct(position, p1);
        Double l;
        Double w;
        Double mn;
        if (p3.x == position.x && p3.y == position.y) {
            l = (double) Math.abs(p1.x - position.x);
            w = (double) Math.abs(p1.y - position.y);
            mn = (double) max(l.intValue(), w.intValue());
        } else if (p3.x == position.x) {
            mn = (double) (position.y - p3.y);
        } else if (p3.y == position.y) {
            mn = (double) (position.x - p3.x);

        } else {
            if (position.x - p3.x < position.y - p3.y) p3.x = position.y - position.x + p3.y;
            if (position.x - p3.x > position.y - p3.y) p3.x = position.x - position.y + p3.y;
            mn = (double) (position.x - p3.x);

        }
        if (this.properties.get("released") == 1) {
            Double j = (double) (p3.x + mn.intValue());
            this.properties.put("x2", j);
            j = (double) (p3.y + mn.intValue());
            this.properties.put("y2", j);
            this.properties.put("released", 0d);
            setPosition(p3);
        }
            canvas.setColor(getFillColor());
            canvas.fillRect(p3.x, p3.y, mn.intValue(), mn.intValue());
            canvas.setColor(getColor());
            canvas.drawRect(p3.x, p3.y, mn.intValue(), mn.intValue());

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Square c=new Square();
        c.setProperties(getProperties());
        c.setPosition(getPosition());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        return c;
    }
}
