package fr.afpa.pompey.cda22045.exceptions;

public class CustomExceptionFactory {
    public static ValidationException validationError(String message) {
        return new ValidationException(message);
    }

    public static DatabaseException databaseError(String message, Throwable cause) {
        return new DatabaseException(message, cause);
    }

    public static NotFoundException notFound(String message) {
        return new NotFoundException(message);
    }

    public static UnauthorizedAccessException unauthorizedAccess(String message) {
        return new UnauthorizedAccessException(message);
    }

    public static ConflictException conflict(String message) {
        return new ConflictException(message);
    }
}
