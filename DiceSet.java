/*
 * Representation of a Yahtzee set of 5 dice, which can be rolled and rerolled 3 times.
 * 
 * 
 */

import java.util.ArrayList;
import java.util.Collections;

public class DiceSet {
    private int rolls;
    private ArrayList<Dice> dice;

    public DiceSet(){
        reset();
    }

    public boolean canRoll(){
        /*
         * @return boolean whether or not the dice set can still be rolled
         */
        return rolls > 0;
    }

    public void rollAll(){
        /*
         * rolls all dices at every index.
         * 
         */

        if (canRoll()) {
            for (int i = 0; i < 5; i++){
                this.dice.add(new Dice());
            }

            rolls -= 1;
        }
    }

    public void rollDiceAt(boolean[] indexes){
        /*
         * rolls dices at indexes indicated.
         * 
         * @pre indexes.length == 5;
         * 
         * @post able to reroll any number of the 5 dice
         */
        assert indexes.length == 5;

        if (canRoll()) {
            for (int i = 0; i < indexes.length; i++){
                if (indexes[i]) {
                    this.dice.add(i, new Dice());
                }
            }
    
            rolls -= 1;
        }
    }

    public int sumDice(){
        /*
         * @return the sum of all the dice in the dice set
         */

        // dice set has not yet been rolled
        if (this.rolls == 3) {
            return 0;
        }

        int output = 0;

        for (Dice d : this.dice) {
            output += d.VALUE;
        }

        return output;
    }

    public ArrayList<Dice> getResult(){
        /*
         * @return sorted ArrayList containing the current roll result. empty if not yet rolled.
         */

        ArrayList<Dice> copy = new ArrayList<Dice>(this.dice);
        Collections.sort(copy);

        return copy;
    }

    public void reset(){
        /*
         * empties the dice set and allows for 3 more rolls.
         */

        this.dice = new ArrayList<Dice>(5);
        this.rolls = 3;
    }
}
