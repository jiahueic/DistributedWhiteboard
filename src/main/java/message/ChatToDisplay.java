package message;

public class ChatToDisplay extends CreateWhiteboardRequest {
    private String chatMessage;
    public ChatToDisplay(){
        super();
    }

    public ChatToDisplay(String username, String chatMessage){
        super(username);
        this.chatMessage = chatMessage;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    public String toString() {
        return  "ChatToDisplay{" +
                "chatMessage='" + chatMessage + '\'' +
                '}';
    }
}
