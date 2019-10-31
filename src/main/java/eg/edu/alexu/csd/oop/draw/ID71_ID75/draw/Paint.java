package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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


import java.awt.*;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
        triangle.setOnAction(e->current="triangle");
        line.setOnAction(e-> current = "line");
        rectangle.setOnAction(e-> current = "rectangle");
        Circle.setOnAction(e-> current = "circle");
        ellipse.setOnAction(e->current="ellipse");
        square.setOnAction(e->current="square");
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
        menu.getChildren().addAll(addedShapes,save,load,undo,redo);
        shapes.getChildren().addAll(select,line,Circle,ellipse,rectangle,square,triangle,Border,colorPicker,Fill,colorPicker2,delete);
        Canvas canvas = new Canvas(1000,600);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        can.setStyle("-fx-background-color: WHITE");
        can.getChildren().add(canvas);
        root.getChildren().addAll(menu,shapes,can);
        primaryStage.setScene(new Scene(root,Region.USE_PREF_SIZE,Region.USE_PREF_SIZE));
        primaryStage.show();

        AtomicReference<Point> p = new AtomicReference<>(new Point());
        AtomicReference<Point> t2 = new AtomicReference<>(new Point());
        AtomicInteger ct1 = new AtomicInteger();
        AtomicBoolean selected = new AtomicBoolean(false);

        Engine engine = new Engine();
        Shape[] newShape = new Shape[1];
        AtomicInteger ct2 = new AtomicInteger();
        undo.setOnAction(e->{
            engine.undo();
            engine.refresh(graphics);
        });
        redo.setOnAction(e->{
            engine.redo();
            engine.refresh(graphics);
        });
        delete.setOnAction(e->{
            if(current == "select" && newShape[0] != null){
                engine.removeShape(newShape[0]);
                engine.refresh(graphics);
                newShape[0] = null;
                ct2.set(0);
                current = "";
            }
        });
        select.setOnAction(e->{
            current = "select";
        });
        canvas.setOnMousePressed(e->{
            switch (current) {
                case "select":{
                    newShape[0] = engine.checkOnShapes((int)e.getX(),(int)e.getY());
                    if(newShape[0] != null){
                        p.set(new Point((int)e.getX(),(int)e.getY()));
                    }
                    break;
                }
                case "triangle":{
                    if(ct1.get() == 1) {
                        t2.set(new Point((int) e.getX(), (int) e.getY()));
                    } else {
                        p.set(new Point((int) e.getX(), (int) e.getY()));
                    }
                    ct1.getAndIncrement();
                    break;
                }
                default:{
                    p.set(new Point((int) e.getX(), (int) e.getY()));
                    break;
                }
            }
        });

        canvas.setOnMouseDragged(e->{
            switch (current){
                case "resize":{
                    if(newShape[0] != null && newShape[0].getProperties().get("triangle")==1d){
                        Shape l;
                        if(newShape[0].getProperties().get("line")==1d){
                            l = new line();
                        } else if(newShape[0].getProperties().get("circle")==1d){
                            l = new Circle();
                        } else if(newShape[0].getProperties().get("rectangle")==1d){
                            l = new Rectangle();
                        } else if(newShape[0].getProperties().get("ellipse")==1d){
                            l = new Ellipse();
                        } else {
                            l = new Square();
                        }
                        //copyShape(l,newShape[0]);
                        Map<String,Double> secondPoint = newShape[0].getProperties();
                        secondPoint.put("x2",Double.valueOf(e.getX()));
                        secondPoint.put("y2",Double.valueOf(e.getY()));
                        l.setProperties(secondPoint);
                        engine.addShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        if(ct2.get() == 1){
                            ct2.getAndIncrement();
                            engine.removeShape(newShape[0]);
                        }
                    }
                    break;
                }
                case "select":{
                    if(newShape[0] != null){
                        Shape l;
                        if(newShape[0].getProperties().get("type")==1d){
                            l = new line();
                        } else if(newShape[0].getProperties().get("type")==2d){
                            l = new Circle();
                        } else if(newShape[0].getProperties().get("type")==3d){
                            l = new Rectangle();
                        } else if(newShape[0].getProperties().get("type")==4d){
                            l = new Ellipse();
                        } else {
                            l = new Square();
                        }
                        try {
                            l=(Shape)newShape[0].clone();
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                        int diffX=(int)e.getX()- p.get().x;
                        int diffY=(int)e.getY()- p.get().y;
                        p.set(new Point((int)e.getX(),(int)e.getY()));
                        l.setPosition(new Point(l.getPosition().x+diffX,l.getPosition().y+diffY));
                        Map<String,Double> secondPoint = l.getProperties();
                        secondPoint=l.getProperties();
                        secondPoint.put("x2",l.getProperties().get("x2")+diffX);
                        secondPoint.put("y2",l.getProperties().get("y2")+diffY);
                        l.setProperties(secondPoint);
                        engine.addShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        if(ct2.get() == 1){
                            ct2.getAndIncrement();
                            engine.removeShape(newShape[0]);
                        }
                    }
                    break;
                }
                case "line":{
                    line l = new line();
                    getLineValues(l, p.get(),colorPicker);
                    Map<String,Double> secondPoint = new HashMap<String, Double>();
                    secondPoint.put("x2",Double.valueOf(e.getX()));
                    secondPoint.put("y2",Double.valueOf(e.getY()));
                    l.setProperties(secondPoint);
                    engine.addShape(l);
                    engine.refresh(graphics);
                    engine.RemoveLastShape();
                    break;
                }
                case "square":{
                    Square s = new Square();
                    s.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    s.setFillColor(getColor(colorPicker2.getValue()));
                    s.setColor(getColor(colorPicker.getValue()));
                    s.setProperties(length);
                    engine.addShape(s);
                    engine.refresh(graphics);
                    engine.RemoveLastShape();
                    break;
                }
                case "rectangle": {
                    Rectangle r = new Rectangle();
                    r.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    r.setFillColor(getColor(colorPicker2.getValue()));
                    r.setColor(getColor(colorPicker.getValue()));
                    r.setProperties(length);
                    engine.addShape(r);
                    engine.refresh(graphics);
                    engine.removeShape(r);
                    break;
                }
                case "ellipse":{
                    Ellipse r = new Ellipse();
                    r.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    r.setFillColor(getColor(colorPicker2.getValue()));
                    r.setColor(getColor(colorPicker.getValue()));
                    r.setProperties(length);
                    engine.addShape(r);
                    engine.refresh(graphics);
                    engine.removeShape(r);
                    break;
                }
                case "circle":{
                    Circle c = new Circle();
                    c.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    c.setFillColor(getColor(colorPicker2.getValue()));
                    c.setColor(getColor(colorPicker.getValue()));
                    c.setProperties(length);
                    engine.addShape(c);
                    engine.refresh(graphics);
                    engine.RemoveLastShape();
                    break;
                }
                case "triangle":{
                    if(ct1.get() != 2) break;
                    Triangle r=new Triangle();
                    r.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", (double) t2.get().x);
                    length.put("y2", (double) t2.get().y);
                    length.put("x3",  e.getX());
                    length.put("y3",  e.getY());
                    r.setFillColor(getColor(colorPicker2.getValue()));
                    r.setColor(getColor(colorPicker.getValue()));
                    r.setProperties(length);
                    engine.addShape(r);
                    engine.refresh(graphics);
                    engine.removeShape(r);
                    break;
                }
                default:{
                    break;
                }
            }
        });
        canvas.setOnMouseReleased(e->{
            switch (current){
                case "line":{
                    line l = new line();
                    getLineValues(l, p.get(),colorPicker);
                    Map<String,Double> secondPoint = new HashMap<String, Double>();
                    secondPoint.put("x2",Double.valueOf(e.getX()));
                    secondPoint.put("y2",Double.valueOf(e.getY()));
                    l.setProperties(secondPoint);
                    engine.addShape(l);
                    engine.refresh(graphics);
                    break;
                }
                case "square":{
                    Square s = new Square();
                    s.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    s.setFillColor(getColor(colorPicker2.getValue()));
                    s.setColor(getColor(colorPicker.getValue()));
                    s.setProperties(length);
                    engine.addShape(s);
                    engine.refresh(graphics);
                    break;
                }
                case "rectangle": {
                    Rectangle r = new Rectangle();
                    r.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    r.setFillColor(getColor(colorPicker2.getValue()));
                    r.setColor(getColor(colorPicker.getValue()));
                    r.setProperties(length);
                    engine.addShape(r);
                    engine.refresh(graphics);
                    break;
                }
                case "ellipse":{
                    Ellipse r = new Ellipse();
                    r.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    r.setFillColor(getColor(colorPicker2.getValue()));
                    r.setColor(getColor(colorPicker.getValue()));
                    r.setProperties(length);
                    engine.addShape(r);
                    engine.refresh(graphics);
                    break;
                }
                case "circle":{
                    Circle c = new Circle();
                    c.setPosition(p.get());
                    Map<String,Double> length = new HashMap<String, Double>();
                    length.put("x2", Double.valueOf(e.getX()));
                    length.put("y2",Double.valueOf(e.getY()));
                    c.setFillColor(getColor(colorPicker2.getValue()));
                    c.setColor(getColor(colorPicker.getValue()));
                    c.setProperties(length);
                    engine.addShape(c);
                    engine.refresh(graphics);
                    break;
                }
                case "triangle": {
                    if(ct1.get() != 2) break;
                    Triangle r = new Triangle();
                    r.setPosition(p.get());
                    Map<String, Double> length = new HashMap<String, Double>();
                    length.put("x2", (double) t2.get().x);
                    length.put("y2", (double) t2.get().y);
                    length.put("x3", e.getX());
                    length.put("y3", e.getY());
                    r.setFillColor(getColor(colorPicker2.getValue()));
                    r.setColor(getColor(colorPicker.getValue()));
                    r.setProperties(length);
                    engine.addShape(r);
                    engine.refresh(graphics);
                    ct1.set(0);
                    break;
                }
                default:{
                    break;
                }
            }
        });
    }

    private void getLineValues(line l, Point p, ColorPicker colorPicker){
        l.setPosition(p);
        Color v = colorPicker.getValue();
        l.setColor(getColor(v));
    }
    private java.awt.Color getColor(Color v){
        float r = (float)v.getRed();
        float b = (float)v.getBlue();
        float g = (float)v.getGreen();
        float o = (float)v.getOpacity();
        return new java.awt.Color(r,g,b,o);
    }
}
