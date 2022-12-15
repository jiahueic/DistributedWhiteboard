package shape;

import shape.Shape;
import peer.Painter;

import java.awt.*;

public class Triangle extends Shape {
    public Triangle(){super();}
    public Triangle(Color col, int startX, int startY, int midX, int midY, int endDragX, int endDragY) {
        super(col);
        this.startX = startX;
        this.startY = startY;
        this.midX = midX;
        this.midY = midY;
        this.endDragX = endDragX;
        this.endDragY = endDragY;

    }
    public void draw(Painter painter) {
        int[] xs = {startX, endDragX, midX};
        int[] ys = {midY, midY, startY};
        Graphics g = painter.getP().getGraphics();
        g.setColor(colour);
        g.drawPolygon(xs, ys, 3);
        g.dispose();
    }

    }
