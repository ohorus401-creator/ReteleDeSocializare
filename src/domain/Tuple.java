package domain;

import java.util.Objects;
/**
 * A pair of two values. It is used as the id of the friendships.
 *
 * @param <E1> the type of the first value
 * @param <E2> the type of the second value
 */
public class Tuple<E1, E2> {
    private final E1 e1;
    private final E2 e2;

    /**
     * Creates a pair.
     *
     * @param e1 the first value
     * @param e2 the second value
     */
    public Tuple(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    /**
     * Returns the first value.
     *
     * @return the first value
     */
    public E1 getE1() {
        return e1;
    }

    /**
     * Returns the second value.
     *
     * @return the second value
     */
    public E2 getE2() {
        return e2;
    }

    /**
     * Checks if this pair is equal to another object. Two pairs are equal if
     * they have the same class and equal values on both positions.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(e1, tuple.e1) && Objects.equals(e2, tuple.e2);
    }

    /**
     * Returns the hash code of the pair, computed from both values.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }

    public String toString() {
        return "(" + getE1() + "," + getE2() + ")" ;
    }
}
