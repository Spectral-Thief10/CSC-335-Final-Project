import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

public class testDiceSet {
    DiceSet d = new DiceSet();
    
    @Test
    public void testRollAll(){
        assertTrue(d.getResult().size() == 0);
        d.rollAll();
        assertTrue(d.getResult().size() == 5);
    }
}
