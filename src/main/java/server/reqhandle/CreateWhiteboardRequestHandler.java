package server.reqhandle;

import message.WhiteboardReply;
import message.CreateWhiteboardRequest;
import message.Json;
import message.Message;
import server.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/*
Handles the request for creating a whiteboard
 */
public class CreateWhiteboardRequestHandler extends RequestHandler{
    public CreateWhiteboardRequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    public void handle() {
        //check if there are peers connected to server already
        if (getServer().hasPeers()){
            // peer will not be able to create the whiteboard since they are not the first to join
            Message reply = new WhiteboardReply(false, "Manager already exists, unable to create whiteboard");
            try {
                getBufferedWriter().write(Json.objectToJson(reply));
                getBufferedWriter().newLine();
                getBufferedWriter().flush();
            } catch (IOException e) {
                System.out.println("IOException when trying to send message");
            }
        }

        else {
            String username = ((CreateWhiteboardRequest)getMessage()).getUsername();
            try {
                // set the manager username
                getServer().setManager(username);
                // add the server listener
                getServer().addListener(getSocket(), username);
                getServer().addNotifier(getSocket(), username);

                // send the success message
                Message reply = new WhiteboardReply(true, "Welcome to the Shared Whiteboard");
                getBufferedWriter().write(Json.objectToJson(reply));
                getBufferedWriter().newLine();
                getBufferedWriter().flush();
                getServer().sendPeerList();
            } catch (IOException e) {
                System.out.println("Server encountered IO exception when creating whiteboard");
            }
        }
    }
}
