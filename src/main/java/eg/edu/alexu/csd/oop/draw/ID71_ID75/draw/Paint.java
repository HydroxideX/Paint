package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import sun.awt.resources.awt;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.DataOutput;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paint extends Application{
    String current = "";

    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(5);
        HBox menu=new HBox();
        HBox shapes=new HBox();
        HBox can=new HBox();
        menu.setSpacing(3);
        shapes.setSpacing(3);
        Image image = new Image(new FileInputStream("Resources/btn13.png"));
        ChoiceBox addedShapes=new ChoiceBox();
        addedShapes.setValue("Default");
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
        undo.setMinHeight(31);
        redo.setMinHeight(31);
        addedShapes.setMinHeight(31);
        addedShapes.setMinWidth(200);
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
        line.setOnAction(e->{
            current = "line";
        });

        Label Border=new Label(" Color: ");
        Border.setFont(new Font("Arial", 20));
        Border.setCenterShape(true);
        Label Fill=new Label(" Fill: ");
        Fill.setFont(new Font("Arial", 20));
        ColorPicker colorPicker=new ColorPicker();
        ColorPicker colorPicker2=new ColorPicker();

        colorPicker.setMinHeight(29);
        colorPicker2.setMinHeight(29);
        image = new Image(new FileInputStream("Resources/btn12.png"));
        Button delete =new Button();
        delete.setGraphic(new ImageView(image));
        Button select=new Button();
        image = new Image(new FileInputStream("Resources/mouse.png"));
        select.setGraphic(new ImageView(image));
        select.setMaxHeight(29);
        select.setPrefHeight(29);
        select.setMinHeight(29);

        Engine engine = new Engine();
        shapes.getChildren().addAll(select,line,Circle,ellipse,rectangle,square,triangle,Border,colorPicker,Fill,colorPicker2,delete);
        Canvas canvas = new Canvas(1000,600);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        can.setStyle("-fx-background-color: WHITE");
        can.getChildren().add(canvas);
        Point p = new Point();
        root.getChildren().addAll(menu,shapes,can);
        primaryStage.setScene(new Scene(root,Region.USE_PREF_SIZE,Region.USE_PREF_SIZE));
        primaryStage.show();
        canvas.setOnMousePressed(e->{
            switch (current) {
                case "select":{
                    break;
                }
                default:{
                    p.x = (int)e.getX();
                    p.y = (int)e.getY();
                    break;
                }
            }
        });

        canvas.setOnMouseDragged(e->{
            switch (current){
                case "line":{
                    line l = new line();
                    l.setPosition(p);
                    Map<String,Double> secondPoint = new HashMap<String, Double>();
                    secondPoint.put("x2",Double.valueOf(e.getX()));
                    secondPoint.put("y2",Double.valueOf(e.getY()));
                    l.setProperties(secondPoint);
                    Color v = colorPicker.getValue();
                    int r = (int)v.getRed();
                    int b = (int)v.getBlue();
                    int g = (int)v.getGreen();
                    int o = (int)v.getOpacity();
                    java.awt.Color nw = new java.awt.Color(r,g,b,o);
                    l.setFillColor(nw);
                    l.setColor(nw);
                    engine.removeShape(l);
                    engine.addShape(l);
                    engine.refresh(graphics);
                }
                case "square":{
                    break;
                }
                case "rectangle": {
                    break;
                }
                case "ellipse":{
                    break;
                }
                case "circle":{
                    break;
                } default:{
                    break;
                }
            }
        });
    }
}
