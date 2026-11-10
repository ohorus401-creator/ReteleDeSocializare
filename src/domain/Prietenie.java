package domain;

import java.time.LocalDateTime;

/**
 * A friendship between two users. Its id is the pair of the two user ids,
 * with the smaller id first, so the friendship (1, 2) is the same as (2, 1).
 */
public class Prietenie extends Entity<Tuple<Long,Long>>{
    private LocalDateTime data;

    /**
     * Creates a friendship between two users.
     *
     * @param idUser1 the id of the first user
     * @param idUser2 the id of the second user
     * @param data the date and time when the friendship was created
     */
    public Prietenie(Long idUser1, Long idUser2, LocalDateTime data){
        this.data = data;

        if ( idUser1 != null && idUser2 != null && idUser1 < idUser2 ){
            setId(new Tuple<>(idUser1, idUser2));
        } else{
            setId(new Tuple<>(idUser2, idUser1));
        }
    }

    /**
     * Returns the smaller of the two user ids.
     *
     * @return the id of the first user
     */
    public Long getIdUser1() {
        return getId().getE1();
    }

    /**
     * Returns the larger of the two user ids.
     *
     * @return the id of the second user
     */
    public Long getIdUser2() {
        return getId().getE2();
    }

    /**
     * Returns the date and time of the friendship.
     *
     * @return the date and time when the friendship was created
     */
    public LocalDateTime getData() {
        return data;
    }
}

