/*
 * File: TestHelper.java
 * Purpose: creates an ArrayList of dice to be used for testing, and
 * runs all the test cases
 */
package testCases;

import java.util.ArrayList;
import model.Dice;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ TestCalculator.class, TestCPU.class, TestDiceSet.class, TestEasyMode.class, TestGameManager.class,
		TestHardMode.class, TestPlayer.class, TestScoreSheet.class })
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
