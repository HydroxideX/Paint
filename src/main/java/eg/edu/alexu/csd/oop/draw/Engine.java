package eg.edu.alexu.csd.oop.draw;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;


public class Engine implements DrawingEngine{
    private int size = 1000;
    private eg.edu.alexu.csd.oop.draw.Shape[] arrayOfShapes = new eg.edu.alexu.csd.oop.draw.Shape[size];
    private eg.edu.alexu.csd.oop.draw.Shape[][] UndoArray = new eg.edu.alexu.csd.oop.draw.Shape[1000][size];

     int index = 0;
    private int UndoIndex = 0;
    private int maxIndex = 0;
    private int UndomaxIndex = 0;
    private int max(int a, int b){
        return Math.max(a, b);
    }
    private List<Class<? extends eg.edu.alexu.csd.oop.draw.Shape>> SupportedShapes=null;
    private ArrayList<String> ClassNames=new ArrayList<>();

    ArrayList<String> getClassNames() throws ClassNotFoundException {
        SupportedShapes=getSupportedShapes();
        return ClassNames;
    }
    void UpdateUndo(){
        UndoIndex--;
        boolean checker=false;
        if(UndoIndex>=0)
        {
            for (int i=0;i<index;i++)
            {
                if(arrayOfShapes[i]!=UndoArray[UndoIndex][i])
                {
                    checker=true;
                    break;
                }
            }
        }
        else checker=true;
        UndoIndex++;
        if(checker)
        {
            System.arraycopy(arrayOfShapes, 0, UndoArray[UndoIndex], 0, index);
            UndoIndex++;
            UndomaxIndex=max(UndoIndex,UndomaxIndex);
        }
    }
    @Override
    public void refresh(Graphics canvas) {
        canvas.setColor(Color.WHITE);
        canvas.fillRect(0,0,10000,10000);
        for(int i = 0;i<index;i++){
            arrayOfShapes[i].draw(canvas);
        }
        maxIndex = max(index,maxIndex);
    }

    @Override
    public void addShape(eg.edu.alexu.csd.oop.draw.Shape shape) {
        arrayOfShapes[index] = shape;
        index++;
        maxIndex = index;
    }

    @Override
    public void removeShape(eg.edu.alexu.csd.oop.draw.Shape shape) {
        boolean removed = false;
        for (int i = 0;i<index;i++){
            if(arrayOfShapes[i] == shape){
                removed = true;
                for(int j = i+1;j<size;j++){
                    if(arrayOfShapes[j-1]== null) break;
                    arrayOfShapes[j-1] = arrayOfShapes[j];
                }
                arrayOfShapes[size-1] = null;
                break;
            }
        }
        if(removed) index--;
        maxIndex = index;
    }

    void RemoveLastShape(){
        index--;
    }

    @Override
    public void updateShape(eg.edu.alexu.csd.oop.draw.Shape oldShape, eg.edu.alexu.csd.oop.draw.Shape newShape) {
        for(int i = 0;i<index;i++) {
            if (arrayOfShapes[i] == oldShape) {
                arrayOfShapes[i] = newShape;
            }
        }
    }

    @Override
    public eg.edu.alexu.csd.oop.draw.Shape[] getShapes() {
        return arrayOfShapes;
    }

