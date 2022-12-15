package peer.commandhandler;

import message.Message;
import message.ShapeList;
import peer.PeerListener;

public class ShapeListHandler extends CommandHandler {
    public ShapeListHandler(PeerListener peerListener, Message message) {
        super(peerListener, message);
    }

    @Override
    public void handle() {
        synchronized (getPeerListener().getPeer().getWhiteboardState()){
            // clear whiteboard because manager wants to load new existing state
            getPeerListener().getPeer().getWhiteboardState().clear();
            ShapeList shapeList = (ShapeList)getMessage();
            getPeerListener().getPeer().getWhiteboardState().addAll(shapeList.getShapes());
            getPeerListener().getPainter().repaint();
        }
    }
}
