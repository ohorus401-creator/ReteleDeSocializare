package repository;

import domain.Prietenie;
import domain.Tuple;
import exceptions.RepositoryException;
import validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * File repository for friendships. Each line has the format
 * idUser1;idUser2;date, where the date is in ISO format.
 */
public class PrietenieFileRepository extends AbstractFileRepository<Tuple<Long,Long>, Prietenie> {

    /**
     * Creates the repository and loads the data from the file.
     *
     * @param fileName  the path of the file
     * @param validator validator applied to the entities read from the file
     */
    public PrietenieFileRepository(String fileName, Validator<Prietenie> validator ){super(fileName,validator);}

    /** {@inheritDoc} */
    @Override
    protected Prietenie extractEntity(String line) {
        String[] args = line.strip().split(";", -1);
        if (args.length != 3){
            throw new RepositoryException("Numar gresit de argumente: " + args.length);
        }

        try{
            Prietenie friendship = new Prietenie(Long.parseLong(args[0]),Long.parseLong(args[1]), LocalDateTime.parse(args[2]));
            return friendship;
        } catch (NumberFormatException e) {
            throw new RepositoryException("Id urile utilizatorilor trebuie sa fie numere intregi!",e);
        } catch (DateTimeParseException e) {
            throw new RepositoryException("Data nu este in format corect!", e);
        }

    }

    /** {@inheritDoc} */
    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getIdUser1() + ";" + entity.getIdUser2() + ";" + entity.getData();
    }
}
