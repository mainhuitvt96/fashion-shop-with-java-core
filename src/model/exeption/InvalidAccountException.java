package model.exeption;

public class InvalidAccountException extends Exception{
    public InvalidAccountException(String message){
        super(message);
    }
}
