package shape;



import com.fasterxml.jackson.annotation.JsonTypeInfo;
import peer.Painter;


import java.awt.*;

public class Circle extends Shape {
    public int px;
    public int py;
    public int width;
    public int height;

    public Circle(){super();}

    public Circle(Color color, int startX, int startY, int endX, int endY) {
        super(color);
        super.startX = startX;
        super.startY = startY;
        super.endX = endX;
        super.endY = endX;
        px = Math.min(startX,endX);
        py = Math.min(startY,endY);
        width = Math.abs(startX-endX);
        height = Math.abs(startY-endY);
    }

    @Override
    public void draw(Painter painter) {
        Graphics g = painter.getP().getGraphics();
        g.setColor(colour);
        g.drawOval(px,py,width,height);
        g.dispose();
    }


}
