package fr.afpa.pompey.cda22045.exceptions;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
