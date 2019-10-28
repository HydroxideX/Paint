package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import org.jfree.fx.FXGraphics2D;
import org.jfree.*;

import java.awt.*;

public class test extends Application{

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas();
        FXGraphics2D name = new FXGraphics2D(canvas);
        VBox vbox =  new VBox();
        vbox.setStyle("-fx-background-color: white;");
        primaryStage.setTitle("SimpleJAVAFX");
        primaryStage.setScene(new Scene(vbox,canvas.getWidth()-10,canvas.getHeight()+30));
        primaryStage.show();
    }
}
