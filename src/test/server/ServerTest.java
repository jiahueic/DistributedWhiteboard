package server;

import org.junit.jupiter.api.Test;
import peer.Peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    class PeerThread extends Thread{
        public void run(){
            Peer peer = null;
            try {
                peer = new Peer(3232, InetAddress.getByName("localhost"), "bob");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            peer.connect();
        }
    }
    @Test
    void peerConnectToServer() throws IOException, InterruptedException {
        Server server = new Server(3232);
        server.start();
        Peer peer = new Peer(3232, InetAddress.getByName("localhost"), "bob");
        peer.connect();
    }
}