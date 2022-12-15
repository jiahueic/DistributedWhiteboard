package peer.dialogue;

import javax.swing.*;

public class ContainerDialogue {
    public static void main(String[] args) {
        //testing
        ContainerDialogue joinDialogue = new ContainerDialogue();
        joinDialogue.showDialogue("This is a notification");
    }
    public void showDialogue(String message) {
        JOptionPane.showMessageDialog(null,message);
        // 0 for yes, 1 for no, 2 for cancel
    }
}
