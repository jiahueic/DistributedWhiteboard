package peer.whiteboardaction;

import message.*;
import peer.GenericPeer;
import peer.Painter;
import peer.Peer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class WhiteboardAction {

    private Message message;
    private GenericPeer genericPeer;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;

    public GenericPeer getGenericPeer() {
        return genericPeer;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public WhiteboardAction (GenericPeer genericPeer){
        this.genericPeer = genericPeer;
        initMessage();
    }

    public abstract void initMessage();

    public void send() throws IOException, ClassNotFoundException {
        bufferedWriter = this.genericPeer.getBufferedWriter();
        bufferedReader = this.genericPeer.getBufferedReader();
        if (message != null){
            String s = Json.objectToJson(message);
            bufferedWriter.write(s);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        else {
            throw new IOException();
        }
    }

    /**
     * Code to run if readResponse returns true
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public abstract void processResponse() throws IOException, ClassNotFoundException;

    public boolean readResponse() throws IOException, ClassNotFoundException {
        String s1 = bufferedReader.readLine();
        Message message1 = Json.JsonToMessage(s1);
        if (message1.getName().equals("message.WhiteboardReply")) {
            WhiteboardReply whiteBoardReply = (WhiteboardReply) message1;
            System.out.println(whiteBoardReply.getMessage());
            System.out.println(whiteBoardReply.getSuccess());
            return whiteBoardReply.getSuccess();
        }
        return false;
    }

    public void doAction() throws IOException, ClassNotFoundException {
        send();
        if (readResponse()){
            processResponse();
        }
        else throw new IOException();
    }
}