package message;

import java.util.ArrayList;

public class UpdatePeerList extends Message {
    private ArrayList<String> peers;
    public UpdatePeerList(){
        super();
    }

    public UpdatePeerList(ArrayList<String> peers){
        super();
        this.peers = peers;
    }

    public void setPeers(ArrayList<String> peers) {
        this.peers = peers;
    }

    public ArrayList<String> getPeers() {
        return peers;
    }
}
