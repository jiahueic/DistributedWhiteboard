package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import message.Json;
import message.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerNotifier extends ServerListener {
    private List<Message> messagesToSend;

    public ServerNotifier(Server server, Socket socket, String username) throws IOException {
        super(server, socket, username);
        messagesToSend = Collections.synchronizedList(new ArrayList<Message>());
    }

    public List<Message> getMessagesToSend() {
        return messagesToSend;
    }

    public void addMessageToSend(Message message){
        messagesToSend.add(message);
    }

    @Override
    public void run() {
        System.out.println("Server is notifying " + getSocket().toString());
        while (!isInterrupted() && getServer().getConnectedPeers().containsKey(getUsername())){
            while (messagesToSend.size()>0){
                Message m = messagesToSend.remove(0);
                try {
                    String s = Json.objectToJson(m);
                    getBufferedWriter().write(s);
                    getBufferedWriter().newLine();
                    getBufferedWriter().flush();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        System.out.println("Notifier thread closed");
    }
}
