package message;

public class AcceptUserResponse extends AcceptUserRequest {
    private boolean accepted = false;

    public AcceptUserResponse(){
        super();
    }

    public AcceptUserResponse(String userToAccept, boolean accepted){
        super(userToAccept);
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
