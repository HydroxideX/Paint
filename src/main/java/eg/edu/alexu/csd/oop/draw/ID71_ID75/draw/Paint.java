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

public class Paint extends Application{

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(500,550);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        FXGraphics2D name = new FXGraphics2D(gc);
        Circle c= new Circle();
        c.setPosition(new Point(200,200));
        c.setColor(Color.red);
        c.draw(name);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
