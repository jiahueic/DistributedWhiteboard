package server.reqhandle;

import message.Message;
import peer.PeerNotifier;
import server.Server;
import server.ServerNotifier;

import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Map;

public class ShapeToDrawRequestHandler extends RequestHandler {
    public ShapeToDrawRequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) {
        super(server, socket, message, bufferedWriter);
    }

    @Override
    public void handle() {
        System.out.println("server is handling a draw shape request");
        // add the shape to whiteboardstate
        getServer().getWhiteboardState().add(getMessage());
        // pass the shape to all the notifier threads
        for (Map.Entry<String, ServerNotifier> set : getServer().getNotifierThreads().entrySet()) {
            Message message = getMessage();
            ServerNotifier sn = set.getValue();
            sn.addMessageToSend(message);
            System.out.println(sn.getMessagesToSend());
    }
        //debug
        System.out.println(getServer().getWhiteboardState());
}
}
