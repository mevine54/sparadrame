package fr.afpa.pompey.cda22045.exceptions;

public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
