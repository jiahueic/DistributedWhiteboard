package server.reqhandle;

import message.Message;
import message.ShapeList;
import message.ShapeToDraw;
import server.Server;
import server.ServerNotifier;
import shape.Shape;

import java.io.BufferedWriter;
import java.net.Socket;
import java.util.List;

public class ShapeListHandler extends RequestHandler {
    public ShapeListHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    @Override
    public void handle() {
        // send the shape list to all peers
        for (ServerNotifier sn : getServer().getNotifierThreads().values()){
            sn.getMessagesToSend().add(getMessage());
        }
        ShapeList shapeList = (ShapeList)getMessage();
        List<Shape> shapes = shapeList.getShapes();
        // shape list is received if manager wants to load whiteboard
        // so we clear the current whiteboard
        getServer().getWhiteboardState().clear();
        for (Shape shape: shapes){
            ShapeToDraw shapeToDraw = new ShapeToDraw(shape);
            getServer().getWhiteboardState().add(shapeToDraw);
        }
        System.out.println(getServer().getWhiteboardState());
    }
}
