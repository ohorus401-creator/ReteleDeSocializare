package service;

import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import exceptions.InexistentIdException;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import repository.Repository;
import exceptions.DuplicateEntityException;
import validation.Validator;

import java.util.List;
import java.util.Objects;

/**
 * Service that manages the users of the network.
 */
public class UserService {
    private final Validator<Utilizator> validator;
    private final Repository<Long, Utilizator> repoUser;
    private final Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;

    /**
     * Creates the service.
     *
     * @param validator     validator used for the users
     * @param repoUser      repository of the users
     * @param repoPrietenie repository of the friendships
     */
    public UserService(Validator<Utilizator> validator,  Repository<Long, Utilizator> repoUser, Repository<Tuple<Long, Long>, Prietenie> repoPrietenie){
        this.validator = validator;
        this.repoUser = repoUser;
        this.repoPrietenie = repoPrietenie;
    }

    /**
     * Adds a new user.
     *
     * @param id the id of the user
     * @param prenume the first name of the user
     * @param nume the last name of the user
     * @throws ValidationException if the user data is not valid
     * @throws DuplicateEntityException if a user with the given id already exists
     * @throws RepositoryException if saving the user fails
     */
    public void addUser(Long id, String prenume, String nume){
        Utilizator user = new Utilizator(id, prenume, nume);

        validator.validate(user);
        Utilizator exist = repoUser.save(user);
        if (exist != null){
            throw new DuplicateEntityException("ID ul utilizatorului exista deja in sistem!");
        }
    }

    /**
     * Removes the user with the given id, together with all of their friendships.
     *
     * @param id the id of the user to remove
     * @throws InexistentIdException if no user has the given id
     * @throws RepositoryException   if updating the storage fails
     */
    public void removeUser(Long id){

        Utilizator exist = repoUser.delete(id);
        if (exist == null){
            throw new InexistentIdException("Nu exista un user salvat cu acest id in sistem!");
        }

        List<Prietenie> prietenii = repoPrietenie.findAll();
        for (Prietenie pr : prietenii){
            Long a = pr.getIdUser1();
            Long b = pr.getIdUser2();

            if (Objects.equals(a, id) || Objects.equals(b, id)){
                repoPrietenie.delete(pr.getId());
            }
        }
    }

    /**
     * Returns all the users.
     * @return a list with all the users
     */
    public List<Utilizator> getAllUsers(){
        return repoUser.findAll();
    }

    /**
     * Finds a user by id
     * @param id the id of the user
     * @return the user, or null if it does not exist
     */
    public Utilizator findAfterId(Long id){
        return repoUser.findOne(id);
    }
}
