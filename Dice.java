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
        this.VALUE = (rnjesus.nextInt()%6)+1;
    }

    public int compareTo(Dice other){
        return Integer.compare(this.VALUE, other.VALUE);
    }
}
