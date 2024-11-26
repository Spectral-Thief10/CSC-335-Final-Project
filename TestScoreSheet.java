import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.ScoreSheet;

class TestScoreSheet {
	ScoreSheet scoreSheet = new ScoreSheet();

	@Test
	public void testSetScore() {
		assertTrue(scoreSheet.setScoreCategory(ScoreSheet.Category.ONE,
				TestHelper.createResult(new int[] { 1, 3, 1, 3, 1 })));

		assertEquals(3, scoreSheet.getScoreCategory(ScoreSheet.Category.ONE));

		assertFalse(scoreSheet.setScoreCategory(ScoreSheet.Category.ONE,
				TestHelper.createResult(new int[] { 2, 3, 4, 1, 1 })));

		assertEquals(3, scoreSheet.getScoreCategory(ScoreSheet.Category.ONE));
	}

	@Test
	public void testtotalScoreForUpperSection() {
		assertEquals(0, scoreSheet.getCurrentScore());
		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, TestHelper.createResult(new int[] { 1, 2, 1, 3, 3 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, TestHelper.createResult(new int[] { 1, 1, 2, 2, 2 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, TestHelper.createResult(new int[] { 3, 2, 1, 5, 3 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, TestHelper.createResult(new int[] { 4, 4, 4, 4, 5 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, TestHelper.createResult(new int[] { 5, 3, 4, 2, 5 }));

		assertEquals(40, scoreSheet.getCurrentScore());

	}

	@Test
	public void testUpperBonus() {
		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, TestHelper.createResult(new int[] { 1, 1, 1, 1, 1 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, TestHelper.createResult(new int[] { 2, 2, 2, 2, 2 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, TestHelper.createResult(new int[] { 3, 3, 3, 3, 3 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, TestHelper.createResult(new int[] { 4, 4, 4, 4, 4 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, TestHelper.createResult(new int[] { 5, 5, 5, 5, 5 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.FULL_HOUSE, TestHelper.createResult(new int[] {3, 3, 3, 1, 1}));
		assertTrue(scoreSheet.hasBonus());
		assertTrue(scoreSheet.getCurrentScore() > 0);
	}

	@Test
	public void testStateForUpperBonusWhenItsFalse() {
		scoreSheet.setScoreCategory(ScoreSheet.Category.ONE, TestHelper.createResult(new int[] { 1, 2, 1, 3, 3 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.TWO, TestHelper.createResult(new int[] { 1, 1, 2, 2, 2 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.THREE, TestHelper.createResult(new int[] { 3, 2, 1, 5, 3 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR, TestHelper.createResult(new int[] { 4, 4, 4, 4, 5 }));
		scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE, TestHelper.createResult(new int[] { 5, 3, 4, 2, 5 }));

		assertFalse(scoreSheet.hasBonus());
		assertTrue(scoreSheet.getCurrentScore() > 0);

	}
}
