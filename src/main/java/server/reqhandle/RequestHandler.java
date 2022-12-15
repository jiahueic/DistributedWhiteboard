package server.reqhandle;

import message.Message;
import server.Server;

import java.io.BufferedWriter;
import java.net.Socket;

public class RequestHandler {
    private Server server;
    private Socket socket;
    private Message message;
    private BufferedWriter bufferedWriter;
    public RequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter){
        this.server = server;
        this.message = message;
        this.socket = socket;
        this.bufferedWriter = bufferedWriter;
    }

    public void handle(){
        System.out.println("No request was handled");
    }

    public Server getServer() {
        return server;
    }

    public Message getMessage() {
        return message;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
}
