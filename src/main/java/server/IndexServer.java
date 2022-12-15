package server;



import java.io.IOException;

// the main class to run Server Thread
// Server Thread starts IO thread

public class IndexServer {
    public static void main(String[] args) throws IOException {
        Server server = new Server(3200,15000);
        server.start();
    }
}
