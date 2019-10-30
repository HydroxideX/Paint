package eg.edu.alexu.csd.oop.draw.ID71_ID75.draw;

import java.awt.*;
import java.util.List;

public class Engine implements DrawingEngine{
    int size = 100000;
    Shape[] arrayOfShapes = new Shape[size];
    int index = 0;

    @Override
    public void refresh(Graphics canvas) {
        canvas.dispose();
        for(int i = 0;i<index;i++){
            arrayOfShapes[i].draw(canvas);
        }
    }

    @Override
    public void addShape(Shape shape) {
        arrayOfShapes[index] = shape;
        index++;
    }

    @Override
    public void removeShape(Shape shape) {
        for(int i = 0;i<size;i++){
            if(arrayOfShapes[i] == shape){
                for(int j = i+1;j<size;j++){
                    if(arrayOfShapes[j-1]== null) break;
                    arrayOfShapes[j-1] = arrayOfShapes[j];
                }
                arrayOfShapes[size-1] = null;
                break;
            }
        }
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
