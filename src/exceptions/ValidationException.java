package exceptions;

import java.util.List;

/** Thrown when an entity does not pass the validation. */
public class ValidationException extends RuntimeException{
    /**
     * Creates the exception.
     *
     * @param messages the validation errors, joined into one message
     */
    public ValidationException(List<String> messages){
        super(String.join("\n", messages));
    }
}
