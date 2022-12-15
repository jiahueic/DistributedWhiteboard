package peer.commandhandler;

import message.Message;
import message.ShapeToDraw;
import peer.PeerListener;
import shape.Shape;

public class ShapeToDrawHandler extends CommandHandler {

    public ShapeToDrawHandler(PeerListener peerListener, Message message) {
        super(peerListener, message);
    }

    @Override
    public void handle() {
        ShapeToDraw shapeToDraw = (ShapeToDraw)getMessage();
        System.out.println("Drawing " + shapeToDraw.getShape());
        Shape shape = shapeToDraw.getShape();
        getPeerListener().getPeer().getWhiteboardState().add(shape);
        getPeerListener().getPeer().getPainter().repaint();
        //shape.draw(getPeerListener().getPeer().getPainter());
    }
}
