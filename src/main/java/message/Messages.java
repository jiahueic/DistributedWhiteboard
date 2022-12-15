package message;

import java.util.ArrayList;
import java.util.List;

/***
 * Message that contains a list of messages
 */
public class Messages extends Message {
    private List<Message> messages;
    public Messages(){
        super();
        messages = new ArrayList<>();
    }

    public Messages(List<Message> messages){
        super();
        this.messages = messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
