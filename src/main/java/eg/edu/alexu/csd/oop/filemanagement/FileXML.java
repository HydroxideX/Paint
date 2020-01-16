package eg.edu.alexu.csd.oop.filemanagement;

import eg.edu.alexu.csd.oop.draw.Engine;
import eg.edu.alexu.csd.oop.shapes.Shape;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class FileXML implements File {
    private Engine engine;
    public FileXML(Engine engine){
        this.engine = engine;
    }

    @Override
    public void save(Shape[] array, String path) {
        try {
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
            xmlEncoder.writeObject(array);
            xmlEncoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(String path) {
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
            engine.setIndex(0);
            engine.setArrayOfShapes(new Shape[engine.getSize()]);
            engine.setArrayOfShapes((Shape[]) xmlDecoder.readObject());
            while (engine.getArrayOfShapes()[engine.getIndex()] != null) {
                engine.setIndex(engine.getIndex()+1);
            }
            engine.setUndoIndex(-1);
            engine.updateUndo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
