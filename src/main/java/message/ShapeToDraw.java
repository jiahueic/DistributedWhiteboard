package message;

import shape.Shape;

import java.util.ArrayList;

public class ShapeToDraw extends Message {
    private Shape shape;
    public ShapeToDraw(){
        super();
    }

    public ShapeToDraw(Shape shape){
        super();
        this.shape = shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "ShapeToDraw{" +
                "shape=" + shape +
                '}';
    }
}
