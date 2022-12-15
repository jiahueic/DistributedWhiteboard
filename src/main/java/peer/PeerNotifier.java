package peer;

import com.fasterxml.jackson.core.JsonProcessingException;
import message.Json;
import message.Message;
import shape.Shape;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PeerNotifier extends Thread {
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    private ArrayList<Message> messagesToSend = new ArrayList<>();
    public PeerNotifier(BufferedWriter bufferedWriter) throws IOException {
    this.bufferedWriter = bufferedWriter;
    }

    public ArrayList<Message> getMessagesToSend() {
        return messagesToSend;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void run() {
        System.out.println("Peer is notifying " + bufferedWriter);

        while (!isInterrupted()) {
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
    }
}