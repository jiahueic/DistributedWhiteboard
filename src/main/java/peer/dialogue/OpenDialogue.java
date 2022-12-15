package peer.dialogue;

import javax.swing.*;

public class OpenDialogue {
    private String fileName=null;

    public String getFileName() {
        return fileName;
    }

    public static void main(String[] args) {
        //testing
    }
    public boolean showDialogue() {
        String reqMess = "Would you like to save the current file?";
        int userRes = JOptionPane.showConfirmDialog(null, reqMess);
        if (userRes == 0) {
            fileName = JOptionPane.showInputDialog("Please enter your fileName");
            return true;
        }
        return false;
    }
}
