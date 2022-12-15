package shape;

import shape.Circle;
import peer.Painter;

import java.awt.*;

public class Line extends Shape {
    public Line(){super();}

    public Line(Color col, int startX, int startY, int endX, int endY) {
//        super(col,startX,startY,endX,endY);
        super(col);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

    }
    public void draw(Painter painter) {
        Graphics g = painter.getP().getGraphics();
        g.setColor(colour);

        g.drawLine(startX, startY, endX, endY);
    }

    }
