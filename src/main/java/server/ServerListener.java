package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * Thread that listens for operation done on the shared whiteboard
 * One thread per peer connected
 */
public class ServerListener extends Thread {
    private Server server;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public String getUsername() {
        return username;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public Socket getSocket() {
        return socket;
    }

    public Server getServer() {
        return server;
    }

    public ServerListener(Server server, Socket socket, String username) throws IOException {
        this.server = server;
        this.socket = socket;
        this.socket.setSoTimeout(0);
        this.username = username;
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        System.out.println("Listener is running for " + socket.toString());
        while (!isInterrupted()){
            try {
                server.processRequest(socket);
            }
            catch(SocketException e){
                e.printStackTrace();
                break;
            }
            catch (IOException e) {
                System.out.println("Listener encountered an IOException");
                e.printStackTrace();
                //break;
            }
        }

        if (getUsername().equals(getServer().getManager())){
            getServer().closeAllConnections();
            getServer().getWhiteboardState().clear();
        }

        else {
            getServer().closeConnection(getUsername());
            System.out.println("Interrupting notifier thread");
            System.out.println("Closing socket");
            try {
                getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // send updated list of peers to all peers
            System.out.println("Listener thread closed");
        }
    }
}
