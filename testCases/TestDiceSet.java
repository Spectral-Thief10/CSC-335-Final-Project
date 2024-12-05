package testCases;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import model.Dice;
import model.DiceSet;

import java.util.ArrayList;

public class TestDiceSet {
    DiceSet d = new DiceSet();
    
    @Test
    public void testRollAll(){
        assertTrue(d.getResult().size() == 0);
        d.rollAll();
        assertTrue(d.getResult().size() == 5);
    }

    @Test
    public void testRerolls(){
        d.rollAll();
        ArrayList<Dice> d1 = d.getResult();
        d.rollDiceAt(new boolean[] {true, true, false, false, false});
        ArrayList<Dice> d2 = d.getResult();
        d.rollDiceAt(new boolean[] {false, false, false, true, false});
        ArrayList<Dice> d3 = d.getResult();

        assertTrue(d1.get(2).equals(d2.get(2)));
        assertTrue(d1.get(3).equals(d2.get(3)));
        assertTrue(d1.get(4).equals(d2.get(4)));

        assertTrue(d2.get(0).equals(d3.get(0)));
        assertTrue(d2.get(1).equals(d3.get(1)));
        assertTrue(d2.get(2).equals(d3.get(2)));
        assertTrue(d2.get(4).equals(d3.get(4)));
        assertTrue(d1.size()==d2.size() && d1.size()==d3.size());
    }
}
