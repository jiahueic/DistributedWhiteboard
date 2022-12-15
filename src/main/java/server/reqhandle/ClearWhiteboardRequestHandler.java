package server.reqhandle;

import message.Message;
import server.Server;
import server.ServerNotifier;

import java.io.BufferedWriter;
import java.net.Socket;

public class ClearWhiteboardRequestHandler extends RequestHandler {

    public ClearWhiteboardRequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    @Override
    public void handle() {
        getServer().getWhiteboardState().clear();
        for (ServerNotifier sn : getServer().getNotifierThreads().values()){
            sn.getMessagesToSend().add(getMessage());
        }
    }
}
