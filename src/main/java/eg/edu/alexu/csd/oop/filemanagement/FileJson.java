package eg.edu.alexu.csd.oop.filemanagement;

import eg.edu.alexu.csd.oop.draw.Engine;
import eg.edu.alexu.csd.oop.shapes.Shape;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class FileJson implements File {
    private Engine engine;
    public FileJson(Engine engine){
        this.engine = engine;
    }

    @Override
    public void save(Shape[] arrayOfShapes, String path) {
        java.io.File file2 = new java.io.File(path);
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
    }

    @Override
    public void load(String path) {
        java.io.File file2 = new java.io.File(path);
        try {
            Shape newShape;
            String s;
            BufferedReader file = new BufferedReader(new FileReader(file2));
            engine.setIndex(0);
            while ((s = file.readLine()) != null) {
                if (s.length() <= 2) continue;
                String[] parts = s.split(":|,");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replaceAll("[:,]", "");
                    parts[i] = parts[i].replaceAll("\\}", "");
                    parts[i] = parts[i].replaceAll("\\{", "");
                    parts[i] = parts[i].replaceAll("\\\"", "");
                    parts[i] = parts[i].replaceAll("\\]", "");
                    parts[i] = parts[i].replaceAll("\\[", "");
                }
                Class cl = Class.forName(parts[0]);
                newShape = (Shape) cl.newInstance();
                newShape.setPosition(new Point(Integer.parseInt(parts[2]), Integer.parseInt(parts[4])));
                Scanner sc = new Scanner(parts[6]);
                sc.useDelimiter("\\D+");
                int r;
                int g;
                int b;
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
                    if(i+1 == parts.length) break;
                    properties.put(parts[i], Double.valueOf(parts[i + 1]));
                }
                newShape.setProperties(properties);
                engine.getArrayOfShapes()[engine.getIndex()] = newShape;
                engine.setIndex(engine.getIndex()+1);
                engine.setMaxIndex(engine.getIndex());
            }
            for (int j = engine.getIndex(); j < engine.getSize(); j++) {
                engine.getArrayOfShapes()[j] = null;
            }
            engine.setUndoIndex(-1);
            engine.setUndoMaxIndex(-1);
            engine.updateUndo();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
