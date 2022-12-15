package peer.commandhandler;

import message.Message;
import peer.PeerListener;

public class KickUserRequestHandler extends CommandHandler {
    public KickUserRequestHandler(PeerListener peerListener, Message message) {
        super(peerListener, message);
    }

    @Override
    public void handle() {
        System.out.println("you have been kicked from the whiteboard");
        System.exit(0);
    }
}
