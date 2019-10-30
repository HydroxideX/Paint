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
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Canvas canvas = new Canvas(650,700);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root,Region.USE_PREF_SIZE,Region.USE_PREF_SIZE));
        primaryStage.show();
    }
    Shape[] arrayOfShapes = new Shape[100000];
    int index = 0;

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
