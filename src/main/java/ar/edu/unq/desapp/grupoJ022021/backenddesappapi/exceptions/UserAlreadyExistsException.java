package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message) {
        super(message);

    }
}
