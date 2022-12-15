package message;

public class CreateWhiteboardRequest extends Message {
    private String username;
    public CreateWhiteboardRequest(){
        super();
    }

    public CreateWhiteboardRequest(String username){
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "CreateWhiteboardRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
