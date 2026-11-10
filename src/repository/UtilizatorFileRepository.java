package repository;

import domain.Utilizator;
import exceptions.RepositoryException;
import validation.Validator;

/**
 * File repository for users. Each line has the format id;prenume;nume.
 */
public class UtilizatorFileRepository extends AbstractFileRepository<Long, Utilizator> {

    /**
     * Creates the repository and loads the data from the file.
     *
     * @param fileName  the path of the file
     * @param validator validator applied to the entities read from the file
     */
    public UtilizatorFileRepository(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

    /** {@inheritDoc} */
    @Override
    protected Utilizator extractEntity(String line) {
        String[] args = line.strip().split(";",-1);
        if ( args.length != 3 ){
            throw new RepositoryException("Numar nepotrivit de argumente: " + args.length);
        }

        try{
            Utilizator user = new Utilizator(Long.parseLong(args[0]), args[1], args[2]);
            return user;
        } catch (NumberFormatException e){
            throw  new RepositoryException("ID ul nu este un numar intreg!", e);
        }

    }

    /** {@inheritDoc} */
    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId() + ";" + entity.getPrenume() + ";" + entity.getNume();
    }
}
