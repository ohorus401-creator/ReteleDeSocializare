package repository;

import domain.Entity;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import validation.Validator;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Repository that keeps the entities in memory and also in a text file, one
 * entity per line. The data is loaded at creation and the file is rewritten
 * after every successful save or delete.
 *
 * @param <ID> the type of the id of the entities
 * @param <E>  the type of the entities
 */
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private final String fileName;
    private final Validator<E> validator;
    /**
     * Converts a line of the file to an entity.
     *
     * @param line a line of the file
     * @return the entity described by the line
     * @throws RepositoryException if the line has a wrong format
     */
    protected abstract E extractEntity(String line);
    /**
     * Converts an entity to a line of the file.
     *
     * @param entity the entity to convert
     * @return the line that represents the entity
     */
    protected abstract String createEntityAsString(E entity);

    /**
     * Creates the repository and loads the entities from the file. If the file
     * does not exist, the repository starts empty. Invalid lines are reported
     * and skipped.
     *
     * @param fileName  the name of the file
     * @param validator validator applied to the entities read from the file
     * @throws RepositoryException if the file exists but cannot be read
     */
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        this.fileName = fileName;
        this.validator = validator;
        loadFromFile();
    }


    private void saveOnFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))){
            for ( E entity : findAll() ){
                writer.write(createEntityAsString(entity));
                writer.newLine();
            }
        }catch (IOException e){
            throw new RepositoryException("Eroare la scrierea in fisierul " + fileName + "!", e);
        }
    }

    private void loadFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))){
            int lineNumber = 0;
            String linie;
            while( (linie = reader.readLine()) != null ){
                lineNumber++;
                if (linie.isBlank()) {
                    continue;
                }
                try{
                    E newEntity = extractEntity(linie);
                    validator.validate(newEntity);
                    E result = super.save(newEntity);
                    if ( result != null ){
                        System.err.println(fileName + ", linia " + lineNumber + ": este o dublura!");
                    }
                } catch (ValidationException | RepositoryException e){
                    System.err.println(fileName + ", linia " + lineNumber + ": " + e.getMessage() + "!");
                }
            }
        }catch(FileNotFoundException e){
            //Fisier inexistent, pornim cu repository ul gol, fisierul se va crea la prima adaugare in fisier
        }catch ( IOException e ){
            throw new RepositoryException("Eroare la citirea fisierului: "+ fileName+ "!", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws RepositoryException if rewriting the file fails
     */
    @Override
    public E save(E entity){
        E result = super.save(entity);
        if ( result == null ){
            saveOnFile();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @throws RepositoryException if rewriting the file fails
     */
    @Override
    public E delete(ID id){
        E result = super.delete(id);
        if ( result != null ){
            saveOnFile();
        }
        return result;
    }
}

