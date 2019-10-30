package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.Scene;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;


import java.util.List;
import java.util.Map;

public class Paint extends Application{

    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        HBox menu=new HBox();
        HBox shapes=new HBox();
        HBox can=new HBox();

        ChoiceBox addedShapes=new ChoiceBox();
        Button save=new Button("Save");
        Button load=new Button("load");
        Button undo=new Button("Undo");
        Button redo=new Button("Redo");

        menu.getChildren().addAll(addedShapes,save,load,undo,redo);



        Canvas canvas = new Canvas(650,700);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        can.getChildren().add(canvas);
        root.getChildren().addAll(menu,shapes,can);
        primaryStage.setScene(new Scene(root,Region.USE_PREF_SIZE,Region.USE_PREF_SIZE));
        primaryStage.show();
    }
    Shape[] arrayOfShapes = new Shape[100000];
    int index = 0;


}
