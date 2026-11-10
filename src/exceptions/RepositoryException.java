package exceptions;
/**
 * Thrown when an error appears in the persistence layer, for example when
 * a file cannot be read or written, or when a line has a wrong format.
 * The original exception is kept as the cause.
 */
public class RepositoryException extends RuntimeException {
    /**
     * Creates the exception with a message and the original cause.
     *
     * @param message the description of the error
     * @param cause the original exception, for example IOException
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates the exception with a message only.
     *
     * @param message the description of the error
     */
    public RepositoryException(String message) {
        super(message);
    }
}
