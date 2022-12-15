package message;

import shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class ShapeList extends Message {
    private List<Shape> shapes;

    public ShapeList(){
        super();
    }

    public ShapeList(List<Shape> shapes){
        super();
        this.shapes = shapes;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }
}
