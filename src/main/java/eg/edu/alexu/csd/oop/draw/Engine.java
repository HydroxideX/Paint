package eg.edu.alexu.csd.oop.draw;

import javax.swing.*;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileReader;
import java.io.IOException;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;


public class Engine implements DrawingEngine {
    private int size = 1000;
    private eg.edu.alexu.csd.oop.draw.Shape[] arrayOfShapes;
    private eg.edu.alexu.csd.oop.draw.Shape[][] UndoArray;

    int index = 0;
    private int UndoIndex = 0;
    private int maxIndex = 0;
    private int UndomaxIndex = 0;
    public Engine(){
        arrayOfShapes = new eg.edu.alexu.csd.oop.draw.Shape[size];
        UndoArray = new eg.edu.alexu.csd.oop.draw.Shape[22][size];
    }
    private int max(int a, int b) {
        return Math.max(a, b);
    }

    private List<Class<? extends eg.edu.alexu.csd.oop.draw.Shape>> SupportedShapes = null;
    private ArrayList<String> ClassNames = new ArrayList<>();

    ArrayList<String> getClassNames() throws ClassNotFoundException {
        SupportedShapes = getSupportedShapes();
        return ClassNames;
    }

    @Override
    public void refresh(Graphics canvas) {
        javax.swing.DebugGraphics x= new DebugGraphics(canvas);
        try{
            canvas.setColor(Color.WHITE);
            canvas.fillRect(0, 0, 10000, 10000);
            for (int i = 0; i < index; i++) {
                arrayOfShapes[i].draw(canvas);
                arrayOfShapes[i].draw(x);
            }
            maxIndex = max(index, maxIndex);
        }catch (NullPointerException ignored){
            for (int i = 0; i < index; i++) {
                arrayOfShapes[i].draw(x);
            }
        }
    }

    @Override
    public void addShape(eg.edu.alexu.csd.oop.draw.Shape shape) {
        if(shape.getPosition() == null){
            shape.setPosition(new Point(0,0));
        }
        if(shape.getColor() == null){
            shape.setColor(Color.red);
        }
        if(shape.getFillColor()== null){
            shape.setFillColor(Color.black);
        }
        arrayOfShapes[index] = shape;
        index++;
        maxIndex = index;
        updateUndo();
    }

    void addTempShape(eg.edu.alexu.csd.oop.draw.Shape shape) {
        arrayOfShapes[index] = shape;
        index++;
    }

    @Override
    public void removeShape(eg.edu.alexu.csd.oop.draw.Shape shape) {
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
        updateUndo();
    }


