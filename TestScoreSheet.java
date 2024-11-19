import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.ScoreSheet;

class TestScoreSheet {

	@Test
	public void testSetScore() {
		ScoreSheet scoreSheet = new ScoreSheet();

		assertTrue(scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 3));

		assertEquals(Integer.valueOf(3), scoreSheet.getScoreCategory(ScoreSheet.Category.ONE));

		assertFalse(scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 4));

		assertEquals(Integer.valueOf(3), scoreSheet.getScoreCategory(ScoreSheet.Category.ONE));

	}

	@Test
	public void testtotalScoreForUpperSection() {
		ScoreSheet scoreSheet = new ScoreSheet();

		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 2);
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, 6);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, 6);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, 16);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, 10);

		assertEquals(40, scoreSheet.totalScoreForUpperSection());

	}

	@Test
	public void testStateForUpperBonusWhenItsTrue() {
		ScoreSheet scoreSheet = new ScoreSheet();

		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 5);
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, 8);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, 9);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, 20);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, 20);
		scoreSheet.setScoreCategory(ScoreSheet.Category.SIXE, 24);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE_OF_A_KIND, 25);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR_OF_A_KIND, 30);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FULL_HOUSE, 25);
		scoreSheet.setScoreCategory(ScoreSheet.Category.SMALL_STRAIGHT, 30);
		scoreSheet.setScoreCategory(ScoreSheet.Category.LARGE_STRAIGHT, 40);
		scoreSheet.setScoreCategory(ScoreSheet.Category.YAHTZEE, 50);
		scoreSheet.setScoreCategory(ScoreSheet.Category.CHANCE, 22);

		assertTrue(scoreSheet.totalScoreForUpperSection() >= 63);
		assertTrue(scoreSheet.totalScore() > 0);

	}

	@Test
	public void testStateForUpperBonusWhenItsFalse() {
		ScoreSheet scoreSheet = new ScoreSheet();

		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 2);
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, 6);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, 6);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, 16);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, 10);

		assertFalse(scoreSheet.totalScoreForUpperSection() >= 63);
		assertFalse(scoreSheet.totalScore() > 0);

	}

	@Test
	public void testTotalScoreIncomplete() {
		ScoreSheet scoreSheet = new ScoreSheet();

		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 2);
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, 6);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, 6);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, 16);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, 10);

		assertEquals(0, scoreSheet.totalScore());

	}

	@Test
	public void testTotalScoreCompleteWithBonus() {
		ScoreSheet scoreSheet = new ScoreSheet();

		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 5);
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, 8);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, 9);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, 20);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, 20);
		scoreSheet.setScoreCategory(ScoreSheet.Category.SIXE, 24);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE_OF_A_KIND, 25);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR_OF_A_KIND, 30);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FULL_HOUSE, 25);
		scoreSheet.setScoreCategory(ScoreSheet.Category.SMALL_STRAIGHT, 30);
		scoreSheet.setScoreCategory(ScoreSheet.Category.LARGE_STRAIGHT, 40);
		scoreSheet.setScoreCategory(ScoreSheet.Category.YAHTZEE, 50);
		scoreSheet.setScoreCategory(ScoreSheet.Category.CHANCE, 22);

		assertEquals(343, scoreSheet.totalScore());

	}

	@Test
	public void testTotalScoreCompleteWithOutBonus() {
		ScoreSheet scoreSheet = new ScoreSheet();

		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, 5);
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, 8);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, 9);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, 4);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, 5);
		scoreSheet.setScoreCategory(ScoreSheet.Category.SIXE, 6);
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE_OF_A_KIND, 25);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR_OF_A_KIND, 30);
		scoreSheet.setScoreCategory(ScoreSheet.Category.FULL_HOUSE, 25);
		scoreSheet.setScoreCategory(ScoreSheet.Category.SMALL_STRAIGHT, 30);
		scoreSheet.setScoreCategory(ScoreSheet.Category.LARGE_STRAIGHT, 40);
		scoreSheet.setScoreCategory(ScoreSheet.Category.YAHTZEE, 50);
		scoreSheet.setScoreCategory(ScoreSheet.Category.CHANCE, 22);

		assertEquals(259, scoreSheet.totalScore());

	}

}
