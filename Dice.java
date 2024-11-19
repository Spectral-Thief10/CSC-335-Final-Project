/*
 * Representation of a d6 dice.
 * 
 */

import java.util.Random;

public final class Dice implements Comparable<Dice> {
    public final int VALUE;

    public Dice (){
        /*
         * Randomly sets the value of dice from 1-6. This cannot be changed once set.
         * Access using .VALUE
         */

        Random rnjesus = new Random(System.currentTimeMillis());
        this.VALUE = (rnjesus.nextInt(0, 7));
    }

    public int compareTo(Dice other){
        return Integer.compare(this.VALUE, other.VALUE);
    }

    @Override
    public boolean equals(Object obj){
        // make sure we can actually compare these
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        // cast obj as a book and compare
        final Dice other = (Dice)obj;

        return other.VALUE == this.VALUE;
    }
}
