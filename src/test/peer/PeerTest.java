package peer;

import message.ChatToDisplay;
import message.Message;
import message.ShapeToDraw;
import org.junit.jupiter.api.Test;
import server.Server;
import shape.Circle;
import shape.Shape;
import shape.*;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class PeerTest {
    @Test
    void createWhiteboard() throws IOException, InterruptedException, ClassNotFoundException {
        Server server = new Server(3232);
        server.start();
        Peer peer = new Peer(3232, InetAddress.getByName("localhost"), "bob");
        peer.connect();
        peer.createWhiteboard();
        server.join();
    }

    @Test
    void sendshape() throws IOException, InterruptedException, ClassNotFoundException {
        Server server = new Server(3232);
        server.start();
        Peer peer = new Peer(3232, InetAddress.getByName("localhost"), "bob");
        peer.connect();
        peer.createWhiteboard();
        Shape shape = new Circle(Color.RED, 200, 200, 300, 300);
        Message message = new ShapeToDraw(shape);
        peer.getPeerNotifier().getMessagesToSend().add(message);
        shape = new shape.Rectangle(Color.RED, 200, 200, 300, 300);
        message = new ShapeToDraw(shape);
        peer.getPeerNotifier().getMessagesToSend().add(message);
        server.join();
    }

    @Test
    void sendChat() throws IOException, InterruptedException, ClassNotFoundException {
        Server server = new Server(3232);
        server.start();
        Peer peer = new Peer(3232, InetAddress.getByName("localhost"), "bob");
        peer.connect();
        peer.createWhiteboard();
        Shape shape = new Circle(Color.RED, 200, 200, 300, 300);
        Message message = new ShapeToDraw(shape);
        peer.getPeerNotifier().getMessagesToSend().add(message);
        message = new ChatToDisplay(peer.getUsername(),"I like beans");
        peer.getPeerNotifier().getMessagesToSend().add(message);
        server.join();
    }

    @Test
    void multiplePeers() throws IOException, InterruptedException, ClassNotFoundException {
        Server server = new Server(3232);
        server.start();
        Peer peer = new Peer(3232, InetAddress.getByName("localhost"), "bob");
        Peer peer1 = new Peer(3232, InetAddress.getByName("localhost"),"bab");
        peer.connect();
        peer.createWhiteboard();
        peer1.connect();
        peer1.joinWhiteboard();
        Shape shape = new Circle(Color.RED, 200, 200, 300, 300);
        Message message = new ShapeToDraw(shape);
        peer.getPeerNotifier().getMessagesToSend().add(message);
        message = new ChatToDisplay(peer.getUsername(),"I like beans");
        peer.getPeerNotifier().getMessagesToSend().add(message);
        server.join();
    }
}