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
        Canvas canvas = new Canvas(500,550);
        FXGraphics2D name = new FXGraphics2D(canvas.getGraphicsContext2D());
        Circle c= new Circle();
        c.setPosition(new Point(200,200));
        c.setColor(Color.black);
        c.setFillColor(Color.red);
        Map<String,Double> m=new HashMap<String ,Double>();
        m=c.getProperties();
        m.put("radius", new Double(300));
        c.setProperties(m);
        c.draw(name);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
