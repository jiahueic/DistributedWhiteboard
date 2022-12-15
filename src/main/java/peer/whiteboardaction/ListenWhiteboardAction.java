package peer.whiteboardaction;

import message.ListenWhiteboardRequest;
import peer.GenericPeer;

import java.io.IOException;

public class ListenWhiteboardAction extends WhiteboardAction {

    public ListenWhiteboardAction(GenericPeer genericPeer) {
        super(genericPeer);
    }

    @Override
    public void initMessage() {
        setMessage(new ListenWhiteboardRequest(getGenericPeer().getUsername()));
    }

    @Override
    public void processResponse() throws IOException, ClassNotFoundException {
        // do nothing
    }

    @Override
    public void doAction() throws IOException, ClassNotFoundException {
        getGenericPeer().connect();
        super.doAction();
    }
}
