package server.reqhandle;

import message.*;
import server.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ListenWhiteboardRequestHandler extends RequestHandler {
    public ListenWhiteboardRequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    public void handle(){
        String username = ((ListenWhiteboardRequest)getMessage()).getUsername();
        if (getServer().getConnectedPeers().containsKey(username)){
            try {
                getServer().addNotifier(getSocket(), username);
                Message reply = new WhiteboardReply(true, "Server is notifying you on whiteboard updates");
                getBufferedWriter().write(Json.objectToJson(reply));
                getBufferedWriter().newLine();
                getBufferedWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Message reply = new WhiteboardReply(false, "Unable to create notifier, username doesn't exist");
            try {
                getBufferedWriter().write(Json.objectToJson(reply));
                getBufferedWriter().newLine();
                getBufferedWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
