package repository;

import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import exceptions.RepositoryException;
import validation.Validator;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Factory that creates the repositories of the application.
 * It is the only class that knows the repository types.
 * The type of persistence and its parameters are read from config.properties.
 */
public class RepositoryFactory {
    private Properties prop = loadConfig();

    private Properties loadConfig(){
        Properties prop = new Properties();
        String path = "src/main/resources/config.properties";
        try(FileReader fisier = new FileReader(path, StandardCharsets.UTF_8)){
            prop.load(fisier);
        } catch (IOException e) {
            throw new RepositoryException("Eroare la citirea din fisierul: " + path ,e);
        }

        return prop;
    }

    private RepositoryType getType(){
        String text = getValue("repository.type");

        try {
            return RepositoryType.valueOf(text.trim());
        } catch (IllegalArgumentException e){
            throw new RepositoryException("Valorile permise sunt: MEMORY, FILE, DATABASE.\nValoarea introdusa: " + text, e);
        }
    }

    private String getValue(String key){
        String text = prop.getProperty(key);

        if (text == null){
            throw new RepositoryException("Cheia " + key + " lipseste din config.properties!");
        }

        return text.trim();
    }

    /**
     * Creates the repository of the users, for the configured persistence type.
     * @param validator validator used for the users
     * @return the repository of the users
     * @throws RepositoryException if the configuration file cannot be read,
     *                             a required key is missing or the persistence type is unknown
     */
    public Repository<Long, Utilizator> createUtilizatorRepository(Validator<Utilizator> validator){
        RepositoryType type = getType();
        switch (type){
            case MEMORY: return new InMemoryRepository<>();
            case FILE: {
                String path = getValue("file.utilizatori");
                return new UtilizatorFileRepository(path,validator);
            }
            case DATABASE: {
                String url = getValue("db.url");
                String user = getValue("db.user");
                String password = getValue("db.password");

                return new UtilizatorDbRepository(validator,url, user, password);
            }
        } throw new RepositoryException("Tip de persistenta necunoscut: " + type);
    }

    /**
     * Creates the repository of the friendships, for the configured persistence type.
     *
     * @param validator validator used for the friendships
     * @return the repository of the friendships
     * @throws RepositoryException if the configuration file cannot be read,
     *                             a required key is missing or the persistence type is unknown
     */
    public Repository<Tuple<Long,Long>, Prietenie> createPrietenieRepository(Validator<Prietenie> validator){
        RepositoryType type = getType();
        switch (type){
            case MEMORY: return new InMemoryRepository<>();
            case FILE: {
                String path = getValue("file.prietenii");
                return new PrietenieFileRepository(path,validator);
            }
            case DATABASE: {
                String url = getValue("db.url");
                String user = getValue("db.user");
                String password = getValue("db.password");

                return new PrietenieDbRepository(validator,url, user, password);
            }
        } throw new RepositoryException("Tip de persistenta necunoscut: " + type);
    }
}
