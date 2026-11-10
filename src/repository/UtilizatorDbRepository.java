package repository;

import domain.Utilizator;
import validation.Validator;

import java.util.List;

/**
 * The JDBC implementation is part of the next laboratory,
 * so every operation currently throws UnsupportedOperationException.
 */
public class UtilizatorDbRepository implements Repository<Long, Utilizator>{
    private final Validator<Utilizator> validator;
    private final String url;
    private final String user;
    private final String password;

    /**
     * Creates the repository.
     *
     * @param validator validator used for the entities
     * @param url       the database url
     * @param user      the database user
     * @param password  the database password
     */
    public UtilizatorDbRepository(Validator<Utilizator> validator, String url, String user, String password) {
        this.validator = validator;
        this.user = user;
        this.url = url;
        this.password = password;
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public Utilizator findOne(Long id){
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public List<Utilizator> findAll() {
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public Utilizator save(Utilizator entity) {
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public Utilizator delete(Long id){
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }

}
