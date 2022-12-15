package shape;

import peer.Painter;

import java.awt.*;
import java.util.ArrayList;

public class PointCollection extends Shape{
    private ArrayList<Point> points = new ArrayList<Point>();
    public PointCollection() {
        super();
    }
    public void addPoint(Point point) {
        points.add(point);
    }
    public ArrayList<Point>getPoints() {
        return this.points;
    }

    @Override
    public void draw(Graphics graphics) {
        for(int i = 0; i < points.size(); i++) {
            points.get(i).draw(graphics);
        }
    }

    @Override
    public void draw(Painter painter) {
        for(int i = 0; i < points.size(); i++) {
            points.get(i).draw(painter);
        }
    }
}
