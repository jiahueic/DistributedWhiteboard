package peer;

import message.Json;
import peer.whiteboardaction.WhiteboardActionFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public abstract class GenericPeer {
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    private int timeout;
    private int serverPort;
    private InetAddress serverIP;
    public String username;
    Painter painter;
    private Socket socket;

    private String className;

    public String getClassName() {
        return className;
    }

    public InetAddress getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public Painter getPainter() {
        return painter;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    private WhiteboardActionFactory whiteboardActionFactory;
    public WhiteboardActionFactory getWhiteboardActionFactory() {
        return whiteboardActionFactory;
    }

    public void setUsername(String userName){
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public GenericPeer(int serverPort, InetAddress serverIP, String username) {
        this.timeout = 15000;
        this.serverPort = serverPort;
        this.serverIP = serverIP;
        this.username = username;
        whiteboardActionFactory = new WhiteboardActionFactory(this);
        className = this.getClass().getCanonicalName();
    }

    public void connect(){
        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress(serverIP, serverPort), timeout);
            System.out.println("Connected to server "+ serverIP + " on port " + serverPort);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
