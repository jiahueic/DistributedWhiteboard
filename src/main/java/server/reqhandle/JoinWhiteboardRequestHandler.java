package server.reqhandle;

import message.*;
import server.Server;
import server.WaitForManagerInput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/*
Handles the request for joining a white board
 */
public class JoinWhiteboardRequestHandler extends RequestHandler {
    public JoinWhiteboardRequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    @Override
    public void handle() {
        String username = ((JoinWhiteboardRequest)getMessage()).getUsername();
        // if username already exists in system
        if (getServer().getConnectedPeers().containsKey(username)){
            System.out.println("Peer unable to join as username already taken");
            Message message = new WhiteboardReply(false, "Username already taken, please use a different username");
            try {
                getBufferedWriter().write(Json.objectToJson(message));
                getBufferedWriter().newLine();
                getBufferedWriter().flush();
            } catch (IOException e) {
                System.out.println("Encountered IO exception");
            }
        }
        else if (getServer().hasPeers()){
            // satisfied conditions for joining
            // send accept user request to manager
            Message message = new AcceptUserRequest(username);
            getServer().getNotifierThreads().get(getServer().getManager()).addMessageToSend(message);
            // create the thread to wait for manager input
            WaitForManagerInput waitForManagerInput = new WaitForManagerInput(this, username);
            // put thread into waiting room
            getServer().getWaitingRoom().put(username, waitForManagerInput);
            // start the thread to wait for manager input
            waitForManagerInput.start();
        }
        else {
            System.out.println("Peer unable to join as no whiteboard exists");
            try {
                Message reply = new WhiteboardReply(false, "Unable to join as no whiteboard exists");
                getBufferedWriter().write(Json.objectToJson(reply));
                getBufferedWriter().newLine();
                getBufferedWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
