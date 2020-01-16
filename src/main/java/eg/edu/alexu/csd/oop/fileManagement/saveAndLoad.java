package eg.edu.alexu.csd.oop.fileManagement;

import eg.edu.alexu.csd.oop.Shapes.Shape;

public interface saveAndLoad {
    public void save(Shape[] array, String path);
    public void load(String path);
}
