package peer.whiteboardaction;

import message.JoinWhiteboardRequest;
import peer.GenericPeer;

public class JoinWhiteboardAction extends CreateWhiteboardAction {

    public JoinWhiteboardAction(GenericPeer genericPeer) {
        super(genericPeer);
        System.out.println("Waiting for manager to accept join request");
    }

    @Override
    public void initMessage() {
        setMessage(new JoinWhiteboardRequest(getGenericPeer().getUsername()));
    }
}
