package eg.edu.alexu.csd.oop.draw;
import eg.edu.alexu.csd.oop.Shapes.Shape;
import eg.edu.alexu.csd.oop.Shapes.Triangle;
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
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Paint extends Application{
    private String current = "select";

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
    private Button customShape = new Button();
    private Button resize = new Button();
    private Button loadClass = new Button();
    private Button delete = new Button();
    private Button move = new Button();

    private ChoiceBox addedShapes = new ChoiceBox();

    private ColorPicker colorPicker = new ColorPicker(Color.BLACK);
    private ColorPicker colorPicker2 = new ColorPicker(Color.WHITE);

    private Label Border = new Label(" Color: ");
    private Label Fill = new Label(" Fill: ");

    public void start(Stage primaryStage) throws Exception {
        FileChooser ChooseFile = new FileChooser();
        ChooseFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Save", "*.json", "*.xml"));
        FileChooser JarChooser = new FileChooser();
        JarChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ClassLoader",  "*.jar"));
        AtomicReference<eg.edu.alexu.csd.oop.Shapes.Shape> loader = new AtomicReference<>();
        AtomicReference<Point> p = new AtomicReference<>(new Point());
        AtomicReference<Point> t2 = new AtomicReference<>(new Point());
        AtomicInteger ct1 = new AtomicInteger();
        eg.edu.alexu.csd.oop.Shapes.Shape[] newShape = new eg.edu.alexu.csd.oop.Shapes.Shape[3];
        AtomicInteger ct2 = new AtomicInteger();
        Engine engine = new Engine();


        VBox root = new VBox();
        root.setSpacing(5);
        
        HBox menu = new HBox();
        menu.setSpacing(3);
        menu.getChildren().addAll(save, load, undo, redo, addedShapes, loadClass);
        
        HBox shapes = new HBox();
        shapes.setSpacing(3);
        shapes.getChildren().addAll(select,move , line, Circle, Ellipse, Rectangle, Square, Triangle, customShape, Border, colorPicker, Fill, colorPicker2, delete, resize);

        HBox can = new HBox();
        Canvas canvas = new Canvas(1000, 600);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        can.setStyle("-fx-background-color: WHITE");
        can.getChildren().add(canvas);

        root.getChildren().addAll(menu, shapes, can);

        final AtomicReference<ArrayList<String>>[] ClassNames = new AtomicReference[]{new AtomicReference<>(engine.getClassNames())};
        for (String className : ClassNames[0].get()) {
            if(className.equals("TwoPointShapes")) continue;
           addedShapes.getItems().add(className);
           addedShapes.setValue(className);
        }

        Image image = new Image(new FileInputStream("Resources/btn1.png"));
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
        image = new Image(new FileInputStream("Resources/btn8.png"));
        undo.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn9.png"));
        redo.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn13.png"));
        save.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn14.png"));
        load.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/btn12.png"));
        delete.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/mouse.png"));
        select.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/move.png"));
        move.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/resize.png"));
        resize.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/customShape.png"));
        customShape.setGraphic(new ImageView(image));
        image = new Image(new FileInputStream("Resources/loadShape.png"));
        loadClass.setGraphic(new ImageView(image));

        customShape.setPrefHeight(29);
        customShape.setMinHeight(29);
        loadClass.setPrefHeight(29);
        loadClass.setMinHeight(29);
        select.setPrefHeight(29);
        select.setMinHeight(29);
        move.setPrefHeight(29);
        move.setMinHeight(29);
        resize.setPrefHeight(29);
        resize.setMinHeight(29);
        customShape.setMinHeight(29);
        colorPicker.setMinHeight(29);
        colorPicker2.setMinHeight(29);
        undo.setMinHeight(31);
        redo.setMinHeight(31);
        addedShapes.setMinHeight(31);
        loadClass.setMinHeight(31);
        addedShapes.setMinWidth(200);

        Fill.setFont(new Font("Arial", 20));
        Border.setFont(new Font("Arial", 20));
        Border.setCenterShape(true);

        primaryStage.setScene(new Scene(root, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE));
        primaryStage.setTitle("Paint");
        primaryStage.setResizable(false);
        image = new Image(new FileInputStream("Resources/paint.png"));
        primaryStage.getIcons().add(image);
        primaryStage.show();

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
        move.setOnAction(e -> {
            current = "move";
            disable(move);
        });
        resize.setOnAction(e -> {
            current = "resize";
            disable(resize);
        });
        loadClass.setOnAction(e -> {
            File selectedFile = JarChooser.showOpenDialog(primaryStage);
            if(selectedFile!=null)
            {

                java.util.jar.JarFile jarfile = null; //jar file path(here sqljdbc4.jar)
                try {
                    jarfile = new java.util.jar.JarFile(selectedFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                assert jarfile != null;
                java.util.Enumeration<java.util.jar.JarEntry> enu= jarfile.entries();
                while(enu.hasMoreElements())
                {
                    current = selectedFile.getName();
                    current = current.substring(0, current.length() - 4);
                    String destdir = "loaded_data/"+current;     //abc is my destination directory
                    java.util.jar.JarEntry je = enu.nextElement();
                    java.io.File fl = new java.io.File(destdir, je.getName());
                    if(!fl.exists())
                    {
                        fl.getParentFile().mkdirs();
                        fl = new java.io.File(destdir, je.getName());
                    }
                    if(je.isDirectory())
                    {
                        continue;
                    }
                    java.io.InputStream is = null;
                    try {
                        is = jarfile.getInputStream(je);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    FileOutputStream fo = null;
                    try {
                        fo = new FileOutputStream(fl);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    while(true)
                    {
                        try {
                            assert is != null;
                            if (!(is.available()>0)) break;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            assert fo != null;
                            fo.write(is.read());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        assert fo != null;
                        fo.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        is.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (selectedFile != null) {
                current = selectedFile.getName();
                current = current.substring(0, current.length() - 4);
                selectedFile=new File("loaded_data/"+current+"/eg/edu/alexu/csd/oop/draw/"+current+".class");
                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                Class d = null;
                try {
                    current = selectedFile.getName();
                    current = current.substring(0, current.length() - 6);
                    try {
                        String path = System.getProperty("user.dir");
                        copyFileUsingChannel(selectedFile, new File(path + "/target/classes/Hybrid/eg/edu/alexu/csd/oop/draw/"+current+".class"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    String path = System.getProperty("user.dir");
                    String pack = "eg.edu.alexu.csd.oop.draw";
                    String Hybridpack = path+"/target/classes/Hybrid";
                    URL[] a = new URL[]{new File(Hybridpack).toURI().toURL()};
                    URLClassLoader c = new URLClassLoader(a);
                    d = c.loadClass(pack+ "." + current);
                } catch (ClassNotFoundException | NoClassDefFoundError ex) {
                    ex.printStackTrace();
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                assert d != null;
                if(eg.edu.alexu.csd.oop.Shapes.Shape.class.isAssignableFrom(d)){
                    addedShapes.getItems().add(current);
                    addedShapes.setValue(current);
                    current="select";
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Wrong Class");
                    alert.setHeaderText("The class you have chosen doesn't implement the required Interface");
                    alert.showAndWait();
                }
            }
        });
        addedShapes.setOnAction(e->{
            select.fire();
            current="select";
        });
        customShape.setOnAction(e -> {
            current= (String) addedShapes.getValue();
            String pack = "eg.edu.alexu.csd.oop.Shapes";
            try {
                eg.edu.alexu.csd.oop.Shapes.Shape shape;
                if(current.equals("Circle")|| current.equals("Triangle") || current.equals("line")
                        || current.equals("Square")|| current.equals("Ellipse")|| current.equals("Rectangle")) {
                    Class cl = Class.forName(pack + "." + current);
                    shape = (eg.edu.alexu.csd.oop.Shapes.Shape) cl.newInstance();
                } else {
                    String path1 = System.getProperty("user.dir");
                    path1 = path1.replace('\\', '/');
                    URLClassLoader x = new URLClassLoader(new URL[]{new File(path1+"/target/classes/Hybrid/").toURI().toURL()});
                    Class cl = x.loadClass(pack + "." + current);
                    shape = (eg.edu.alexu.csd.oop.Shapes.Shape) cl.newInstance();
                }
                newShapeDialogBox shapeDiaglogBox = new newShapeDialogBox(shape,engine,graphics);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException ex) {
                ex.printStackTrace();
            }
            disable(customShape);
            customShape.setDisable(false);
            select.fire();
        });
        undo.setOnAction(e -> {
            engine.undo();
            engine.refresh(graphics);
        });
        redo.setOnAction(e -> {
            engine.redo();
            engine.refresh(graphics);
        });
        save.setOnAction(e -> {
            File selectedFile = ChooseFile.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                engine.save(selectedFile.getPath());
            }
        });
        load.setOnAction(e -> {
            File selectedFile = ChooseFile.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                engine.load(selectedFile.getPath());
                engine.refresh(graphics);
            }
        });
        delete.setOnAction(e -> {
            if (current.equals("select") && newShape[2] != null) {
                engine.removeShape(newShape[2]);
                engine.updateUndo();
                engine.refresh(graphics);
                newShape[2] = null;
                ct2.set(0);
            }
        });
        canvas.setOnMousePressed(e -> {
            switch (current) {
                case "select":
                case "resize":
                case "move": {
                    newShape[0] = engine.checkOnShapes((int) e.getX(), (int) e.getY());
                    if (newShape[0] != null) {
                        if (newShape[0].getProperties().get("type") == 0d && current.equals("resize")) {
                            newShapeDialogBox shapeDiaglogBox = new newShapeDialogBox(newShape[0], engine, graphics);
                            engine.refresh(graphics);
                            disable(customShape);
                            customShape.setDisable(false);
                            break;
                        }
                        try {
                            newShape[1] = (eg.edu.alexu.csd.oop.Shapes.Shape) newShape[0].clone();
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                        p.set(new Point((int) e.getX(), (int) e.getY()));
                        if(!current.equals("select")) {
                            Map<String, Double> secondPoint = new HashMap<>(newShape[1].getProperties());
                            java.awt.Color temp = newShape[1].getColor();
                            newShape[1].setColor(newShape[1].getFillColor());
                            newShape[1].setFillColor(temp);
                            newShape[1].setProperties(secondPoint);
                        }
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
                    if (newShape[1] != null) {
                        eg.edu.alexu.csd.oop.Shapes.Shape l = null;
                        if (newShape[1].getProperties().get("type") == 6d) {
                            l = new Triangle();
                            try {
                                l = (eg.edu.alexu.csd.oop.Shapes.Shape) newShape[1].clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            int diffX = (int) e.getX() - p.get().x;
                            int diffY = (int) e.getY() - p.get().y;
                            Map<String, Double> secondPoint = new HashMap<>(l.getProperties());
                            secondPoint.put("x3", l.getProperties().get("x3") + diffX);
                            secondPoint.put("y3", l.getProperties().get("y3") + diffY);
                            l.setProperties(secondPoint);
                            engine.addTempShape(l);
                            engine.refresh(graphics);
                            engine.RemoveLastShape();
                            try {
                                newShape[1] = (eg.edu.alexu.csd.oop.Shapes.Shape) l.clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            p.get().x = (int) e.getX();
                            p.get().y = (int) e.getY();
                            break;
                        } else if (newShape[1].getProperties().get("type") == 0d) {
                                break;
                        } else {
                            try {
                                l = newShape[1].getClass().newInstance();
                            } catch (InstantiationException | IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }
                        try {
                            l = (eg.edu.alexu.csd.oop.Shapes.Shape) newShape[1].clone();
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
                        engine.addTempShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        try {
                            newShape[1] = (eg.edu.alexu.csd.oop.Shapes.Shape) l.clone();
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                        p.get().x = (int) e.getX();
                        p.get().y = (int) e.getY();
                    }
                    break;
                }
                case "move": {
                    if (newShape[1] != null) {
                        eg.edu.alexu.csd.oop.Shapes.Shape l = null;
                        if (newShape[1].getProperties().get("type") == 6d) {
                            l = new Triangle();
                            try {
                                l = (eg.edu.alexu.csd.oop.Shapes.Shape) newShape[1].clone();
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
                            engine.addTempShape(l);
                            engine.refresh(graphics);
                            engine.RemoveLastShape();
                            try {
                                newShape[1] = (eg.edu.alexu.csd.oop.Shapes.Shape) l.clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            p.get().x = (int) e.getX();
                            p.get().y = (int) e.getY();
                            break;
                        }
                        else if (newShape[1].getProperties().get("type") == 0d) {
                            try {
                                l = newShape[1].getClass().newInstance();
                            } catch (InstantiationException | IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                l = (eg.edu.alexu.csd.oop.Shapes.Shape) newShape[1].clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            int diffX = (int) e.getX() - p.get().x;
                            int diffY = (int) e.getY() - p.get().y;
                            assert l != null;
                            l.setPosition(new Point(l.getPosition().x + diffX, l.getPosition().y + diffY));
                            l.setProperties(newShape[1].getProperties());
                            l.setFillColor(newShape[1].getColor());
                            l.setFillColor(newShape[1].getFillColor());
                            engine.addTempShape(l);
                            engine.refresh(graphics);
                            engine.RemoveLastShape();
                            try {
                                newShape[1] = (eg.edu.alexu.csd.oop.Shapes.Shape) l.clone();
                            } catch (CloneNotSupportedException ex) {
                                ex.printStackTrace();
                            }
                            p.get().x = (int) e.getX();
                            p.get().y = (int) e.getY();
                            break;
                        } else {
                            try {
                                l = newShape[1].getClass().newInstance();
                            } catch (InstantiationException | IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        }
                        try {
                            l = (eg.edu.alexu.csd.oop.Shapes.Shape) newShape[1].clone();
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
                        engine.addTempShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        try {
                            newShape[1] = (eg.edu.alexu.csd.oop.Shapes.Shape) l.clone();
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
                    engine.addTempShape(r);
                    engine.refresh(graphics);
                    engine.removeShape(r);
                    engine.updateUndo();
                    break;
                }
                default: {
                    eg.edu.alexu.csd.oop.Shapes.Shape l;
                    String pack = "eg.edu.alexu.csd.oop.Shapes";
                    try {
                        Class cl = Class.forName(pack + "." + current);
                        l = (eg.edu.alexu.csd.oop.Shapes.Shape) cl.newInstance();
                        l.setPosition(p.get());
                        Map<String, Double> length = new HashMap<>();
                        length.put("x2", e.getX());
                        length.put("y2", e.getY());
                        length.put("released", 0d);
                        l.setFillColor(getColor(colorPicker2.getValue()));
                        l.setColor(getColor(colorPicker.getValue()));
                        l.setProperties(length);
                        engine.addTempShape(l);
                        engine.refresh(graphics);
                        engine.RemoveLastShape();
                        newShape[1] = (Shape) l.clone();
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | CloneNotSupportedException ignored) {
                    }
                    break;
                }
            }
        });
        canvas.setOnMouseReleased(e -> {
            switch (current) {
                case "resize":
                case "move": {
                    if (newShape[1] != null) {
                        if(newShape[1].getProperties().get("type") == 0d && current.equals("resize")) break;
                        Map<String, Double> secondPoint = new HashMap<>(newShape[1].getProperties());
                        java.awt.Color temp=newShape[1].getColor();
                        newShape[1].setColor(newShape[1].getFillColor());
                        newShape[1].setFillColor(temp);
                        secondPoint.put("released", 1d);
                        newShape[1].setProperties(secondPoint);
                        engine.addShape(newShape[1]);
                        engine.refresh(graphics);
                        newShape[2] = newShape[1];
                        newShape[1] = null;
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
                    if (newShape[1] != null) {
                        Map <String,Double> m = new HashMap<>(newShape[1].getProperties());
                        m.put("released",1d);
                        newShape[1].setProperties(m);
                        engine.addShape(newShape[1]);
                        engine.refresh(graphics);
                        newShape[2] = newShape[1];
                        newShape[1] = null;
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
        move.setDisable(false);
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