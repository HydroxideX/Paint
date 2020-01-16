package eg.edu.alexu.csd.oop.filemanagement;

import eg.edu.alexu.csd.oop.shapes.Shape;

public interface File {
    public void save(Shape[] array, String path);
    public void load(String path);
}
