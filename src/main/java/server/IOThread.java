package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;

/**
 Adapted from COMP90015 Assignment 1
 Original author: Aaron Harwood
 */

public class IOThread extends Thread {
    private ServerSocket serverSocket;
    // change private to default
    private LinkedBlockingDeque<Socket> incomingConnections;
    private int timeout;

    /**
     * Create an IOThread, which attempts to the bind to the provided
     * port with a server socket. The thread must be explicitly started.
     * @param port the port for the server socket
     * @param incomingConnections the blocking queue to put incoming connections
     * @param timeout the timeout value to be set on incoming connections
     * @throws IOException
     */
    public IOThread(int port, LinkedBlockingDeque<Socket> incomingConnections, int timeout)
            throws IOException {
        this.timeout = timeout;
        this.incomingConnections=incomingConnections;
        serverSocket = new ServerSocket(port);
    }

    /**
     * Shutdown the server socket, which simply closes it.
     * @throws IOException
     */
    public void shutdown() throws IOException {
        serverSocket.close();
    }

    @Override
    public void run() {
        System.out.println("IO thread running");
        while(!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                try {
                    socket.setSoTimeout(this.timeout);
                    if(!incomingConnections.offer(socket)) {
                        socket.close();
                        System.out.println("IO thread dropped connection - incoming connection queue is full.");
                    }
                    else {
                        System.out.println("IO thread inserted connection into queue");
                    }
                } catch (IOException e) {
                    System.out.println("Something went wrong with the connection.");
                }
            } catch (IOException e) {
                System.out.println("IO thread failed to accept.");
                break;
            }
        }
        System.out.println("IO thread completed.");
    }
}
