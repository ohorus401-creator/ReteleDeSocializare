package validation;

import domain.Prietenie;
import exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator for Prietenie entities.
 */
public class PrietenieValidator implements Validator<Prietenie>{

    /**
     * Verify whether a friendship is valid.
     * @param entity the friendship that must be verified
     * @throws ValidationException if either id is null, if either id is not positive or equal
     */
    @Override
    public void validate(Prietenie entity) {
        Long id1 = entity.getIdUser1();
        Long id2 = entity.getIdUser2();
        List<String> e = new ArrayList<>();

        if (id1 == null || id2 == null){
            e.add("Id urile nu pot fi nule!");
        } else{
            if (id1 <= 0 || id2 <= 0){
                e.add("Id urile trebuie sa fie pozitive!");
            }
            if (id1.equals(id2)){
                e.add("Un User nu poate fi prieten cu el insusi!");
            }
        }

        if (!e.isEmpty()){
            throw new ValidationException(e);
        }
    }
}
