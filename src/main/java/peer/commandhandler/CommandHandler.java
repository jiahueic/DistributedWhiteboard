package peer.commandhandler;

import message.Message;
import peer.PeerListener;

public abstract class CommandHandler {
    private PeerListener peerListener;
    private Message message;

    public Message getMessage() {
        return message;
    }

    public CommandHandler(PeerListener peerListener, Message message){
        this.peerListener = peerListener;
        this.message = message;
    }

    public PeerListener getPeerListener() {
        return peerListener;
    }

    public abstract void handle();
}
