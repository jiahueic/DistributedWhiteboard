package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.InvalidRequestException;
import message.*;
import server.reqhandle.RequestHandler;
import server.reqhandle.RequestHandlerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;

import java.util.concurrent.LinkedBlockingDeque;

import java.nio.charset.StandardCharsets;

/**
 * Adapted from COMP90015 assignment 1
 * Server protocol implementation for the Index Server, which extends thread
 * and processes an unbounded number of incoming connections until it is interrupted.
 * @author aaron
 */

public class Server extends Thread {

    private LinkedBlockingDeque<Socket> incomingConnections;
    private IOThread ioThread;
    // This is to
    private HashMap<String, ServerListener> connectedPeers = new HashMap<>();
    public static ArrayList<String> onlineUsers = new ArrayList<String>();
    private HashMap<String, ServerNotifier> notifierThreads = new HashMap<>();
    private String manager;
    private int socketTimeout = 15000;
    private int port;
    private HashMap<String, WaitForManagerInput> waitingRoom = new HashMap<>();

    public HashMap<String, WaitForManagerInput> getWaitingRoom() {
        return waitingRoom;
    }

    public HashMap<String, ServerNotifier> getNotifierThreads() {
        return notifierThreads;
    }

    private List<Message> WhiteboardState = Collections.synchronizedList(new ArrayList<>());

    public List<Message> getWhiteboardState() {
        return WhiteboardState;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManager() {
        return manager;
    }

    /**
     * The Server thread must be explicitly started after creating an instance. The
     * Server starts an independent IOThread to accept connections.
     *
     * @param port
     * @param socketTimeout
     * @throws IOException
     */
    public Server(int port, int socketTimeout) throws IOException {
        this.socketTimeout = socketTimeout;
        this.port = port;
        startIOThread();
    }

    public Server(int port) throws IOException {
        this.port = port;
        startIOThread();
    }

    public boolean hasPeers(){
        return connectedPeers.size() > 0;
    }

    public HashMap<String, ServerListener> getConnectedPeers() {
        return connectedPeers;
    }

    public void startIOThread() throws IOException {
        incomingConnections = new LinkedBlockingDeque<Socket>();
        ioThread = new IOThread(port, incomingConnections, socketTimeout);
        ioThread.start();
    }

    @Override
    public void run() {
        System.out.println("Server thread running.");
        while(!isInterrupted()) {
            try {
                Socket socket = incomingConnections.take();
                processRequest(socket);
            } catch (InterruptedException e) {
                System.out.println("Server interrupted.");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server thread waiting for IO thread to stop...");
        ioThread.interrupt();
        try {
            ioThread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted while joining with IO thread.");
        }
        System.out.println("Server thread completed.");
    }

    public synchronized void addListener(Socket socket, String username) throws IOException {
        ServerListener serverListener = new ServerListener(this, socket, username);
        serverListener.start();
        connectedPeers.put(username, serverListener);
        onlineUsers.add(username);
    }

    public synchronized void addNotifier(Socket socket, String username) throws IOException {
        ServerNotifier serverNotifier = new ServerNotifier(this, socket, username);
        serverNotifier.start();
        notifierThreads.put(username, serverNotifier);
    }

    public void processRequest(Socket socket) throws IOException {
        System.out.println("Server is processing a request");
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

        try {
            Message message = null;
            message = readMsg(bufferedReader);

            if (message != null){
                RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory();
                RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(this, socket, message, bufferedWriter);
                requestHandler.handle();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Problem encountered deserialising message");
        } catch (InvalidRequestException e) {
            System.out.println("Request from client was invalid");
        }
    }

    public synchronized void closeConnection(String username){
        getNotifierThreads().get(username).interrupt();
        getConnectedPeers().get(username).interrupt();
        getNotifierThreads().remove(username);
        getConnectedPeers().remove(username);
        try {
            sendPeerList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public synchronized void closeAllConnections(){
        Message message = new KickUserRequest();
        String s = null;
        try {
            s = Json.objectToJson(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (String username : getNotifierThreads().keySet()){
            try {
                getNotifierThreads().get(username).getBufferedWriter().write(s);
                getNotifierThreads().get(username).getBufferedWriter().newLine();
                getNotifierThreads().get(username).getBufferedWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getNotifierThreads().get(username).interrupt();
            getConnectedPeers().get(username).interrupt();
        }
        getNotifierThreads().clear();
        getConnectedPeers().clear();
    }

    public synchronized void sendPeerList() throws JsonProcessingException {
        ArrayList<String> peers = new ArrayList<>(getConnectedPeers().keySet());
        Message message = new UpdatePeerList(peers);
        for (ServerNotifier sn : getNotifierThreads().values()){
            sn.getMessagesToSend().add(message);
        }
    }

    public Message readMsg(BufferedReader bufferedReader) throws IOException, ClassNotFoundException {
        String s = bufferedReader.readLine();
        if (s != null){
            Message message = Json.JsonToMessage(s);
            return message;
        }
        throw new IOException("read null from bufferedReader");
    }
}
