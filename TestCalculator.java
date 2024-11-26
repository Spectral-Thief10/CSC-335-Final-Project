import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Calculator;
import model.Dice;

import java.util.ArrayList;

public class TestCalculator {
	@Test
	public void testUpperSectionCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 1, 3, 5, 4, 3 });

		assertEquals(1, Calculator.upperCalculator(dice, 1));
		assertEquals(6, Calculator.upperCalculator(dice, 3));
		assertEquals(4, Calculator.upperCalculator(dice, 4));
		assertEquals(5, Calculator.upperCalculator(dice, 5));

	}

	@Test
	public void testThreeOfAKindCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 5, 5, 5, 3, 2 });
		assertEquals(20, Calculator.threeOfAKindCalculator(dice));

		ArrayList<Dice> dice2 = TestHelper.createResult(new int[] { 4, 4, 4, 4, 6 });
		assertEquals(22, Calculator.threeOfAKindCalculator(dice2));

		ArrayList<Dice> dice3 = TestHelper.createResult(new int[] { 1, 2, 3, 4, 5 });
		assertEquals(0, Calculator.threeOfAKindCalculator(dice3));
	}

	@Test
	public void testFourOfAKindCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 4, 2, 4, 4, 6 });
		assertEquals(0, Calculator.fourOfAKindCalculator(dice));

		ArrayList<Dice> dice2 = TestHelper.createResult(new int[] { 4, 4, 4, 4, 6 });
		assertEquals(22, Calculator.fourOfAKindCalculator(dice2));

		ArrayList<Dice> dice3 = TestHelper.createResult(new int[] { 4, 4, 4, 4, 4 });
		assertEquals(20, Calculator.fourOfAKindCalculator(dice3));
	}

	@Test
	public void testFullHouseCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 4, 2, 4, 4, 2 });
		assertEquals(25, Calculator.fullHouseCalculator(dice));

		ArrayList<Dice> dice2 = TestHelper.createResult(new int[] { 4, 4, 4, 1, 6 });
		assertEquals(0, Calculator.fullHouseCalculator(dice2));

		ArrayList<Dice> dice3 = TestHelper.createResult(new int[] { 2, 1, 3, 2, 1 });
		assertEquals(0, Calculator.fullHouseCalculator(dice3));
	}

	@Test
	public void testSmallStraightCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 4, 2, 3, 1, 5 });
		assertEquals(30, Calculator.smallStraightCalculator(dice));

		ArrayList<Dice> dice2 = TestHelper.createResult(new int[] { 4, 2, 3, 1, 6 });
		assertEquals(30, Calculator.smallStraightCalculator(dice2));

		ArrayList<Dice> dice3 = TestHelper.createResult(new int[] { 2, 4, 3, 5, 4 });
		assertEquals(30, Calculator.smallStraightCalculator(dice3));

		ArrayList<Dice> dice4 = TestHelper.createResult(new int[] { 4, 6, 3, 1, 5 });
		assertEquals(30, Calculator.smallStraightCalculator(dice4));

		ArrayList<Dice> dice5 = TestHelper.createResult(new int[] { 3, 2, 3, 1, 6 });
		assertEquals(0, Calculator.smallStraightCalculator(dice5));

		ArrayList<Dice> dice6 = TestHelper.createResult(new int[] { 2, 4, 3, 5, 6 });
		assertEquals(30, Calculator.smallStraightCalculator(dice6));

		ArrayList<Dice> dice7 = TestHelper.createResult(new int[] { 3, 4, 3, 1, 6 });
		assertEquals(0, Calculator.smallStraightCalculator(dice7));

		ArrayList<Dice> dice8 = TestHelper.createResult(new int[] { 3, 5, 3, 2, 6 });
		assertEquals(0, Calculator.smallStraightCalculator(dice8));

	}

	@Test
	public void testLargeStraightCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 4, 2, 3, 1, 5 });
		assertEquals(40, Calculator.largeStraightCalculator(dice));

		ArrayList<Dice> dice2 = TestHelper.createResult(new int[] { 4, 2, 3, 5, 6 });
		assertEquals(40, Calculator.largeStraightCalculator(dice2));

		ArrayList<Dice> dice3 = TestHelper.createResult(new int[] { 3, 2, 3, 1, 6 });
		assertEquals(0, Calculator.largeStraightCalculator(dice3));

	}

	@Test
	public void testYahtzeeCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 4, 4, 4, 4, 4 });
		assertEquals(50, Calculator.yahtzeeCalculator(dice));

		ArrayList<Dice> dice2 = TestHelper.createResult(new int[] { 4, 4, 4, 4, 5 });
		assertEquals(0, Calculator.yahtzeeCalculator(dice2));

	}

	@Test
	public void testChanceCalculator() {
		ArrayList<Dice> dice = TestHelper.createResult(new int[] { 4, 4, 4, 4, 4 });
		assertEquals(20, Calculator.chanceCalculator(dice));

		ArrayList<Dice> dice2 = TestHelper.createResult(new int[] { 4, 2, 3, 5, 6 });
		assertEquals(20, Calculator.chanceCalculator(dice2));

	}

}
