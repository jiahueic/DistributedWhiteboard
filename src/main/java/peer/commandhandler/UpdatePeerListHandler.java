package peer.commandhandler;

import message.Message;
import message.UpdatePeerList;
import peer.PeerListener;

import java.util.ArrayList;

public class UpdatePeerListHandler extends CommandHandler {
    public UpdatePeerListHandler(PeerListener peerListener, Message message) {
        super(peerListener, message);
    }

    @Override
    public void handle() {
        UpdatePeerList updatePeerList = (UpdatePeerList) getMessage();
        ArrayList<String> PeerList = updatePeerList.getPeers();
        getPeerListener().getPeer().getPainter().displayPeerList(PeerList);
        System.out.println(PeerList);
    }
}
