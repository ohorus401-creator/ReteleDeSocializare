package validation;

import domain.Utilizator;
import exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator for Utilizator entities.
 */
public class UserValidator implements Validator<Utilizator> {

    /**
     * Verify if a user is valid.
     * @param user the user that must be verified
     * @throws ValidationException if the id is null or not positive, or if the first name or last name is empty,
     *                             does not start with an uppercase letter, contains characters other than letters,
     *                             spaces and hyphens, or has fewer than 3 characters.
     */
    @Override
    public void validate(Utilizator user) {
        String nume = user.getNume();
        String prenume = user.getPrenume();
        List<String> e = new ArrayList<>();

        if (user.getId() == null || user.getId() <= 0){
            e.add("ID ul este invalid!");
        }

        if (nume == null || prenume == null || nume.isBlank() || prenume.isBlank()){
            e.add("Numele sau prenumele nu pot fi vide!");
        } else {
            if (!nume.matches("\\p{Lu}[\\p{L}\\s-]*") || nume.length() < 3){
                e.add("Structura numelui nu este valida!");
            }
            if (!prenume.matches("\\p{Lu}[\\p{L}\\s-]*") || prenume.length() < 3){
                e.add("Structura prenumelui nu este valida!");
            }
        }

        if ( !e.isEmpty() ){
            throw new ValidationException(e);
        }
    }
}
