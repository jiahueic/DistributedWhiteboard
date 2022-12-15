package server.reqhandle;

import message.AcceptUserResponse;
import message.Message;
import server.Server;

import java.io.BufferedWriter;
import java.net.Socket;

public class AcceptUserResponseHandler extends RequestHandler {
    public AcceptUserResponseHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    @Override
    public void handle() {
        AcceptUserResponse acceptUserResponse = (AcceptUserResponse) getMessage();
        String userToAccept = acceptUserResponse.getUserToAccept();
        if (acceptUserResponse.isAccepted()){
            getServer().getWaitingRoom().get(userToAccept).setResponse("accept");
        }
        else {
            getServer().getWaitingRoom().get(userToAccept).setResponse("decline");
        }
    }
}
