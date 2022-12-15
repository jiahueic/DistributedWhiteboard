package peer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CreateWhiteBoard {
    public static void main(String args[]){
        CreateWhiteBoard createWhiteBoard = new CreateWhiteBoard();
        createWhiteBoard.executeCommand(args);
    }

    public void executeCommand(String[] args){
        // args[0] to args[1]
        if (args.length < 3){
            System.out.println("Not enough arguments, correct usage: [address] [port] [username]");
        }
        try {
            Manager manager = new Manager(Integer.parseInt(args[1]), InetAddress.getByName(args[0]), args[2]);
            manager.connect();
            manager.createWhiteboard();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
