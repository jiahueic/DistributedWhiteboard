package shape;

import shape.Circle;
import peer.Painter;

import java.awt.*;

public class Rectangle extends Circle {
    public Rectangle(){
        super();
    }

    public Rectangle(Color color, int startX, int startY, int endX, int endY) {
        super(color,startX, startY, endX, endY);
    }
    public void draw(Painter painter) {
        //System.out.println("Colour in pointer:"+ colour);

        Graphics g = painter.getP().getGraphics();
        g.setColor(colour);
        g.drawRect(px,py,width,height);
        g.dispose();
    }

}