    public void removeShape2(eg.edu.alexu.csd.oop.draw.Shape shape) {
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

    void RemoveLastShape() {
        index--;
    }

    @Override
    public void updateShape(eg.edu.alexu.csd.oop.draw.Shape oldShape, eg.edu.alexu.csd.oop.draw.Shape newShape) {
        for (int i = 0; i < index; i++) {
            if (arrayOfShapes[i] == oldShape) {
                arrayOfShapes[i] = newShape;
                updateUndo();
                break;
            }
        }
    }

    @Override
    public eg.edu.alexu.csd.oop.draw.Shape[] getShapes() {
        Shape [] newarrayOfShapes = new Shape[index];
        for(int i = 0 ;i<index;i++) newarrayOfShapes[i] = arrayOfShapes[i];
        return newarrayOfShapes;
    }

    @Override
    public List<Class<? extends eg.edu.alexu.csd.oop.draw.Shape>> getSupportedShapes() throws ClassNotFoundException {
        SupportedShapes = new ArrayList<>();
        File dir = new File("target/classes/eg/edu/alexu/csd/oop/draw");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String current = child.getName();
                current = current.substring(0, current.length() - 6);
                String pack = "eg.edu.alexu.csd.oop.draw";
                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                Class cl = classLoader.loadClass(pack + "." + current);
                if (eg.edu.alexu.csd.oop.draw.Shape.class.isAssignableFrom(cl) && !current.equals("Shape")) {
                    SupportedShapes.add(cl);
                    ClassNames.add(current);
                }
            }
        }
        return SupportedShapes;
    }

    void updateUndo() {
        if(UndoIndex == 20){
            for(int i = 0;i<20;i++){
                System.arraycopy(UndoArray[i+1], 0, UndoArray[i], 0, size);
            }
        } else {
            UndoIndex++;
            System.arraycopy(arrayOfShapes, 0, UndoArray[UndoIndex], 0, size);
        }
        UndomaxIndex = UndoIndex;
    }

    @Override
    public void undo() {
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

    private double[] pointsToLine(Point p1, Point p2) {
        double[] ar = new double[3];
        ar[0] = (double) (p2.y - p1.y) / (p2.x - p1.x);
        ar[1] = 1;
        ar[2] = (p1.y - ar[0] * p1.x);
        return ar;
    }


    private static double area(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    private static boolean isInside(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y) {
        double A = area(x1, y1, x2, y2, x3, y3);
        double A1 = area(x, y, x2, y2, x3, y3);
        double A2 = area(x1, y1, x, y, x3, y3);
        double A3 = area(x1, y1, x2, y2, x, y);
        return (A == A1 + A2 + A3);
    }

    eg.edu.alexu.csd.oop.draw.Shape checkOnShapes(int x, int y) {
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
                double[] ar = pointsToLine(p1, p2);
                if (Math.abs(-ar[0] * x + ar[1] * y - ar[2]) < 50) {
                    return arrayOfShapes[i];
                }
            } else if (arrayOfShapes[i].getProperties().get("type") == 6d) {
                if (isInside(arrayOfShapes[i].getPosition().x, arrayOfShapes[i].getPosition().y,
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
        if (path.contains(".xml")||path.contains(".XmL")||path.contains("Xml")) {
            try {
                XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
                xmlEncoder.writeObject(arrayOfShapes);
                xmlEncoder.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (path.contains(".json") || path.contains(".JsOn")) {
            File file2 = new File(path);
            try {
                FileWriter file = new FileWriter(file2);
                file.write('[');
                int i = 0;
                while (arrayOfShapes[i] != null) {
                    file.write('{');
                    file.write('"' + arrayOfShapes[i].getClass().getName() + '"');
                    file.write(':');
                    file.write('{');
                    file.write('"');
                    file.write("x1");
                    file.write('"');
                    file.write(':');
                    file.write('"');
                    file.write(String.valueOf(arrayOfShapes[i].getPosition().x));
                    file.write('"');
                    file.write(',');
                    file.write('"');
                    file.write("y1");
                    file.write('"');
                    file.write(':');
                    file.write('"');
                    file.write(String.valueOf(arrayOfShapes[i].getPosition().y));
                    file.write('"');
                    file.write(',');
                    file.write('"');
                    file.write("color");
                    file.write('"');
                    file.write(':');
                    file.write('"');
                    file.write(arrayOfShapes[i].getColor().toString());
                    file.write('"');
                    file.write(',');
                    file.write('"');
                    file.write("fill");
                    file.write('"');
                    file.write(':');
                    file.write('"');
                    file.write(arrayOfShapes[i].getFillColor().toString());
                    file.write('"');
                    file.write(',');
                    Map<String, Double> properties = new HashMap<String, Double>();
                    properties = arrayOfShapes[i].getProperties();
                    if(properties != null) {
                        Iterator var4 = arrayOfShapes[i].getProperties().entrySet().iterator();
                        while (var4.hasNext()) {
                            Map.Entry s = (Map.Entry) var4.next();
                            String s1 = (String) s.getKey();
                            String s2 = s.getValue().toString();
                            file.write('"');
                            file.write(s1);
                            file.write('"');
                            file.write(':');
                            file.write('"');
                            file.write(s2);
                            file.write('"');
                            if (var4.hasNext()) {
                                file.write(',');
                            }
                        }
                    }
                    file.write('}');
                    if (arrayOfShapes[i + 1] != null) file.write("},");
                    else file.write('}');
                    file.write("\n");
                    i++;
                }
                file.write(']');
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void load(String path) {
        if (path.contains(".xml")||path.contains(".XmL")||path.contains("Xml")) {
            try {
                XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
                index = 0;
                arrayOfShapes = new Shape[size];
                arrayOfShapes = (Shape[]) xmlDecoder.readObject();
                while (arrayOfShapes[index] != null) {
                    index++;
                }
                UndoIndex = -1;
                updateUndo();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (path.contains(".json") || path.contains(".JsOn")) {
            File file2 = new File(path);
            try {
                Shape newShape;
                String s = new String();
                BufferedReader file = new BufferedReader(new FileReader(file2));
                index = 0;
                while ((s = file.readLine()) != null) {
                    if (s.length() <= 2) continue;
                    String[] parts = s.split(":|,");
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].replaceAll("[:,]", "");
                        parts[i] = parts[i].replaceAll("\\}", "");
                        parts[i] = parts[i].replaceAll("\\{", "");
                        ;
                        parts[i] = parts[i].replaceAll("\\\"", "");
                        parts[i] = parts[i].replaceAll("\\]", "");
                        parts[i] = parts[i].replaceAll("\\[", "");
                    }
                    Class cl = Class.forName(parts[0]);
                    newShape = (Shape) cl.newInstance();
                    newShape.setPosition(new Point(Integer.valueOf(parts[2]), Integer.valueOf(parts[4])));
                    Scanner sc = new Scanner(parts[6]);
                    sc.useDelimiter("\\D+");
                    int r, g, b;
                    r = sc.nextInt();
                    sc = new Scanner(parts[7]);
                    sc.useDelimiter("\\D+");
                    g = sc.nextInt();
                    sc = new Scanner(parts[8]);
                    sc.useDelimiter("\\D+");
                    b = sc.nextInt();
                    Color color = new Color(r, g, b);
                    newShape.setColor(color);
                    sc = new Scanner(parts[10]);
                    sc.useDelimiter("\\D+");
                    r = sc.nextInt();
                    sc = new Scanner(parts[11]);
                    sc.useDelimiter("\\D+");
                    g = sc.nextInt();
                    sc = new Scanner(parts[12]);
                    sc.useDelimiter("\\D+");
                    b = sc.nextInt();
                    Color color2 = new Color(r, g, b);
                    newShape.setFillColor(color2);
                    Map<String, Double> properties = new HashMap<String, Double>();
                    for (int i = 13; i < parts.length; i += 2) {
                        if(parts[i].length() <= 2) break;
                        properties.put(parts[i], Double.valueOf(parts[i + 1]));
                    }
                    newShape.setProperties(properties);
                    arrayOfShapes[index++] = newShape;
                    maxIndex = index;
                }
                for (int j = index; j < size; j++) {
                    arrayOfShapes[j] = null;
                }
                UndoIndex = -1;
                UndomaxIndex = -1;
                updateUndo();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException();
        }
    }
}