    @Override
    public List<Class<? extends eg.edu.alexu.csd.oop.draw.Shape>> getSupportedShapes() throws ClassNotFoundException {
        SupportedShapes=new ArrayList<>();
        File dir = new File("target/classes/eg/edu/alexu/csd/oop/draw");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String current = child.getName();
                current = current.substring(0, current.length() - 6);
                String pack = "eg.edu.alexu.csd.oop.draw";
                ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                Class cl = classLoader.loadClass(pack + "." + current);
                if(eg.edu.alexu.csd.oop.draw.Shape.class.isAssignableFrom(cl)&& !current.equals("Shape")){
                    SupportedShapes.add(cl);
                    ClassNames.add(current);
                }
            }
        }
        return SupportedShapes;
    }

    @Override
    public void undo() {
        if(UndoIndex>1)
        {
            UndoIndex--;
            UndoIndex--;
            System.arraycopy(UndoArray[UndoIndex], 0,arrayOfShapes , 0, UndoArray[UndoIndex].length);
            UndomaxIndex=max(UndoIndex,UndomaxIndex);
            int i=0;
            while (arrayOfShapes[i]!=null)
            {
                i++;
            }
            index=i;
            UndoIndex++;
        }
        else {
            arrayOfShapes=new eg.edu.alexu.csd.oop.draw.Shape[size];
            index=0;
            UndoIndex=0;
        }
    }

    @Override
    public void redo() {
        if(UndoIndex <UndomaxIndex) {
            int i=0;
            while (UndoArray[UndoIndex][i]!=null)
            {
                i++;
            }
            index=i;
            System.arraycopy(UndoArray[UndoIndex], 0,arrayOfShapes , 0, index);
            UndoIndex++;
            UndomaxIndex=max(UndoIndex,UndomaxIndex);
             i=0;
            while (arrayOfShapes[i]!=null)
            {
                i++;
            }
            index=i;
        }
    }

    private double[] pointsToLine(Point p1, Point p2){
        double[] ar = new double[3];
        ar[0]=(double)(p2.y-p1.y)/(p2.x-p1.x);
        ar[1]=1;
        ar[2]=(p1.y-ar[0]*p1.x);
        return ar;
    }


    private static double area(int x1, int y1, int x2, int y2, int x3, int y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+x3*(y1-y2))/2.0);
    }
    private static boolean isInside(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y)
    {
        double A = area (x1, y1, x2, y2, x3, y3);
        double A1 = area (x, y, x2, y2, x3, y3);
        double A2 = area (x1, y1, x, y, x3, y3);
        double A3 = area (x1, y1, x2, y2, x, y);
        return (A == A1 + A2 + A3);
    }

    eg.edu.alexu.csd.oop.draw.Shape checkOnShapes(int x, int y){
        for(int i = index-1 ;i>=0;i--){
            Map<String, Double> secondPoint = new HashMap<>(arrayOfShapes[i].getProperties());
            secondPoint.putIfAbsent("type",9d);
            arrayOfShapes[i].setProperties(secondPoint);
            if(arrayOfShapes[i].getProperties().get("type") == 9d){
                Point p1 = arrayOfShapes[i].getPosition();
                double dist = Math.sqrt(Math.pow(p1.x-x,2)+Math.pow(p1.y-y,2));
                if(dist <= 20) return arrayOfShapes[i];
            } else if(arrayOfShapes[i].getProperties().get("type").intValue()==1){
                Point p1 = new Point(arrayOfShapes[i].getPosition());
                Point p2 = new Point(arrayOfShapes[i].getProperties().get("x2").intValue(),
                        arrayOfShapes[i].getProperties().get("y2").intValue());
                double[] ar = pointsToLine(p1,p2);
                if(Math.abs(-ar[0]*x+ar[1]*y-ar[2]) < 20){
                    return arrayOfShapes[i];
                }
            }else if(arrayOfShapes[i].getProperties().get("type") == 6d) {
                if(isInside(arrayOfShapes[i].getPosition().x,arrayOfShapes[i].getPosition().y,
                        arrayOfShapes[i].getProperties().get("x2").intValue(),arrayOfShapes[i].getProperties().get("y2").intValue(),
                        arrayOfShapes[i].getProperties().get("x3").intValue(),arrayOfShapes[i].getProperties().get("y3").intValue(),
                        x,y)){
                    return arrayOfShapes[i];
                }
            }
            else {
                Point p1 = new Point(arrayOfShapes[i].getPosition());
                Point p2 = new Point(arrayOfShapes[i].getProperties().get("x2").intValue(),
                        arrayOfShapes[i].getProperties().get("y2").intValue());
                if((x<=p1.x && y <= p1.y && y >= p2.y && x >= p2.x)
                        ||(x>=p1.x && y <= p1.y && y >= p2.y && x <= p2.x)
                        ||(x>=p1.x && y >= p1.y && y <= p2.y && x <= p2.x)
                        ||(x<=p1.x && y >= p1.y && y <= p2.y && x >= p2.x)){
                    return arrayOfShapes[i];
                }
            }
        }
        return null;
    }

    @Override
    public void save(String path) {
        if(path.contains(".xml")){


        } else if (path.contains(".json")) {
            File file = new File(path);
            try {
                FileWriter file2 = new FileWriter(file);
                JSONArray Shapes = new JSONArray();
                JSONObject a = new JSONObject();
                JSONObject b = new JSONObject();
                a.put("index",String.valueOf(index));
                b.put("maxIndex",String.valueOf(maxIndex));
                Shapes.add(a);
                Shapes.add(b);
                for(int i = 0;i<index;i++){

                    JSONObject m = new JSONObject();
                    m.put("x2", arrayOfShapes[i].getProperties().get("x2").toString());
                    m.put("y2", arrayOfShapes[i].getProperties().get("y2").toString());
                    m.put("released", arrayOfShapes[i].getProperties().get("released").toString());
                    m.put("type", arrayOfShapes[i].getProperties().get("type").toString());
                    m.put("color", arrayOfShapes[i].getColor().toString());
                    m.put("name", arrayOfShapes[i].getClass().getName());
                    m.put("fillColor", arrayOfShapes[i].getFillColor().toString());
                    m.put("positionx", String.valueOf(arrayOfShapes[i].getPosition().x));
                    m.put("positiony", String.valueOf(arrayOfShapes[i].getPosition().y));
                    if(arrayOfShapes[i].getClass().getName()=="eg.edu.alexu.csd.oop.draw.Triangle"){
                        m.put("x3", arrayOfShapes[i].getProperties().get("x3").toString());
                        m.put("y3", arrayOfShapes[i].getProperties().get("y3").toString());
                    }
                    JSONObject r = new JSONObject();
                    r.put("shape",m);
                    Shapes.add(r);
                }
                file2.write(Shapes.toString());
                file2.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public  void load(String path)
    {
        if(path.contains(".json")) {
            JSONParser jsonParser = new JSONParser();
            File file = new File(path);
            try (FileReader reader = new FileReader(file)) {
                Object obj = jsonParser.parse(reader);
                JSONArray shapeList = (JSONArray) obj;
                eg.edu.alexu.csd.oop.draw.Shape x = null;
                JSONObject index1 = (JSONObject) shapeList.get(0);
                JSONObject maxIndex1 = (JSONObject) shapeList.get(1);
                index = Integer.parseInt(index1.get("index").toString());
                maxIndex = Integer.parseInt(maxIndex1.get("maxIndex").toString());
                for (int i = 2; i < shapeList.size(); i++) {
                    Object temp = shapeList.get(i);
                    x = parseShape((JSONObject)temp);
                    arrayOfShapes[i-2] = x;
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private eg.edu.alexu.csd.oop.draw.Shape parseShape(JSONObject shape) {
        Double j;
        Map<String,Double> m = new HashMap<String, Double>();
        JSONObject shapeObject = (JSONObject) shape.get("shape");
        String name = shapeObject.get("name").toString();
        Class cl= null;
        try {
            cl = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        eg.edu.alexu.csd.oop.draw.Shape l = null;
        try {
            l = (Shape)cl.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(cl.getName()=="eg.edu.alexu.csd.oop.draw.Triangle"){
            m.put("x3", Double.valueOf(shapeObject.get("x3").toString()));
            m.put("y3", Double.valueOf(shapeObject.get("y3").toString()));
        }
        m.put("x2", Double.valueOf(shapeObject.get("x2").toString()));
        m.put("y2", Double.valueOf(shapeObject.get("y2").toString()));
        m.put("released", Double.valueOf(shapeObject.get("released").toString()));
        m.put("type", Double.valueOf(shapeObject.get("type").toString()));
        l.setProperties(m);
        l.setPosition(new Point(Double.valueOf(shapeObject.get("positionx").toString()).intValue(),Double.valueOf(shapeObject.get("positiony").toString()).intValue()));
        String s = shapeObject.get("color").toString();
        Scanner sc = new Scanner(s);
        sc.useDelimiter("\\D+");
        Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
        l.setColor(color);
        s = shapeObject.get("fillColor").toString();
        sc = new Scanner(s);
        sc.useDelimiter("\\D+");
        Color color2 = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
        l.setFillColor(color2);
        return l;
    }
}
