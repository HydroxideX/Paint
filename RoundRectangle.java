package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RoundRectangle implements Shape {
    protected Point p;
    protected Map<String, Double> prop = new HashMap();
    protected Color c;
    protected Color fc;

    public RoundRectangle() {
        this.prop.put("Width", 100D);
        this.prop.put("Length", 200.0D);
        this.prop.put("ArcWidth", 150.0D);
        this.prop.put("ArcLength", 150.0D);
    }

    public void setPosition(Point position) {
        this.p = position;
    }

    public Point getPosition() {
        return this.p;
    }

    public void setProperties(Map<String, Double> properties) {
        this.prop = properties;
    }

    public Map<String, Double> getProperties() {
        return this.prop;
    }

    public void setColor(Color color) {
        this.c = color;
    }

    public Color getColor() {
        return this.c;
    }

    public void setFillColor(Color color) {
        this.fc = color;
    }

    public Color getFillColor() {
        return this.fc;
    }

    public void draw(Graphics canvas) {
        ((Graphics2D)canvas).setColor(this.getFillColor());
        ((Graphics2D)canvas).fillRoundRect((int)this.p.getX(), (int)this.p.getY(), ((Double)this.prop.get("Width")).intValue(), ((Double)this.prop.get("Length")).intValue(), ((Double)this.prop.get("ArcWidth")).intValue(), ((Double)this.prop.get("ArcLength")).intValue());
        ((Graphics2D)canvas).setStroke(new BasicStroke(2.0F));
        ((Graphics2D)canvas).setColor(this.getColor());
        ((Graphics2D)canvas).drawRoundRect((int)this.p.getX(), (int)this.p.getY(), ((Double)this.prop.get("Width")).intValue(), ((Double)this.prop.get("Length")).intValue(), ((Double)this.prop.get("ArcWidth")).intValue(), ((Double)this.prop.get("ArcLength")).intValue());
    }

    public Object clone() throws CloneNotSupportedException {
        Shape r = new RoundRectangle();
        r.setColor(this.c);
        r.setFillColor(this.fc);
        r.setPosition(this.p);
        Map newprop = new HashMap();
        Iterator var4 = this.prop.entrySet().iterator();

        while(var4.hasNext()) {
            Entry s = (Entry)var4.next();
            newprop.put(s.getKey(), s.getValue());
        }

        r.setProperties(newprop);
        return r;
    }
}
