package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Paint extends Application{
    private String current = "";

    public static void main(String[] args){
        launch(args);
    }
    private Button save = new Button();
    private Button load = new Button();
    private Button undo = new Button();
    private Button redo = new Button();
    private Button line = new Button();
    private Button Circle = new Button();
    private Button Ellipse = new Button();
    private Button select = new Button();
    private Button Rectangle = new Button();
    private Button Square = new Button();
    private Button Triangle = new Button();
    private Button customShape = new Button("Custom");
    private Button resize = new Button("Resize");

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
        Engine engine = new Engine();
        ArrayList<String >ClassNames=engine.getClassNames();
        for (String className : ClassNames) {
           addedShapes.getItems().add(className);
           addedShapes.setValue(className);
        }
        save.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn14.png"));
        load.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn8.png"));
        undo.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn9.png"));
        redo.setGraphic(new ImageView(image));
        undo.setMinHeight(31);
        redo.setMinHeight(31);
        addedShapes.setMinHeight(31);
        addedShapes.setMinWidth(200);
        image = new Image(new FileInputStream("Resources/btn1.png"));
        line.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn2.png"));
        Circle.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn3.png"));
        Ellipse.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn4.png"));
        Rectangle.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn5.png"));
        Square.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn6.png"));
        Triangle.setGraphic(new ImageView(image));
        Button loadClass = new Button("Load Class");
        loadClass.setMinHeight(31);
        Triangle.setOnAction(e -> {
            current = "Triangle";
            disable(Triangle);
        });
        line.setOnAction(e -> {
            current = "line";
            disable(line);
        });
        Rectangle.setOnAction(e -> {
            current = "Rectangle";
            disable(Rectangle);
        });
        Circle.setOnAction(e -> {
            current = "Circle";
            disable(Circle);
        });
        Ellipse.setOnAction(e -> {
            current = "Ellipse";
            disable(Ellipse);
        });
        Square.setOnAction(e -> {
            current = "Square";
            disable(Square);
        });
        select.setOnAction(e -> {
            current = "select";
            disable(select);
        });
        resize.setOnAction(e -> {
            current = "resize";
            disable(resize);
        });

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ClassLoader", "*.class", "*.java"));
        AtomicReference<Shape> loader = new AtomicReference<>();

        loadClass.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                Class x = null;
                try {
                    current = selectedFile.getName();
                    current = current.substring(0, current.length() - 6);
                    try {
                        copyFileUsingChannel(selectedFile, new File("target/classes/eg/edu/alexu/csd/oop/draw/ID71_ID75/draw/"+current+".class"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    String pack = "eg.edu.alexu.csd.oop.draw.ID71_ID75.draw";
                    x= classLoader.loadClass(pack + "." + current);
                } catch (ClassNotFoundException | NoClassDefFoundError ex) {
                    ex.printStackTrace();
                }
                if(Shape.class.isAssignableFrom(x)){
                    addedShapes.getItems().add(current);
                    addedShapes.setValue(current);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Wrong Class");
                    alert.setHeaderText("The class you have chosen doesn't implement the required Interface");
                    alert.showAndWait();
                }
            }
        });
        //01011799537
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
            disable(customShape);
            current= (String) addedShapes.getValue();
            if(!current.equals("Triangle"))
            current = "load";
            else current="Triangle";
        });
        addedShapes.setOnAction(e->{
            disable(select);
            select.fire();
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
                        secondPoint.put("released", 0d);
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
                        ex.printStackTrace();
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
                    Map<String, Double> length = new HashMap<>(l.getProperties());
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
                        secondPoint.put("released", 1d);
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
                        newShape[0] = null;
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

    private void disable(Button x) {
        Triangle.setDisable(false);
        line.setDisable(false);
        Ellipse.setDisable(false);
        Square.setDisable(false);
        Rectangle.setDisable(false);
        Circle.setDisable(false);
        select.setDisable(false);
        customShape.setDisable(false);
        resize.setDisable(false);
        x.setDisable(true);
    }

    private java.awt.Color getColor(Color v){
        float r = (float)v.getRed();
        float b = (float)v.getBlue();
        float g = (float)v.getGreen();
        float o = (float)v.getOpacity();
        return new java.awt.Color(r,g,b,o);
    }
    private void copyFileUsingChannel(File src, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(src).getChannel();
            destinationChannel = new FileOutputStream(dest).getChannel();
            destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            assert sourceChannel != null;
            sourceChannel.close();
            assert destinationChannel != null;
            destinationChannel.close();
        }
    }
}