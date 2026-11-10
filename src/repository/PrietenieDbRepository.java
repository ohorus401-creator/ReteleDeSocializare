package repository;

import domain.Prietenie;
import domain.Tuple;
import validation.Validator;

import java.util.List;

/**
 * The JDBC implementation is part of the next laboratory,
 * so every operation currently throws UnsupportedOperationException.
 */
public class PrietenieDbRepository implements Repository<Tuple<Long,Long>, Prietenie> {
    private final Validator<Prietenie> validator;
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
    public PrietenieDbRepository(Validator<Prietenie> validator, String url, String user, String password) {
        this.validator = validator;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public Prietenie findOne(Tuple<Long, Long> longLongTuple) {
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public List<Prietenie> findAll() {
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public Prietenie save(Prietenie entity) {
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException always, the implementation is not written yet
     */
    @Override
    public Prietenie delete(Tuple<Long, Long> longLongTuple) {
        throw new UnsupportedOperationException("Implementarea JDBC vine la Lab 4");
    }
}
