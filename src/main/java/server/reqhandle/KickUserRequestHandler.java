package server.reqhandle;

import message.Json;
import message.KickUserRequest;
import message.Message;
import server.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class KickUserRequestHandler extends RequestHandler {

    public KickUserRequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    @Override
    public void handle() {
        KickUserRequest kickUserRequest = (message.KickUserRequest) getMessage();
        if (kickUserRequest.getUsername().equals(getServer().getManager())){
            String userToKick = kickUserRequest.getUserToKick();
            // send the kickuser request
            try {
                getServer().getNotifierThreads().get(userToKick).getBufferedWriter().write(Json.objectToJson(getMessage()));
                getServer().getNotifierThreads().get(userToKick).getBufferedWriter().newLine();
                getServer().getNotifierThreads().get(userToKick).getBufferedWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // close the connection on the server
            getServer().closeConnection(userToKick);
        }
    }
}
