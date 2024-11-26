package fr.afpa.pompey.cda22045.exceptions;

public class ConflictException extends BaseException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
