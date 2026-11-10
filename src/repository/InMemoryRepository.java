package repository;
import domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository that keeps the entities in memory.
 * @param <ID> the type of the id of the entities.
 * @param <E> the type of the entities
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E>{
    protected Map<ID, E> entities = new HashMap<>();

    /** {@inheritDoc} */
    @Override
    public E findOne(ID id) {
        if (id == null)
            throw new IllegalArgumentException("Id ul nu trebuie sa fie null");
        return entities.get(id);
    }

    /** {@inheritDoc} */
    @Override
    public List<E> findAll() {
        return new ArrayList<>(entities.values());
    }

    /** {@inheritDoc} */
    @Override
    public E save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entitatea nu trebuie sa fie null");
        return entities.putIfAbsent(entity.getId(), entity);
    }

    /** {@inheritDoc} */
    @Override
    public E delete(ID id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");
        return entities.remove(id);
    }
}
