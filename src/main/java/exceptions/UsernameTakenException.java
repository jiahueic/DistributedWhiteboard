package exceptions;

public class UsernameTakenException extends Exception {
    public UsernameTakenException(String str){
        super(str);
    }
}
