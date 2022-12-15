package peer;

import files.FileHandler;
import message.*;
import peer.dialogue.ContainerDialogue;
import peer.dialogue.OpenDialogue;
import shape.Shape;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

// first person who creates a whiteboard
public class Manager extends Peer {
    public Manager(int serverPort, InetAddress serverIP, String username) {
        super(serverPort, serverIP, username);
    }

    @Override
    public void kickUser(String username) {
        if (getUsername().equals(username)){
            System.out.println("Cannot kick yourself");
        }
        else{
            Message message = new KickUserRequest(getUsername(), username);
            getPeerNotifier().getMessagesToSend().add(message);
        }
    }

    @Override
    public void clearWhiteboard(){
        OpenDialogue openDialogue = new OpenDialogue();
        if (openDialogue.showDialogue()){
            if (!openDialogue.getFileName().isEmpty()){
                setRecentFileName(openDialogue.getFileName());
                saveWhiteboard();
                System.out.println("Clearing the whiteboard");
                Message message = new ClearWhiteboardRequest();
                getPeerNotifier().getMessagesToSend().add(message);
            }

            else {
                ContainerDialogue containerDialogue = new ContainerDialogue();
                containerDialogue.showDialogue("Invalid filename");
            }
        }
        else {
            System.out.println("Clearing the whiteboard");
            Message message = new ClearWhiteboardRequest();
            getPeerNotifier().getMessagesToSend().add(message);
        }
    }

    @Override
    public synchronized void saveWhiteboard() {
        if (getRecentFileName() != null){
            System.out.println("Saving file to: " + getRecentFileName());
            FileHandler fileHandler = new FileHandler(getRecentFileName());
            try {
                fileHandler.writeWhiteBoardStateToFile(getWhiteboardState());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            new ContainerDialogue().showDialogue("No recent filename");
            System.out.println("no recent filename");
        }
    }

    @Override
    public synchronized void saveAsWhiteboard() {
        String filename = JOptionPane.showInputDialog("Please enter your fileName");
        if (!filename.isEmpty()){
            setRecentFileName(filename);
            saveWhiteboard();
        }
        else {
            new ContainerDialogue().showDialogue("No filename specified");
        }
    }

    @Override
    public synchronized void loadWhiteboard() {
        String filename = JOptionPane.showInputDialog("Please enter your fileName");
        if (!filename.isEmpty()){
            setRecentFileName(filename);
            System.out.println("Opening white board from file: " + filename);
            FileHandler fileHandler = new FileHandler(filename);
            try {
                List<Shape> shapes = fileHandler.fileToWhiteBoardState();
//            // clear the whiteboard
//            clearWhiteboard();
//            getWhiteboardState().addAll(shapes);
//            getPainter().repaint();

                // marshall all of the shapes and send them to server
                Message message = new ShapeList(shapes);
                getPeerNotifier().getMessagesToSend().add(message);
            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                e.printStackTrace();
                new ContainerDialogue().showDialogue("Error opening file");
                setRecentFileName(null);
            }
        }
        else {
            new ContainerDialogue().showDialogue("No filename specified");
        }
    }

    @Override
    public void closeWhiteboard() {
        System.exit(0);
    }
}

