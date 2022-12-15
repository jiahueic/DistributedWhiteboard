package shape;

import shape.Shape;
import peer.Painter;

import java.awt.*;

public class Point extends Shape {
    public Point(){super();}

    public Point(Color c, int x, int y) {
        super(c);
        //System.out.println(c);
        this.x = x;
        this.y = y;
    }

    public void draw(Painter painter) {
        Graphics g = painter.getP().getGraphics();
        g.setColor(colour);
        g.fillOval(x, y, 6, 6);
        g.dispose();
    }

    }
