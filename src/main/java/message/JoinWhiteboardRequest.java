package message;

public class JoinWhiteboardRequest extends CreateWhiteboardRequest {
    public JoinWhiteboardRequest(){
        super();
    }
    public JoinWhiteboardRequest(String username){
        super(username);
    }
}
