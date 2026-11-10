import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import exceptions.RepositoryException;
import repository.Repository;
import repository.RepositoryFactory;
import service.PrietenieService;
import service.ReteaService;
import service.UserService;
import ui.ConsoleUI;
import validation.PrietenieValidator;
import validation.UserValidator;

/**
 * Entry point of the application. It creates the repositories through the
 * factory, builds the services and starts the console interface.
 */
public class Main {
    /**
     * Starts the application.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        try {
            RepositoryFactory factory = new RepositoryFactory();

            UserValidator userValidator = new UserValidator();
            PrietenieValidator prietenieValidator = new PrietenieValidator();

            Repository<Long, Utilizator> repoUser = factory.createUtilizatorRepository(userValidator);
            Repository<Tuple<Long, Long>, Prietenie> repoPrietenie = factory.createPrietenieRepository(prietenieValidator);

            UserService userService = new UserService(userValidator, repoUser, repoPrietenie);
            PrietenieService prietenieService = new PrietenieService(prietenieValidator, repoPrietenie, repoUser);
            ReteaService reteaService = new ReteaService(repoUser, repoPrietenie);

            prietenieService.removeOrphanFriendships();

            ConsoleUI ui = new ConsoleUI(userService, prietenieService, reteaService);
            ui.runUi();
        } catch (RepositoryException e) {
            System.err.println("Eroare la initializarea aplicatiei: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.err.println("Tipul de persistenta ales nu este implementat inca: " + e.getMessage());
        }
    }
}