package shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import peer.Painter;

import java.awt.*;
import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shape {
    public String type;
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    public int endDragX; // for triangle only
    public int endDragY; // for triangle only
    public int midX; // for triangle only
    public int midY; // for triangle only
    public int x; // for dots only
    public int y; // for dots only
    public Color colour;

    public Shape(){this.type = this.getClass().getCanonicalName();}

    public Shape(Color col) {
        super();
        this.colour = col;
        this.type = this.getClass().getCanonicalName();
    }

    public void draw(Painter painter){
        System.out.println("No Shape was drawn");
    }

    public void draw(Graphics graphics){
        System.out.println("No Shape was drawn");
    }

    @Override
    public String toString() {
        return "Shape{" +
                "type='" + type + '\'' +
                ", col=" + colour +
                ", startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                ", endDragX=" + endDragX +
                ", endDragY=" + endDragY +
                ", midX=" + midX +
                ", midY=" + midY +
                ", x=" + x +
                ", y=" + y +
                ", colour=" + colour +
                '}';
    }
}