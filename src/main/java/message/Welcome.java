package message;

public class Welcome extends Message {
    private String msg;

    public Welcome(){
        super();
    }

    public Welcome(String msg){
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
