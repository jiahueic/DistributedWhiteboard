package peer.commandhandler;

import message.ChatToDisplay;
import message.Message;
import peer.PeerListener;

public class ChatToDisplayHandler extends CommandHandler {
    public ChatToDisplayHandler(PeerListener peerListener, Message message) {
        super(peerListener, message);
    }

    @Override
    public void handle() {
        ChatToDisplay chatToDisplay = (ChatToDisplay) getMessage();
        getPeerListener().getPeer().getPainter().addChatMessage(chatToDisplay);
        System.out.println("Displaying message in chat: " + chatToDisplay.getChatMessage());
    }
}
