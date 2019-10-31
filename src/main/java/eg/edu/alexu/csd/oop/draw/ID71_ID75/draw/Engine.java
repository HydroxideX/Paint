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
    private int size = 100000;
    private Shape[] arrayOfShapes = new Shape[size];
    private int index = 0;
    private int maxIndex = 0;
    private int max(int a, int b){
        return Math.max(a, b);
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
    public void addShape(Shape shape) {
        arrayOfShapes[index] = shape;
        index++;
        maxIndex = index;
    }

    private boolean removed = false;
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
        maxIndex = index;
    }

    void RemoveLastShape(){
        index--;
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        for(int i = 0;i<index;i++) {
            if (arrayOfShapes[i] == oldShape) {
                arrayOfShapes[i] = newShape;
            }
        }
    }

    @Override
    public Shape[] getShapes() {
        Shape[] currentShapes = new Shape[size];
        if (index >= 0) System.arraycopy(arrayOfShapes, 0, currentShapes, 0, index);
        return currentShapes;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        return null;
    }

    @Override
    public void undo() {
        if(index>0)
        index--;
    }

    @Override
    public void redo() {
        if(index < maxIndex) index++;
    }

    private double[] pointsToLine(Point p1, Point p2){
        double[] ar = new double[3];
        ar[0]=(double)(p2.y-p1.y)/(p2.x-p1.x);
        ar[1]=1;
        ar[2]=(p1.y-ar[0]*p1.x);
        return ar;
    }

    //from GeeksforGeeks//
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
    /////endofPartThatWasCopied//
    Shape checkOnShapes(int x, int y){
        for(int i = index-1 ;i>=0;i--){
            if(arrayOfShapes[i].getProperties().get("type").intValue()==1){
                Point p1 = new Point(arrayOfShapes[i].getPosition());
                Point p2 = new Point(arrayOfShapes[i].getProperties().get("x2").intValue(),
                        arrayOfShapes[i].getProperties().get("y2").intValue());
                double[] ar = pointsToLine(p1,p2);
                if(Math.abs(-ar[0]*x+ar[1]*y-ar[2]) < 20){
                    return arrayOfShapes[i];
                }
            }else if(arrayOfShapes[i].getProperties().get("type")==2d ||
                    arrayOfShapes[i].getProperties().get("type")==3d ||
                    arrayOfShapes[i].getProperties().get("type")==4d ||
                    arrayOfShapes[i].getProperties().get("type")==5d){
                Point p1 = new Point(arrayOfShapes[i].getPosition());
                Point p2 = new Point(arrayOfShapes[i].getProperties().get("x2").intValue(),
                        arrayOfShapes[i].getProperties().get("y2").intValue());
                if((x<=p1.x && y <= p1.y && y >= p2.y && x >= p2.x)
                        ||(x>=p1.x && y <= p1.y && y >= p2.y && x <= p2.x)
                        ||(x>=p1.x && y >= p1.y && y <= p2.y && x <= p2.x)
                        ||(x<=p1.x && y >= p1.y && y <= p2.y && x >= p2.x)){
                    return arrayOfShapes[i];
                }
            } else if(arrayOfShapes[i].getProperties().get("type") == 6d) {
                if(isInside(arrayOfShapes[i].getPosition().x,arrayOfShapes[i].getPosition().y,
                        arrayOfShapes[i].getProperties().get("x2").intValue(),arrayOfShapes[i].getProperties().get("y2").intValue(),
                        arrayOfShapes[i].getProperties().get("x3").intValue(),arrayOfShapes[i].getProperties().get("y3").intValue(),
                        x,y)){
                    return arrayOfShapes[i];
                }
            } else {
                continue;
            }
        }
        return null;
    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }
}
