package peer.whiteboardaction;

import message.CreateWhiteboardRequest;
import message.JoinWhiteboardRequest;
import message.ListenWhiteboardRequest;
import peer.GenericPeer;

public class WhiteboardActionFactory {
    public enum Action {
        CREATE,
        JOIN,
        LISTEN
    }

    private GenericPeer genericPeer;

    public WhiteboardActionFactory(GenericPeer genericPeer){
        this.genericPeer = genericPeer;
    }

    public WhiteboardAction createWhiteboardAction(Action action){
        if (action == Action.JOIN){
            return new JoinWhiteboardAction(genericPeer);
        }

        else if (action == Action.CREATE){
            return new CreateWhiteboardAction(genericPeer);
        }

        else {
            return new ListenWhiteboardAction(genericPeer);
        }
    }
}
