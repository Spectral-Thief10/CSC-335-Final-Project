import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Calculator;

class TestCalculator {

	private final Calculator calculator = new Calculator();

	@Test
	public void testUpperSectionCalculator() {
		int[] dice = { 3, 1, 3, 4, 5 };
		assertEquals(1, calculator.upperSectionCalculator(dice, 1));
		assertEquals(6, calculator.upperSectionCalculator(dice, 3));
		assertEquals(4, calculator.upperSectionCalculator(dice, 4));
		assertEquals(5, calculator.upperSectionCalculator(dice, 5));
	}

	@Test
	public void testThreeOfAKindCalculator() {
		int[] dice = { 4, 2, 4, 4, 6 };
		assertEquals(20, calculator.threeOfAKindCalculator(dice));

		int[] dice2 = { 4, 4, 4, 4, 6 };
		assertEquals(22, calculator.threeOfAKindCalculator(dice2));
		
		int[] dice3 = { 4, 1, 1, 4, 6 };
		assertEquals(0, calculator.threeOfAKindCalculator(dice3));
	}
	
	@Test
	public void testFourOfAKindCalculator() {
		int[] dice = { 4, 2, 4, 4, 6 };
		assertEquals(0, calculator.fourOfAKindCalculator(dice));

		int[] dice2 = { 4, 4, 4, 4, 6 };
		assertEquals(22, calculator.fourOfAKindCalculator(dice2));
		
		int[] dice3 = { 4, 4, 4, 4, 4 };
		assertEquals(20, calculator.fourOfAKindCalculator(dice3));
	}
	
	@Test
	public void testFullHouseCalculator() {
		int[] dice = { 4, 2, 4, 4, 2 };
		assertEquals(25, calculator.fullHouseCalculator(dice));

		int[] dice2 = { 4, 4, 4, 1, 6 };
		assertEquals(0, calculator.fullHouseCalculator(dice2));
		
		int[] dice3 = { 2, 1, 3, 2, 1 };
		assertEquals(0, calculator.fullHouseCalculator(dice3));
	}
	
	@Test
	public void testSmallStraightCalculator() {
		int[] dice = { 4, 2, 3, 1, 5 };
		assertEquals(30, calculator.smallStraightCalculator(dice));

		int[] dice2 = { 4, 2, 3, 1, 6 };
		assertEquals(30, calculator.smallStraightCalculator(dice2));
		
		int[] dice3 = { 2, 4, 3, 5, 4 };
		assertEquals(30, calculator.smallStraightCalculator(dice3));
		
		int[] dice4 = { 4, 6, 3, 1, 5 };
		assertEquals(30, calculator.smallStraightCalculator(dice4));

		int[] dice5 = { 3, 2, 3, 1, 6 };
		assertEquals(0, calculator.smallStraightCalculator(dice5));
		
		int[] dice6 = { 2, 4, 3, 5, 6 };
		assertEquals(30, calculator.smallStraightCalculator(dice6));
		
		int[] dice7 = { 3, 4, 3, 1, 6 };
		assertEquals(0, calculator.smallStraightCalculator(dice7));
		
		int[] dice8 = { 3, 5, 3, 2, 6 };
		assertEquals(0, calculator.smallStraightCalculator(dice8));
		
	}
	
	@Test
	public void testLargeStraightCalculator() {
		int[] dice = { 4, 2, 3, 1, 5 };
		assertEquals(40, calculator.largeStraightCalculator(dice));

		int[] dice2 = { 4, 2, 3, 5, 6 };
		assertEquals(40, calculator.largeStraightCalculator(dice2));

		int[] dice5 = { 3, 2, 3, 1, 6 };
		assertEquals(0, calculator.largeStraightCalculator(dice5));
		
	}
	
	@Test
	public void testYahtzeeCalculator() {
		int[] dice = { 4, 4, 4, 4, 4 };
		assertEquals(50, calculator.yahtzeeCalculator(dice));

		int[] dice2 = { 4, 2, 3, 5, 6 };
		assertEquals(0, calculator.yahtzeeCalculator(dice2));
		
	}
	
	@Test
	public void testChanceCalculator() {
		int[] dice = { 4, 4, 4, 4, 4 };
		assertEquals(20, calculator.chanceCalculator(dice));

		int[] dice2 = { 4, 2, 3, 5, 6 };
		assertEquals(20, calculator.chanceCalculator(dice2));
		
	}
	
}
