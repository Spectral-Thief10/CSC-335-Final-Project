/*
 * Representation of a d6 dice.
 * 
 */
package model;
import java.util.Random;

public final class Dice implements Comparable<Dice> {
	private static Random rnjesus = new Random(
            System.currentTimeMillis() * System.currentTimeMillis() - (System.currentTimeMillis() % 10000));
    public final int VALUE;

    public Dice() {
        /*
         * Randomly sets the value of dice from 1-6. This cannot be changed once set.
         * Access using .VALUE
         */

        this.VALUE = (rnjesus.nextInt(1, 7));
    }

    public Dice(int val) {
        /*
         * Sets a dice with the given value. Primarily for testing.
         * 
         * @parameter: val, the value to set the dice
         * 
         * @pre val > 0 && val <= 6
         * 
         * @post accurate score calculation
         */

        assert val > 0 && val <= 6;
        this.VALUE = val;
    }

    public int compareTo(Dice other) {
        return Integer.compare(this.VALUE, other.VALUE);
    }

    @Override
    public boolean equals(Object obj) {
        // make sure we can actually compare these
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        // cast obj as a book and compare
        final Dice other = (Dice) obj;

        return this.VALUE == other.VALUE;
    }

    @Override
    public String toString() {
        return Integer.toString(this.VALUE);
    }
}