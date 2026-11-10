package exceptions;

/** Thrown when an entity with the same id already exists. */
public class DuplicateEntityException extends RuntimeException {
    /**
     * Creates the exception.
     *
     * @param message the description of the error
     */
    public DuplicateEntityException(String message) {
        super(message);
    }
}
