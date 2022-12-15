package peer;

import message.Json;
import message.Message;
import peer.whiteboardaction.WhiteboardAction;
import peer.whiteboardaction.WhiteboardActionFactory;
import shape.PointCollection;
import shape.Shape;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
  Based on code from COMP90015 Assignment 1
  Original author of skeleton code: Aaron Harwood
 */


public class Peer extends GenericPeer {
    private PeerListener peerListener;
    private PeerNotifier peerNotifier;
    private DrawTimer drawTimer;
    private List<Shape> WhiteboardState = Collections.synchronizedList(new ArrayList<>());
    private String recentFileName;

    public String getRecentFileName() {
        return recentFileName;
    }

    public void setRecentFileName(String recentFileName) {
        this.recentFileName = recentFileName;
    }

    public List<Shape> getWhiteboardState() {
        return WhiteboardState;
    }

    public DrawTimer getDrawTimer() {
        return drawTimer;
    }

    public void setDrawTimer(DrawTimer drawTimer) {
        this.drawTimer = drawTimer;
    }

    public PeerListener getPeerListener() {
        return peerListener;
    }

    public void setPeerListener(PeerListener peerListener) {
        this.peerListener = peerListener;
    }

    public void setPeerNotifier(PeerNotifier peerNotifier) {
        this.peerNotifier = peerNotifier;
    }

    public Peer(int serverPort, InetAddress serverIP, String username) {
        super(serverPort, serverIP, username);
        setDrawTimer(new DrawTimer());
        getDrawTimer().start();
    }

    public PeerNotifier getPeerNotifier() {
        return peerNotifier;

    }

    public void createWhiteboard() throws IOException, ClassNotFoundException {
        WhiteboardAction whiteboardAction = getWhiteboardActionFactory().createWhiteboardAction(WhiteboardActionFactory.Action.CREATE);
        whiteboardAction.doAction();
    }

    public void joinWhiteboard() throws IOException, ClassNotFoundException {
        WhiteboardAction whiteboardAction = getWhiteboardActionFactory().createWhiteboardAction(WhiteboardActionFactory.Action.JOIN);
        whiteboardAction.doAction();
    }

    public void kickUser(String username){
        System.out.println("Error: Only managers can kick users");
    }

    public void clearWhiteboard(){
        System.out.println("Error: Only managers can create a new whiteboard");
    }

    public void startPeerNotifier() throws IOException {
        peerNotifier = new PeerNotifier(getBufferedWriter());
        peerNotifier.start();
    }

    public void startPeerListener() throws IOException {
        peerListener = new PeerListener(getBufferedReader(), this);
        peerListener.setPainter(painter);
        peerListener.start();
    }

    public void saveWhiteboard(){
        System.out.println("Error: Only managers can save the whiteboard");
    }

    public synchronized void saveAsWhiteboard(){
        System.out.println("Error: Only managers can save a whiteboard");
    }

    public void loadWhiteboard(){
        System.out.println("Error: Only managers can load a whiteboard");
    }

    public void closeWhiteboard(){
        System.out.println("Error, only managers can close the whiteboard");
    }
}