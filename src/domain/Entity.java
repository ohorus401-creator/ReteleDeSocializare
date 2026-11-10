package domain;

import java.util.Objects;

/**
 * Base class of all the entities. An entity is identified by its id, and two
 * entities of the same class are equal if they have the same id.
 *
 * @param <T> the type of the id
 */
public abstract class Entity<T> {
    private T id;

    /**
     * Returns the id of the entity.
     *
     * @return the id of the entity
     */
    public T getId() {
        return id;
    }

    /**
     * Sets the id of the entity.
     *
     * @param id the new id
     */
    public void setId(T id) {
        this.id = id;
    }

    /**
     * Checks if this entity is equal to another object. Two entities are equal
     * if they have the same class and the same id.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    /**
     * Returns the hash code of the entity, computed from its id.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
