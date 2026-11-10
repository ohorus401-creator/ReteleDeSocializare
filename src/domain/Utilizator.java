package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A user of the network, with an id, a first name and a last name.
 */
public class Utilizator extends Entity<Long> {
    private Long id;
    private String prenume;
    private String nume;

    /**
     * Creates a user.
     *
     * @param id the id of the user
     * @param prenume the first name of the user
     * @param nume the last name of the user
     */
    public Utilizator(Long id, String prenume, String nume) {
        setId(id);
        this.nume = nume;
        this.prenume = prenume;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     * Sets the first name of the user.
     *
     * @param prenume the new first name
     */
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name
     */
    public String getNume() {
        return nume;
    }

    /**
     * Sets the last name of the user.
     *
     * @param nume the new first name
     */
    public void setNume(String nume) {
        this.nume = nume;
    }
}
