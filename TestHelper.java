import java.util.ArrayList;
import model.Dice;

public class TestHelper {
    public static ArrayList<Dice> createResult(int[] dice) {
		/*
		 * Creates a test ArrayList<Dice> based on an int[] input for testing
         * 
         * @pre dice.length == 5
		 * 
         * @post shit works properly :D
		 */

        assert dice.length == 5;
		ArrayList<Dice> out = new ArrayList<Dice>();

		for (int d : dice) {
			out.add(new Dice(d));
		}

		return out;
	}
}
