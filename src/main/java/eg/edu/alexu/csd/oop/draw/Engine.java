package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.shapes.Shape;
import eg.edu.alexu.csd.oop.utils.shapesDetector;
import eg.edu.alexu.csd.oop.filemanagement.File;
import eg.edu.alexu.csd.oop.filemanagement.FileJson;
import eg.edu.alexu.csd.oop.filemanagement.FileXML;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;


public class Engine implements DrawingEngine {
    private int size = 1000;
    private Shape[] arrayOfShapes;
    private Shape[][] UndoArray;

    private int index = 0;
    private int UndoIndex = 0;
    private int maxIndex = 0;
    private int UndomaxIndex = 0;

    public void setIndex(int value){
        index = value;
    }
    public int getIndex(){
        return index;
    }

    public void setMaxIndex(int value){
        maxIndex = value;
    }

    public int getUdnoMaxIndex(){
        return UndomaxIndex;
    }

    public void setUndoMaxIndex(int value){
        UndomaxIndex = value;
    }

    public int getMaxIndex(){
        return maxIndex;
    }

    public int getUndoIndex(){
        return UndoIndex;
    }

    public void setUndoIndex(int value){
        UndoIndex = value;
    }

    public void setSize(int value){
        size = value;
    }

    public int getSize(){
        return size;
    }

    public Shape[] getArrayOfShapes(){
        return arrayOfShapes;
    }

    public void setArrayOfShapes(Shape[] newArray){
        arrayOfShapes = newArray;
    }

    Engine(){
        arrayOfShapes = new Shape[size];
        UndoArray = new Shape[1000][size];
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }

    private List<Class<? extends Shape>> SupportedShapes = null;
    private ArrayList<String> ClassNames = new ArrayList<>();

