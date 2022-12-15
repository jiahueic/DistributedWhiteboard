package peer.whiteboardaction;

import message.CreateWhiteboardRequest;
import peer.*;

import java.io.IOException;

public class CreateWhiteboardAction extends WhiteboardAction {
    public CreateWhiteboardAction(GenericPeer genericPeer) {
        super(genericPeer);
    }

    // create correct message to be sent
    public void initMessage(){
        setMessage(new CreateWhiteboardRequest(getGenericPeer().getUsername()));
    }

    @Override
    public void processResponse() throws IOException, ClassNotFoundException {
        // start peer notifier
        ((Peer)getGenericPeer()).startPeerNotifier();

        // setup client whiteboard
        Peer peer = (Peer)getGenericPeer();
        peer.setPainter(new Painter(peer));

        // start peer lsitener
        ((Peer)getGenericPeer()).startPeerListener();
    }
}
