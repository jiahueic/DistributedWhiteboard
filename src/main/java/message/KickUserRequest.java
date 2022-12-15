package message;

public class KickUserRequest extends Message {
    private String username;
    private String userToKick;
    public KickUserRequest (){
        super();
    }

    public KickUserRequest(String username, String userToKick){
        super();
        this.username = username;
        this.userToKick = userToKick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserToKick() {
        return userToKick;
    }

    public void setUserToKick(String userToKick) {
        this.userToKick = userToKick;
    }
}
