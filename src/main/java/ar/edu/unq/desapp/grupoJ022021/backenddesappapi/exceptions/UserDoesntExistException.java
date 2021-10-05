package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions;

public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException(String message) {
        super(message);
    }
}
