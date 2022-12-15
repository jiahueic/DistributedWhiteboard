package peer;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.InvalidRequestException;
import message.*;
import peer.commandhandler.CommandHandler;
import peer.commandhandler.CommandHandlerFactory;
import peer.whiteboardaction.WhiteboardAction;
import peer.whiteboardaction.WhiteboardActionFactory;
import shape.Circle;
import shape.Point;
import shape.PointCollection;
import shape.Shape;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class PeerListener extends Thread {
    private BufferedReader bufferedReader;
    private Painter painter;
    private Peer peer;

    public Peer getPeer() {
        return peer;
    }

    public PeerListener(BufferedReader bufferedReader, Peer peer) {
        this.bufferedReader = bufferedReader;
        this.peer = peer;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public Painter getPainter() {
        return painter;

    }

    @Override
    public void run() {
        System.out.println("Peer is listening on " + bufferedReader);
        // put updating code here
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (getBufferedReader().ready()){
                    Message message = readMsg(getBufferedReader());
                    CommandHandler commandHandler = CommandHandlerFactory.getInstance().createCommandHandler(this, message);
                    commandHandler.handle();
                    }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidRequestException e) {
                e.printStackTrace();
            }

        }
    }



    public Message readMsg(BufferedReader bufferedReader) throws ClassNotFoundException, IOException {
        String s = bufferedReader.readLine();
        if (s != null) {
            Message message = Json.JsonToMessage(s);
            return message;
        }
        throw new IOException("read null message");
    }
}
