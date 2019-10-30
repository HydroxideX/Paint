package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Region;
import javafx.scene.Scene;

import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.util.HashMap;
import java.util.Map;

public class Paint extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(650, 700);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE));
        primaryStage.show();
    }
}
