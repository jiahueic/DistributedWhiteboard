package peer.commandhandler;

import message.AcceptUserRequest;
import message.AcceptUserResponse;
import message.Message;
import peer.dialogue.JoinDialogue;
import peer.PeerListener;

public class AcceptUserRequestHandler extends CommandHandler {
    public AcceptUserRequestHandler(PeerListener peerListener, Message message) {
        super(peerListener, message);
    }

    @Override
    public void handle() {
        AcceptUserRequest acceptUserRequest = (AcceptUserRequest)getMessage();
        JoinDialogue joinDialogue = new JoinDialogue();
        String username = acceptUserRequest.getUserToAccept();
        int response = joinDialogue.showDialogue(username);
        Message message;
        if (response == 0){
            message = new AcceptUserResponse(username, true);
        }
        else{
            message = new AcceptUserResponse(username, false);
        }
        getPeerListener().getPeer().getPeerNotifier().getMessagesToSend().add(message);
    }
}
