package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import java.awt.*;
import java.awt.Paint;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Engine implements DrawingEngine{
    int size = 100000;
    Shape[] arrayOfShapes = new Shape[size];
    int index = 0;

    @Override
    public void refresh(Graphics canvas) {
        canvas.setColor(Color.WHITE);
        canvas.fillRect(0,0,10000,10000);
        for(int i = 0;i<index;i++){
            arrayOfShapes[i].draw(canvas);
        }
    }

    @Override
    public void addShape(Shape shape) {
        arrayOfShapes[index] = shape;
        index++;
    }

    boolean removed = false;
    @Override
    public void removeShape(Shape shape) {
        removed = false;
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
    }

    public void RemoveLastShape(Shape shape){
        index--;
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        for(int i = 0;i<size;i++) {
            if (arrayOfShapes[i] == oldShape) {
                arrayOfShapes[i] = newShape;
            }
        }
    }

    @Override
    public Shape[] getShapes() {
        Shape[] currentShapes = new Shape[size];
        for(int i = 0;i<index;i++){
            currentShapes[i] = arrayOfShapes[i];
        }
        return currentShapes;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        return null;
    }

    @Override
    public void undo() {
        index--;
    }

    @Override
    public void redo() {
        if(arrayOfShapes[index] != null){
            index++;
        }
    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }
}
