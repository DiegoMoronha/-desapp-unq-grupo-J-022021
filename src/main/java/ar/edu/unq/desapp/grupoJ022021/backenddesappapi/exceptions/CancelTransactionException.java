package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions;

public class CancelTransactionException extends RuntimeException{
    public CancelTransactionException(String message){
        super(message);
    }
}
