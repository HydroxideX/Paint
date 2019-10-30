package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.Scene;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;


import java.io.FileInputStream;
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
        Image image = new Image(new FileInputStream("Resources/btn13.png"));

        ChoiceBox addedShapes=new ChoiceBox();
        Button save=new Button();
        save.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn14.png"));
        Button load=new Button();
        load.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn8.png"));
        Button undo=new Button();
        undo.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn9.png"));
        Button redo=new Button();
        redo.setGraphic(new ImageView(image));

        menu.getChildren().addAll(addedShapes,save,load,undo,redo);

        Button line =new Button();
        image = new Image(new FileInputStream("Resources/btn1.png"));
        line.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn2.png"));
        Button Circle =new Button();
        Circle.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn3.png"));
        Button ellipse =new Button();
        ellipse.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn4.png"));
        Button rectangle =new Button();
        rectangle.setGraphic(new ImageView(image));
        Button square =new Button();
        image = new Image(new FileInputStream("Resources/btn5.png"));
        square.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn6.png"));
        Button triangle =new Button();
        triangle.setGraphic(new ImageView(image));

        shapes.getChildren().addAll(line,Circle,ellipse,rectangle,square,triangle);

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
