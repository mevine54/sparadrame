package fr.afpa.pompey.cda22045.exceptions;

public class DatabaseException extends BaseException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
