package message;

public class AcceptUserRequest extends Message {
    private String userToAccept;
    public AcceptUserRequest(){
        super();
    }

    public AcceptUserRequest(String userToAccept){
        super();
        this.userToAccept = userToAccept;
    }

    public String getUserToAccept() {
        return userToAccept;
    }

    public void setUserToAccept(String userToAccept) {
        this.userToAccept = userToAccept;
    }
}
