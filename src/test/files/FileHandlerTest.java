package files;

import org.junit.jupiter.api.Test;
import peer.Painter;
import peer.Peer;
import shape.Circle;
import shape.Rectangle;
import shape.Shape;
import shape.Triangle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    void writeToFile() throws IOException {
        FileHandler fileHandler = new FileHandler("myfile.txt");
        fileHandler.writeToFile("asdf");
    }

    @Test
    void writeWhiteBoardStateToFile() throws IOException {
        FileHandler fileHandler = new FileHandler("mywhiteboard.txt");
        ArrayList<Shape> whiteBoardState = new ArrayList<>();
        whiteBoardState.add(new Circle(Color.WHITE, 10, 20, 30, 40));
        whiteBoardState.add(new Rectangle(Color.WHITE, 10, 220, 30, 40));
        whiteBoardState.add(new Triangle(Color.WHITE, 102, 20, 30, 40, 12, 12));
        fileHandler.writeWhiteBoardStateToFile(whiteBoardState);
    }

    @Test
    void readWhiteBoardStateFromFile() throws IOException, InterruptedException, ClassNotFoundException {
        FileHandler fileHandler = new FileHandler("mywhiteboard.txt");
        List<Shape> whiteBoardState = fileHandler.fileToWhiteBoardState();
        System.out.println(whiteBoardState);
        Painter  painter = new Painter(new Peer(3232, InetAddress.getByName("localhost"), "bob"));
        for (Shape shape : whiteBoardState){
            shape.draw(painter);
        }
        Thread.sleep(9999);
    }
}