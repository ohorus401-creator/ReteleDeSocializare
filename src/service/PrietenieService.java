package service;

import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import exceptions.DuplicateEntityException;
import exceptions.InexistentIdException;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import repository.Repository;
import validation.Validator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service that manages the friendships between users.
 */
public class PrietenieService {
    private final Validator<Prietenie> validator;
    private final Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private final Repository<Long, Utilizator> repoUser;

    /**
     * Creates the service.
     *
     * @param validator validator used for the friendships
     * @param repoPrietenie repository of the friendships
     * @param repoUser repository of the users
     */
    public PrietenieService(Validator<Prietenie> validator, Repository<Tuple<Long,Long>, Prietenie> repoPrietenie, Repository<Long, Utilizator> repoUser) {
        this.validator = validator;
        this.repoPrietenie = repoPrietenie;
        this.repoUser = repoUser;
    }

    /**
     * Adds a friendship between two registered users.
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     * @throws ValidationException if the ids are null, not positive or equal
     * @throws InexistentIdException if one of the users doesn't exist
     * @throws DuplicateEntityException if the friendship already exists
     * @throws RepositoryException if saving the friendship fails
     */
    public void addFriendship(Long id1, Long id2){
        Prietenie p = new Prietenie(id1, id2, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        validator.validate(p);

        Utilizator user1 = repoUser.findOne(id1);
        Utilizator user2 = repoUser.findOne(id2);

        if (user1 == null){
            throw new InexistentIdException("Utilizatorul cu id ul " + id1 + " nu exista!");
        }

        if (user2 == null){
            throw new InexistentIdException("Utilizatorul cu id ul " + id2 + " nu exista!");
        }

        if( repoPrietenie.save(p) != null ){
            throw new DuplicateEntityException("Prietenia dintre cei 2 utilizatori este deja inregistrata!");
        }
    }

    /**
     * Deletes a friendship between two users.
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     * @throws ValidationException if the ids are null, not positive or equal
     * @throws InexistentIdException if one of the users or the friendship does not exist
     * @throws RepositoryException if updating the storage fails
     */
    public void removeFriendship(Long id1, Long id2){
        Prietenie p = new Prietenie(id1, id2, LocalDateTime.now());
        validator.validate(p);

        Utilizator user1 = repoUser.findOne(id1);
        Utilizator user2 = repoUser.findOne(id2);

        if (user1 == null){
            throw new InexistentIdException("Utilizatorul cu id ul " + id1 + " nu exista!");
        }

        if (user2 == null){
            throw new InexistentIdException("Utilizatorul cu id ul " + id2 + " nu exista!");
        }

        if (repoPrietenie.delete(p.getId()) == null){
            throw new InexistentIdException("Prietenia nu exista!");
        }
    }

    /**
     * Removes every friendship that refers to a user who does not exist.
     * Meant to be called once at startup, after both repositories are loaded.
     *
     * @throws RepositoryException if updating the storage fails
     */
    public void removeOrphanFriendships() {
        for (Prietenie friendship : repoPrietenie.findAll()) {
            Long id1 = friendship.getIdUser1();
            Long id2 = friendship.getIdUser2();

            if (repoUser.findOne(id1) == null || repoUser.findOne(id2) == null) {
                repoPrietenie.delete(friendship.getId());
            }
        }
    }

    /**
     * Returns all the friendships.
     * @return a list with all the friendships
     */
    public List<Prietenie> getAllFriendships(){
        return repoPrietenie.findAll();
    }
}
