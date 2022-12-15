package peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class JoinWhiteBoard {
    public static void main(String[] args){
        JoinWhiteBoard joinWhiteBoard = new JoinWhiteBoard();
        joinWhiteBoard.executeCommand(args);
    }

    public void executeCommand(String[] args){
        // args[0] to args[1]
        if (args.length < 3){
            System.out.println("Not enough arguments, correct usage: [address] [port] [username]");
        }
        try {
            Peer peer = new Peer(Integer.parseInt(args[1]), InetAddress.getByName(args[0]), args[2]);
            peer.connect();
            peer.joinWhiteboard();
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("unable to join whiteboard");
        }
    }
}
