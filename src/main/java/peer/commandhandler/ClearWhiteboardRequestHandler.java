package peer.commandhandler;

import message.Message;
import peer.PeerListener;

public class ClearWhiteboardRequestHandler extends CommandHandler {
    public ClearWhiteboardRequestHandler(PeerListener peerListener, Message message) {
        super(peerListener, message);
    }

    @Override
    public void handle() {
        // clear the whiteboard and repaint
        getPeerListener().getPeer().getWhiteboardState().clear();
        getPeerListener().getPeer().getPainter().repaint();
    }
}
