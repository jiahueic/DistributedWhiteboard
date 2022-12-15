package peer.dialogue;
import javax.swing.JOptionPane;
public class JoinDialogue {
    private int userResponse;
    public static void main(String[] args) {
        //testing
        JoinDialogue joinDialogue = new JoinDialogue();
        joinDialogue.showDialogue("John");
    }
    public int showDialogue(String userName) {
        String reqMess = "Allow " + userName + " to join?";

        userResponse = JOptionPane.showConfirmDialog(null,reqMess);
        return userResponse;
        // 0 for yes, 1 for no, 2 for cancel
    }
}
