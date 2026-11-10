package exceptions;

/** Thrown when an id does not exist in the system. */
public class InexistentIdException extends RuntimeException {
    /**
     * Creates the exception.
     *
     * @param message the description of the error
     */
    public InexistentIdException(String message) {
        super(message);
    }
}
