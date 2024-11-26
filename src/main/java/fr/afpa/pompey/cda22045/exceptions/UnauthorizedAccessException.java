package fr.afpa.pompey.cda22045.exceptions;

public class UnauthorizedAccessException extends BaseException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
