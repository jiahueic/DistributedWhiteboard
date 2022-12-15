package shape;

import peer.Painter;
import shape.Shape;

import java.awt.*;

public class Text extends Shape {
    public String content;

    public Text(){super();}

    public Text(Color col, String content, int startX, int startY) {
        super(col);
        this.startX = startX;
        this.startY = startY;
        this.content = content;
    }
    public void draw(Painter painter) {
        //System.out.println("Colour in pointer:" + colour);
        Graphics g = painter.getP().getGraphics();
        g.setColor(colour);
        g.drawString(content, startX, startY);
        g.dispose();
    }

    }
