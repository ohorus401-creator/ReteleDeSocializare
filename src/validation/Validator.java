package validation;

import exceptions.ValidationException;

/**
 * Validates entities of a given type.
 * @param <T> the type of the entities that are verified
 */
public interface Validator<T> {
    /**
     * Verify if an entity is valid.
     * @param entity the entity that must be verified
     * @throws ValidationException if the entity is not valid
     */
    void validate(T entity);
}