    ArrayList<String> getClassNames(){
        try {
            SupportedShapes.clear();
            ClassNames.clear();
        }catch (NullPointerException ignored){
        }
        try {
            getSupportedShapes();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ClassNames;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() throws ClassNotFoundException {
        SupportedShapes = new ArrayList<>();
        java.io.File dir = new java.io.File("target/classes/eg/edu/alexu/csd/oop/shapes");
        java.io.File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (java.io.File child : directoryListing) {
                String current = child.getName();
                if(current.equals("TwoPointShapes.class"))continue;
                current = current.substring(0, current.length() - 6);
                String pack = "eg.edu.alexu.csd.oop.shapes";
                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                Class cl = classLoader.loadClass(pack + "." + current);
                if (Shape.class.isAssignableFrom(cl) && !current.equals("Shape")) {
                    SupportedShapes.add(cl);
                    ClassNames.add(current);
                }
            }
        }
        return SupportedShapes;
    }

    @Override
    public Shape[] getShapes() {
        Shape[] newarrayOfShapes = new Shape[index];
        System.arraycopy(arrayOfShapes, 0, newarrayOfShapes, 0, index);
        return newarrayOfShapes;
    }

    @Override
    public void refresh (Graphics canvas) {
        javax.swing.DebugGraphics x = new DebugGraphics(canvas);
        try{
            canvas.setColor(Color.WHITE);
            ((Graphics2D)canvas).setStroke(new BasicStroke(2.0F));
            canvas.fillRect(0, 0, 10000, 10000);
            for (int i = 0; i < index; i++) {
                arrayOfShapes[i].draw(canvas);
            }
            maxIndex = max(index, maxIndex);
        } catch (NullPointerException ignored){
            for (int i = 0; i < index; i++) {
                arrayOfShapes[i].draw(x);
            }
        }
    }

    @Override
    public void addShape(Shape shape) {
        arrayOfShapes[index] = shape;
        index++;
        maxIndex = index;
        updateUndo();
    }

    void addTempShape(Shape shape) {
        arrayOfShapes[index] = shape;
        index++;
    }

    @Override
    public void removeShape(Shape shape) {
        boolean removed = false;
        for (int i = 0; i < index; i++) {
            if (arrayOfShapes[i] == shape) {
                removed = true;
                for (int j = i + 1; j < size; j++) {
                    if (arrayOfShapes[j - 1] == null) break;
                    arrayOfShapes[j - 1] = arrayOfShapes[j];
                }
                arrayOfShapes[size - 1] = null;
                break;
            }
        }
        if (removed) index--;
        maxIndex = index;
    }

    void removeLastShape() {
        index--;
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        for (int i = 0; i < index; i++) {
            if (arrayOfShapes[i] == oldShape) {
                arrayOfShapes[i] = newShape;
                updateUndo();
                break;
            }
        }
    }

    public void updateUndo() {
        if(UndoIndex == 20){
            for(int i = 0;i<20;i++){
                System.arraycopy(UndoArray[i+1], 0, UndoArray[i], 0, size);
            }
            System.arraycopy(arrayOfShapes, 0, UndoArray[UndoIndex], 0, size);
        } else {
            UndoIndex++;
            System.arraycopy(arrayOfShapes, 0, UndoArray[UndoIndex], 0, size);
        }
        UndomaxIndex = UndoIndex;
    }

    @Override
    public void undo() {
        if(UndomaxIndex-UndoIndex == 20) return;
        if (UndoIndex > 0) {
            UndoIndex--;
            System.arraycopy(UndoArray[UndoIndex], 0, arrayOfShapes, 0, size);
            int i = 0;
            while (arrayOfShapes[i] != null) {
                i++;
            }
            index = i;
        }
    }

    @Override
    public void redo() {
        if (UndoIndex < UndomaxIndex) {
            UndoIndex++;
            System.arraycopy(UndoArray[UndoIndex], 0, arrayOfShapes, 0, size);
            int i = 0;
            while (arrayOfShapes[i] != null) {
                i++;
            }
            index = i;
        }
    }

    Shape checkOnShapes(int x, int y) {
        shapesDetector shapesDetector = new shapesDetector();
        for (int i = index - 1; i >= 0; i--) {
            Map<String, Double> secondPoint = new HashMap<>(arrayOfShapes[i].getProperties());
            secondPoint.putIfAbsent("type", 0d);
            arrayOfShapes[i].setProperties(secondPoint);
            if (arrayOfShapes[i].getProperties().get("type") == 0d) {
                Point p1 = arrayOfShapes[i].getPosition();
                double dist = Math.sqrt(Math.pow(p1.x - x, 2) + Math.pow(p1.y - y, 2));
                if (dist <= 50) return arrayOfShapes[i];
            } else if (arrayOfShapes[i].getProperties().get("type").intValue() == 1) {
                Point p1 = new Point(arrayOfShapes[i].getPosition());
                Point p2 = new Point(arrayOfShapes[i].getProperties().get("x2").intValue(),
                        arrayOfShapes[i].getProperties().get("y2").intValue());
                if(Math.abs(p1.x-p2.x) < 20)p1.x+=50;
                if(Math.abs(p1.y-p2.y) < 20)p1.y+=50;
                double[] ar = shapesDetector.pointsToLine(p1, p2);
                if (Math.abs(-ar[0] * x + ar[1] * y - ar[2]) < 50 &&
                        (x<=Math.max(p1.x,p2.x)&&x>=Math.min(p1.x,p2.x))&&(y<=Math.max(p1.y,p2.y)&&y>=Math.min(p1.y,p2.y))) {
                    return arrayOfShapes[i];
                }
            } else if (arrayOfShapes[i].getProperties().get("type") == 6d) {
                if (shapesDetector.isInside(arrayOfShapes[i].getPosition().x, arrayOfShapes[i].getPosition().y,
                        arrayOfShapes[i].getProperties().get("x2").intValue(), arrayOfShapes[i].getProperties().get("y2").intValue(),
                        arrayOfShapes[i].getProperties().get("x3").intValue(), arrayOfShapes[i].getProperties().get("y3").intValue(),
                        x, y)) {
                    return arrayOfShapes[i];
                }
            } else {
                Point p1 = new Point(arrayOfShapes[i].getPosition());
                Point p2 = new Point(arrayOfShapes[i].getProperties().get("x2").intValue(),
                        arrayOfShapes[i].getProperties().get("y2").intValue());
                if ((x <= p1.x && y <= p1.y && y >= p2.y && x >= p2.x)
                        || (x >= p1.x && y <= p1.y && y >= p2.y && x <= p2.x)
                        || (x >= p1.x && y >= p1.y && y <= p2.y && x <= p2.x)
                        || (x <= p1.x && y >= p1.y && y <= p2.y && x >= p2.x)) {
                    return arrayOfShapes[i];
                }
            }
        }
        return null;
    }

    public void save(String path) {
        File saveAndLoad;
        if (path.contains(".xml")||path.contains(".XmL")||path.contains("Xml")) {
            saveAndLoad = new FileXML(this);
            saveAndLoad.save(arrayOfShapes,path);


        } else {
            saveAndLoad = new FileJson(this);
            saveAndLoad.save(arrayOfShapes,path);
        }
    }

    @Override
    public void load(String path) {
        File fileSaveAndLoad;
        if (path.contains(".xml")||path.contains(".XmL")||path.contains("Xml")) {
            fileSaveAndLoad = new FileXML(this);
            fileSaveAndLoad.load(path);
        } else {
            fileSaveAndLoad = new FileJson(this);
            fileSaveAndLoad.load(path);
        }
    }
}