package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Region;
import javafx.scene.Scene;

import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Paint extends Application implements DrawingEngine{

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(650,700);

        FXGraphics2D name = new FXGraphics2D(canvas.getGraphicsContext2D());
        Circle c= new Circle();
        line l=new line();
        l.setPosition(new Point(100,100));
        Map<String,Double> m;
        m=l.getProperties();
        m.put("x2", 500d);
        m.put("y2", 500d);
        l.setProperties(m);
        l.draw(name);
        c.setPosition(new Point(200,200));
        c.setColor(Color.black);
        c.setFillColor(Color.red);

        m=c.getProperties();
        m.put("radius", 300d);
        c.setProperties(m);
       // c.draw(name);
        Circle x= (Circle) c.clone();
        x.draw(name);

        Rectangle r=new Rectangle();
        r.setPosition(new Point(50,50));
        m=r.getProperties();
        m.put("width", 100d);
        m.put("height", 200d);
        r.setProperties(m);
        r.setColor(Color.yellow);
        r.setFillColor(Color.green);
        r.draw(name);

        Square s=new Square();
        s.setPosition(new Point(250,250));
        m=s.getProperties();
        m.put("length", 100d);
        s.setProperties(m);
        s.setColor(Color.black);
        s.setFillColor(Color.blue);
        s.draw(name);

        Ellipse e=new Ellipse();
        e.setPosition(new Point(400,400));
        m=e.getProperties();
        m.put("width",200d);
        m.put("height",300d);
        e.setProperties(m);
        e.setColor(Color.blue);
        e.setFillColor(Color.yellow);
        e.draw(name);

        Triangle t=new Triangle();
        t.setPosition(new Point(1,500));
        m=t.getProperties();
        m.put("x2",500d);
        m.put("x3",500d);
        m.put("y2",200d);
        m.put("y3",10d);
        t.setProperties(m);
        t.draw(name);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root,Region.USE_PREF_SIZE,Region.USE_PREF_SIZE));
        primaryStage.show();
    }

    @Override
    public void refresh(Graphics canvas) {

    }

    @Override
    public void addShape(Shape shape) {

    }

    @Override
    public void removeShape(Shape shape) {

    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {

    }

    @Override
    public Shape[] getShapes() {
        return new Shape[0];
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        return null;
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }
}
