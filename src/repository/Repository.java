package repository;
import domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>>{

    /**
     * Finds an entity with the given id.
     * @param id the id of the entity searched
     * @return the entity searched or null if it doesn't exist
     * @throws IllegalArgumentException if the id is null
     */
    E findOne(ID id);

    /**
     * Finds all the entities that are registered.
     * @return a list with all the entities
     */
    List<E> findAll();

    /**
     * Saves an entity in the repository.
     * @param entity the entity to save
     * @return the existing entity if its id is already in use or
     *         null if the entity was successfully saved
     * @throws IllegalArgumentException if the entity is null
     */
    E save(E entity);

    /**
     * Deletes an entity from the repository by the id given.
     * @param id the id of the entity to be deleted
     * @return the deleted entity or null if no entity had the given id
     * @throws IllegalArgumentException if the id is null
     */
    E delete(ID id);
}
