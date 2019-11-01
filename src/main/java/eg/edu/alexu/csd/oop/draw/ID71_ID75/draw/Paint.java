package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.Scene;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Paint extends Application{
    private String current = "";

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(5);
        HBox menu = new HBox();
        HBox shapes = new HBox();
        HBox can = new HBox();
        menu.setSpacing(3);
        shapes.setSpacing(3);
        Image image = new Image(new FileInputStream("Resources/btn13.png"));
        ChoiceBox addedShapes = new ChoiceBox();
        addedShapes.setValue("Default");
        Button save = new Button();
        save.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn14.png"));
        Button load = new Button();
        load.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn8.png"));
        Button undo = new Button();
        undo.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn9.png"));
        Button redo = new Button();
        redo.setGraphic(new ImageView(image));

        undo.setMinHeight(31);
        redo.setMinHeight(31);
        addedShapes.setMinHeight(31);
        addedShapes.setMinWidth(200);
        Button line = new Button();
        image = new Image(new FileInputStream("Resources/btn1.png"));
        line.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn2.png"));
        Button Circle = new Button();
        Circle.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn3.png"));
        Button Ellipse = new Button();
        Ellipse.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn4.png"));
        Button Rectangle = new Button();
        Rectangle.setGraphic(new ImageView(image));
        Button Square = new Button();
        image = new Image(new FileInputStream("Resources/btn5.png"));
        Square.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn6.png"));
        Button Triangle = new Button();
        Triangle.setGraphic(new ImageView(image));
        Button select = new Button();
        Button customShape = new Button("Custom");
        Button resize = new Button("Resize");
        Button loadClass = new Button("Load Class");
        loadClass.setMinHeight(31);
        Triangle.setOnAction(e -> {
            current = "Triangle";
            Triangle.setDisable(true);
            line.setDisable(false);
            Ellipse.setDisable(false);
            Square.setDisable(false);
            Rectangle.setDisable(false);
            Circle.setDisable(false);
            select.setDisable(false);
            customShape.setDisable(false);
            resize.setDisable(false);
        });
        line.setOnAction(e -> {
            current = "line";
            Triangle.setDisable(false);
            line.setDisable(true);
            Ellipse.setDisable(false);
            Square.setDisable(false);
            Rectangle.setDisable(false);
            Circle.setDisable(false);
            select.setDisable(false);
            customShape.setDisable(false);
            resize.setDisable(false);
        });
        Rectangle.setOnAction(e -> {
            current = "Rectangle";
            Triangle.setDisable(false);
            line.setDisable(false);
            Ellipse.setDisable(false);
            Square.setDisable(false);
            Rectangle.setDisable(true);
            Circle.setDisable(false);
            select.setDisable(false);
            customShape.setDisable(false);
            resize.setDisable(false);
        });
        Circle.setOnAction(e -> {
            current = "Circle";
            Triangle.setDisable(false);
            line.setDisable(false);
            Ellipse.setDisable(false);
            Square.setDisable(false);
            Rectangle.setDisable(false);
            Circle.setDisable(true);
            select.setDisable(false);
            customShape.setDisable(false);
            resize.setDisable(false);
        });
        Ellipse.setOnAction(e -> {
            current = "Ellipse";
            Triangle.setDisable(false);
            line.setDisable(false);
            Ellipse.setDisable(true);
            Square.setDisable(false);
            Rectangle.setDisable(false);
            Circle.setDisable(false);
            select.setDisable(false);
            customShape.setDisable(false);
            resize.setDisable(false);
        });
        Square.setOnAction(e -> {
            current = "Square";
            Triangle.setDisable(false);
            line.setDisable(false);
            Ellipse.setDisable(false);
            Square.setDisable(true);
            Rectangle.setDisable(false);
            Circle.setDisable(false);
            select.setDisable(false);
            customShape.setDisable(false);
            resize.setDisable(false);
        });
        select.setOnAction(e -> {
            current = "select";
            Triangle.setDisable(false);
            line.setDisable(false);
            Ellipse.setDisable(false);
            Square.setDisable(false);
            Rectangle.setDisable(false);
            Circle.setDisable(false);
            select.setDisable(true);
            customShape.setDisable(false);
            resize.setDisable(false);
        });
        resize.setOnAction(e -> {
            current = "resize";
            Triangle.setDisable(false);
            line.setDisable(false);
            Ellipse.setDisable(false);
            Square.setDisable(false);
            Rectangle.setDisable(false);
            Circle.setDisable(false);
            select.setDisable(false);
            customShape.setDisable(false);
            resize.setDisable(true);
        });

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ClassLoader", "*.class", "*.java"));
        AtomicReference<Shape> loader = new AtomicReference<>();

        loadClass.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                current = selectedFile.getName();
                current = current.substring(0, current.length() - 6);
                addedShapes.getItems().add(current);
                addedShapes.setValue(current);
            }
        });

        Label Border = new Label(" Color: ");
        Border.setFont(new Font("Arial", 20));
        Border.setCenterShape(true);
        Label Fill = new Label(" Fill: ");
        Fill.setFont(new Font("Arial", 20));
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        ColorPicker colorPicker2 = new ColorPicker(Color.WHITE);
        colorPicker.setMinHeight(29);
        colorPicker2.setMinHeight(29);
        image = new Image(new FileInputStream("Resources/btn12.png"));
        Button delete = new Button();
        delete.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/mouse.png"));
        select.setGraphic(new ImageView(image));
        select.setMaxHeight(29);
        select.setPrefHeight(29);
        select.setMinHeight(29);
        resize.setMinHeight(29);
        customShape.setMinHeight(29);
        customShape.setOnAction(e -> {
            current = "resize";
            Triangle.setDisable(false);
            line.setDisable(false);
            Ellipse.setDisable(false);
            Square.setDisable(false);
            Rectangle.setDisable(false);
            Circle.setDisable(false);
            select.setDisable(false);
            customShape.setDisable(true);
            resize.setDisable(false);
            current = "load";
        });
        menu.getChildren().addAll(save, load, undo, redo, addedShapes, loadClass);
        shapes.getChildren().addAll(select, line, Circle, Ellipse, Rectangle, Square, Triangle, customShape, Border, colorPicker, Fill, colorPicker2, delete, resize);
        Canvas canvas = new Canvas(1000, 600);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        can.setStyle("-fx-background-color: WHITE");
        can.getChildren().add(canvas);
        root.getChildren().addAll(menu, shapes, can);
        primaryStage.setScene(new Scene(root, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE));
        primaryStage.setTitle("Paint");
        primaryStage.setResizable(false);
        image = new Image(new FileInputStream("Resources/paint.png"));
        primaryStage.getIcons().add(image);
        primaryStage.show();

        AtomicReference<Point> p = new AtomicReference<>(new Point());
        AtomicReference<Point> t2 = new AtomicReference<>(new Point());
        AtomicInteger ct1 = new AtomicInteger();

        Engine engine = new Engine();
        Shape[] newShape = new Shape[1];
        AtomicInteger ct2 = new AtomicInteger();
        undo.setOnAction(e -> {
            engine.undo();
            engine.refresh(graphics);
        });
        redo.setOnAction(e -> {
            engine.redo();
            engine.refresh(graphics);
        });
        FileChooser fileChooser2 = new FileChooser();
        fileChooser2.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Save", "*.json", "*.xml"));
        save.setOnAction(e -> {
            File selectedFile = fileChooser2.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                engine.save(selectedFile.getPath());
            }
        });
        load.setOnAction(e -> {
            File selectedFile = fileChooser2.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                engine.load(selectedFile.getPath());
                engine.refresh(graphics);
            }
        });
        delete.setOnAction(e -> {
            if (current.equals("select") && newShape[0] != null) {
                engine.removeShape(newShape[0]);
                engine.refresh(graphics);
                newShape[0] = null;
                ct2.set(0);
                current = "";
                select.setDisable(false);
            }
        });
        canvas.setOnMousePressed(e -> {
            switch (current) {
                case "select":
                case "resize": {
                    newShape[0] = engine.checkOnShapes((int) e.getX(), (int) e.getY());
                    if (newShape[0] != null) {
                        p.set(new Point((int) e.getX(), (int) e.getY()));
                        Map<String, Double> secondPoint = new HashMap<>(newShape[0].getProperties());
                        secondPoint.put("selected", 1d);
                        newShape[0].setProperties(secondPoint);
                        engine.removeShape(newShape[0]);
                        ct2.getAndIncrement();
                    }
                    break;
                }
                case "Triangle": {
                    if (ct1.get() == 1) {
                        t2.set(new Point((int) e.getX(), (int) e.getY()));
                    } else {
                        p.set(new Point((int) e.getX(), (int) e.getY()));
                    }
                    ct1.getAndIncrement();
                    break;
                }
                default: {
                    p.set(new Point((int) e.getX(), (int) e.getY()));
                    break;
                }
            }
        });

        canvas.setOnMouseDragged(e -> {
            switch (current) {
                case "resize": {
                    if (newShape[0] != null) {
                        Shape l = null;
                        if (newShape[0].getProperties().get("type") == 6d) {
                            l = new Triangle();
                            try {
                                l = (Shape) newShape[0].clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            int diffX = (int) e.getX() - p.get().x;
                            int diffY = (int) e.getY() - p.get().y;
                            Map<String, Double> secondPoint = new HashMap<>(l.getProperties());
                            secondPoint.put("x3", l.getProperties().get("x3") + diffX);
                            secondPoint.put("y3", l.getProperties().get("y3") + diffY);
                            l.setProperties(secondPoint);
                            engine.addShape(l);
                            engine.refresh(graphics);
                            engine.RemoveLastShape();
                            try {
                                newShape[0] = (Shape) l.clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            p.get().x = (int) e.getX();
                            p.get().y = (int) e.getY();
                            break;
                        } else {
                            try {
                                l = newShape[0].getClass().newInstance();
                            } catch (InstantiationException | IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }
                        try {
                            l = (Shape) newShape[0].clone();
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                        int diffX = (int) e.getX() - p.get().x;
                        int diffY = (int) e.getY() - p.get().y;
                        assert l != null;
                        Map<String, Double> secondPoint = new HashMap<>(l.getProperties());
                        secondPoint.put("x2", l.getProperties().get("x2") + diffX);
                        secondPoint.put("y2", l.getProperties().get("y2") + diffY);
                        l.setProperties(secondPoint);
                        engine.addShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        try {
                            newShape[0] = (Shape) l.clone();
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                        p.get().x = (int) e.getX();
                        p.get().y = (int) e.getY();
                    }
                    break;
                }
                case "select": {
                    if (newShape[0] != null) {
                        Shape l = null;
                        if (newShape[0].getProperties().get("type") == 6d) {
                            l = new Triangle();
                            try {
                                l = (Shape) newShape[0].clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            int diffX = (int) e.getX() - p.get().x;
                            int diffY = (int) e.getY() - p.get().y;
                            l.setPosition(new Point(l.getPosition().x + diffX, l.getPosition().y + diffY));
                            Map<String, Double> secondPoint = new HashMap<>(l.getProperties());
                            secondPoint.put("x2", l.getProperties().get("x2") + diffX);
                            secondPoint.put("y2", l.getProperties().get("y2") + diffY);
                            secondPoint.put("x3", l.getProperties().get("x3") + diffX);
                            secondPoint.put("y3", l.getProperties().get("y3") + diffY);
                            l.setProperties(secondPoint);
                            engine.addShape(l);
                            engine.refresh(graphics);
                            engine.RemoveLastShape();
                            try {
                                newShape[0] = (Shape) l.clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            p.get().x = (int) e.getX();
                            p.get().y = (int) e.getY();
                            break;
                        } else {
                            try {
                                l = newShape[0].getClass().newInstance();
                            } catch (InstantiationException | IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }
                        try {
                            l = (Shape) newShape[0].clone();
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                        int diffX = (int) e.getX() - p.get().x;
                        int diffY = (int) e.getY() - p.get().y;
                        assert l != null;
                        l.setPosition(new Point(l.getPosition().x + diffX, l.getPosition().y + diffY));
                        Map<String, Double> secondPoint = new HashMap<>(l.getProperties());
                        secondPoint.put("x2", l.getProperties().get("x2") + diffX);
                        secondPoint.put("y2", l.getProperties().get("y2") + diffY);
                        l.setProperties(secondPoint);
                        engine.addShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        try {
                            newShape[0] = (Shape) l.clone();
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                        p.get().x = (int) e.getX();
                        p.get().y = (int) e.getY();
                    }
                    break;
                }
                case "Triangle": {
                    if (ct1.get() != 2) break;
                    Triangle r = new Triangle();
                    r.setPosition(p.get());
                    Map<String, Double> length = new HashMap<>();
                    length.put("x2", (double) t2.get().x);
                    length.put("y2", (double) t2.get().y);
                    length.put("x3", e.getX());
                    length.put("y3", e.getY());
                    length.put("released", 0d);
                    r.setFillColor(getColor(colorPicker2.getValue()));
                    r.setColor(getColor(colorPicker.getValue()));
                    r.setProperties(length);
                    engine.addShape(r);
                    engine.refresh(graphics);
                    engine.removeShape(r);
                    break;
                }
                case "load": {
                    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                    try {
                        String pack = "eg.edu.alexu.csd.oop.draw.ID71_ID75.draw";
                        Class cl = classLoader.loadClass(pack + "." + addedShapes.getValue().toString());
                        loader.set((Shape) cl.newInstance());
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                        break;
                    }
                    Shape l = null;
                    Shape x = loader.get();
                    try {
                        l = x.getClass().newInstance();
                    } catch (InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    assert l != null;
                    l.setPosition(p.get());
                    Map<String, Double> length = new HashMap<>();
                    length.put("x2", e.getX());
                    length.put("y2", e.getY());
                    length.put("released", 0d);
                    l.setFillColor(getColor(colorPicker2.getValue()));
                    l.setColor(getColor(colorPicker.getValue()));
                    l.setProperties(length);
                    engine.addShape(l);
                    engine.refresh(graphics);
                    engine.RemoveLastShape();
                    newShape[0] = l;
                    break;
                }
                default: {
                    Shape l;
                    String pack = "eg.edu.alexu.csd.oop.draw.ID71_ID75.draw";
                    try {
                        Class cl = Class.forName(pack + "." + current);
                        l = (Shape) cl.newInstance();
                        l.setPosition(p.get());
                        Map<String, Double> length = new HashMap<>();
                        length.put("x2", e.getX());
                        length.put("y2", e.getY());
                        length.put("released", 0d);
                        l.setFillColor(getColor(colorPicker2.getValue()));
                        l.setColor(getColor(colorPicker.getValue()));
                        l.setProperties(length);
                        engine.addShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        newShape[0] = l;
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ignored) {
                    }
                    break;
                }
            }
        });
        canvas.setOnMouseReleased(e -> {
            switch (current) {
                case "resize":
                case "select": {
                    if (newShape[0] != null) {
                        Map<String, Double> secondPoint = new HashMap<>(newShape[0].getProperties());
                        secondPoint.put("selected", 1d);
                        newShape[0].setProperties(secondPoint);
                        engine.addShape(newShape[0]);
                        engine.refresh(graphics);
                    }
                    break;
                }
                case "Triangle": {
                    if (ct1.get() != 2) break;
                    Triangle r = new Triangle();
                    r.setPosition(p.get());
                    Map<String, Double> length = new HashMap<>();
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
                default: {
                    if (newShape[0] != null) {
                        engine.addShape(newShape[0]);
                        engine.refresh(graphics);
                        break;
                    }
                }
            }
        });
        root.setOnKeyPressed(ke -> {
            KeyCode kc = ke.getCode();
            if(kc.equals(KeyCode.DELETE))
            {
                delete.fire();
            }
        });
    }
    private java.awt.Color getColor(Color v){
        float r = (float)v.getRed();
        float b = (float)v.getBlue();
        float g = (float)v.getGreen();
        float o = (float)v.getOpacity();
        return new java.awt.Color(r,g,b,o);
    }
}
