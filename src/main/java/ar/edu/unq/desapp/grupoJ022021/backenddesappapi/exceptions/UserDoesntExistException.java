package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.Exceptions;

public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException(String message) {
        super(message);
    }
}
