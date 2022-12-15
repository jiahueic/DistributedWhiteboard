package server;

import message.JoinWhiteboardRequest;
import message.Json;
import message.Message;
import message.WhiteboardReply;
import server.reqhandle.JoinWhiteboardRequestHandler;

import java.io.IOException;

public class WaitForManagerInput extends Thread {
    private JoinWhiteboardRequestHandler j;
    private String response = null;
    private String username;

    public void setResponse(String response) {
        this.response = response;
    }

    public WaitForManagerInput(JoinWhiteboardRequestHandler joinWhiteboardRequestHandler, String username){
        this.j = joinWhiteboardRequestHandler;
        this.username = username;
    }

    @Override
    public void run() {
        while (response == null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (response.equals("accept")){
            try {
                // add the server listener and notifier
                j.getServer().addListener(j.getSocket(), username);
                j.getServer().addNotifier(j.getSocket(), username);

                // send the whiteboard history to the user
                j.getServer().getNotifierThreads().get(username).getMessagesToSend().addAll(j.getServer().getWhiteboardState());
                // send the success message
                Message reply = new WhiteboardReply(true, "Welcome to the Shared Whiteboard");
                j.getBufferedWriter().write(Json.objectToJson(reply));
                j.getBufferedWriter().newLine();
                j.getBufferedWriter().flush();
                j.getServer().sendPeerList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            Message reply = new WhiteboardReply(false, "Manager declined join request");
            try {
                j.getBufferedWriter().write(Json.objectToJson(reply));
                j.getBufferedWriter().newLine();
                j.getBufferedWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // clear user from waiting room
        j.getServer().getWaitingRoom().remove(username);
    }
}
