package eg.edu.alexu.csd.oop.test.draw;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.test.DummyShape;
import eg.edu.alexu.csd.oop.test.TestRunner;
import org.junit.Test;

import static org.junit.Assert.*;

public class SmokeTest {
    
    public static Class<?> getSpecifications(){
        return DrawingEngine.class;
    }
    
    @Test
    public void testAddShape() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstanceForInterface(DrawingEngine.class);
        instance.addShape(new DummyShape());
    }
    
    @Test
    public void testRemoveShape() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstanceForInterface(DrawingEngine.class);
        try {
            instance.removeShape(new DummyShape());
            fail("Engine accepts removing non existing shape");
        } catch (Throwable e) {
        }
    }
    
    @Test
    public void testUpdateShape() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstanceForInterface(DrawingEngine.class);
        try {
            instance.updateShape(new DummyShape(), new DummyShape());
            fail("Engine accepts updating non existing shape");
        } catch (Throwable e) {
        }
    }
    
    @Test
    public void testUndoRedo() {
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstanceForInterface(DrawingEngine.class);
        try {
            instance.undo();
            fail("Engine undo without having actions");
        } catch (Throwable e) {
        }
        try {
            instance.redo();
            fail("Engine undo without having actions");
        } catch (Throwable e) {
        }
    }
    
    @Test
    public void testGetShapes(){
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstanceForInterface(DrawingEngine.class);
        instance.addShape(new DummyShape());
        instance.addShape(new DummyShape());
        instance.addShape(new DummyShape());
        assertEquals("Wrong number of returned shapes", 3, instance.getShapes().length);
    }
    
    @Test
    public void testGetShapesAfterDelete(){
        DrawingEngine instance = (DrawingEngine)TestRunner.getImplementationInstanceForInterface(DrawingEngine.class);
        DummyShape shape = new DummyShape();
        instance.addShape(shape);
        instance.addShape(new DummyShape());
        instance.addShape(new DummyShape());
        instance.removeShape(shape);
        assertEquals("Wrong number of returned shapes after delete", 2, instance.getShapes().length);
    }

}