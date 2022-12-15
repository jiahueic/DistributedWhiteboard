package message;
// reply message for both creating and joining whiteboard
public class WhiteboardReply extends Message {
    Boolean success;
    String message;
    public WhiteboardReply(){
        super();
    }

    public WhiteboardReply(boolean success, String message){
        super();
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
