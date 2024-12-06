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
        d.rollAll();
        d.rollAll();
        ArrayList<Dice> d1 = d.getResult();
        d.rollAll();
        d.rollDiceAt(new boolean[] {true, true, false, false, false});
        assertEquals(d1,d.getResult());
    }

    @Test
    public void testRerolls(){
        d.rollAll();
        ArrayList<Dice> d1 = d.getResult();
        d.rollDiceAt(new boolean[] {true, true, false, false, false});
        ArrayList<Dice> d2 = d.getResult();
        d.rollDiceAt(new boolean[] {false, false, false, true, false});
        ArrayList<Dice> d3 = d.getResult();
        
        assertThrows(AssertionError.class, () -> d.rollDiceAt(new boolean[] {false, false, false, true, false,true}));

        assertTrue(d1.get(2).equals(d2.get(2)));
        assertTrue(d1.get(3).equals(d2.get(3)));
        assertTrue(d1.get(4).equals(d2.get(4)));

        assertTrue(d2.get(0).equals(d3.get(0)));
        assertTrue(d2.get(1).equals(d3.get(1)));
        assertTrue(d2.get(2).equals(d3.get(2)));
        assertTrue(d2.get(4).equals(d3.get(4)));
        assertTrue(d1.size()==d2.size() && d1.size()==d3.size());
    }
    
    @Test
    public void testDice() {
    	assertThrows(AssertionError.class, () -> new Dice(0));
    	assertThrows(AssertionError.class, () -> new Dice(7));
    	
    	Dice dice1 = new Dice(3);
    	assertEquals(dice1.toString(),"3");
    	
    	Dice dice2 = new Dice(3);
    	assertTrue(dice1.compareTo(dice2)==0);

    	assertFalse(dice1.equals(null));
    	assertFalse(dice1.equals(d));
    	assertTrue(dice1.equals(dice2));
    	
    	Dice dice3 = new Dice(4);
    	assertFalse(dice3.equals(dice2));
    	
    }
    
    
}
