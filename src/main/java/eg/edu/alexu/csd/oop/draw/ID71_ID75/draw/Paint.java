package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Paint extends Application{

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
        Map<String,Double> m=new HashMap<String ,Double>();
        m=l.getProperties();
        m.put("x2",new Double(500));
        m.put("y2",new Double(500));
        l.setProperties(m);
        l.draw(name);
        c.setPosition(new Point(200,200));
        c.setColor(Color.black);
        c.setFillColor(Color.red);

        m=c.getProperties();
        m.put("radius", new Double(300));
        c.setProperties(m);
        c.draw(name);

        Rectangle r=new Rectangle();
        r.setPosition(new Point(50,50));
        m=r.getProperties();
        m.put("width",new Double(100));
        m.put("height",new Double(200));
        r.setProperties(m);
        r.setColor(Color.yellow);
        r.setFillColor(Color.green);
        r.draw(name);

        Square s=new Square();
        s.setPosition(new Point(250,250));
        m=s.getProperties();
        m.put("length",new Double(100));
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
}
