/*
 * File: testEasyMode
 * Purpose: this runs test cases on the easy strategy
 */

package testCases;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.CPU;
import model.Dice;
import model.EasyMode;
import model.Mode;
import model.ScoreSheet.Category;

public class TestEasyMode {
	@Test
	public void testConstructor() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 6, 6, 6, 6, 6 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
	}

	@Test
	public void testChooseCategory() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 6, 6, 6, 6, 6 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
		assertTrue(easy.chooseCategory(diceSet) == Category.ONE);
	}

	@Test
	public void testUpperKindRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 6, 6, 6, 6, 6 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
		boolean[] rerolls = { false, false, false, false, false };
		rerolls = easy.upperKindRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { true, true, true, true, true }, rerolls);
		cpu.removeCategory(Category.ONE);
		rerolls = easy.upperKindRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { true, true, true, true, true }, rerolls);
		cpu.removeCategory(Category.TWO);
		rerolls = easy.upperKindRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { true, true, true, true, true }, rerolls);
		cpu.removeCategory(Category.THREE);
		rerolls = easy.upperKindRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { true, true, true, true, true }, rerolls);
		cpu.removeCategory(Category.FOUR);
		rerolls = easy.upperKindRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { true, true, true, true, true }, rerolls);
		cpu.removeCategory(Category.FIVE);
		diceSet = TestHelper.createResult(new int[] { 1, 1, 1, 1, 1 });
		rerolls = easy.upperKindRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { true, true, true, true, true }, rerolls);
	}

	@Test
	public void testKindRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 6, 6, 6, 6, 6 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
		boolean[] rerolls = { false, false, false, false, false };

		diceSet = TestHelper.createResult(new int[] { 1, 2, 3, 1, 4 });
		rerolls = easy.kindRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { false, true, true, false, true }, rerolls);
	}

	@Test
	public void testFullHouseRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 6, 6, 6, 6, 6 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
		boolean[] rerolls = { false, false, false, false, false };

		diceSet = TestHelper.createResult(new int[] { 1, 2, 3, 1, 4 });
		rerolls = easy.fullHouseRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { false, false, true, false, true }, rerolls);

		diceSet = TestHelper.createResult(new int[] { 1, 1, 1, 1, 1 });
		rerolls = new boolean[] { false, false, false, false, false };
		rerolls = easy.fullHouseRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { false, false, false, true, true }, rerolls);

		diceSet = TestHelper.createResult(new int[] { 1, 1, 2, 2, 1 });
		rerolls = new boolean[] { false, false, false, false, false };
		rerolls = easy.fullHouseRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { false, false, false, false, false }, rerolls);

		diceSet = TestHelper.createResult(new int[] { 1, 1, 1, 2, 1 });
		rerolls = new boolean[] { false, false, false, false, false };
		rerolls = easy.fullHouseRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { false, true, false, false, false }, rerolls);
	}

	@Test
	public void testStraightRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 5, 2, 4, 6, 3 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
		boolean[] rerolls = { false, false, false, false, false };

		rerolls = easy.straightRerolls(diceSet, rerolls);
		assertArrayEquals(new boolean[] { false, false, false, false, false }, rerolls);

		diceSet = TestHelper.createResult(new int[] { 1, 4, 3, 5, 4 });
		rerolls = easy.straightRerolls(diceSet, new boolean[] { false, false, false, false, false });
		assertArrayEquals(new boolean[] { true, true, false, false, false }, rerolls);
	}

	@Test
	public void testYahtzeeRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 6, 6, 6, 6, 6 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
		boolean[] rerolls = { false, false, false, false, false };

		rerolls = easy.yahtzeeRerolls(diceSet, new boolean[] { false, false, false, false, false });
		assertArrayEquals(new boolean[] { false, false, false, false, false }, rerolls);

		diceSet = TestHelper.createResult(new int[] { 1, 2, 3, 4, 5 });
		rerolls = easy.yahtzeeRerolls(diceSet, new boolean[] { false, false, false, false, false });
		assertArrayEquals(new boolean[] { true, true, true, true, false }, rerolls);

		diceSet = TestHelper.createResult(new int[] { 1, 3, 3, 4, 5 });
		rerolls = easy.yahtzeeRerolls(diceSet, new boolean[] { false, false, false, false, false });
		assertArrayEquals(new boolean[] { true, false, false, true, true }, rerolls);
	}

	@Test
	public void testChanceRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] { 6, 6, 6, 5, 6 });
		CPU cpu = new CPU(Mode.EASY, 0, diceSet);
		EasyMode easy = new EasyMode(cpu, diceSet);
		boolean[] rerolls = { false, false, false, false, false };
		rerolls = easy.chanceRerolls(diceSet, new boolean[] { false, false, false, false, false });
		assertArrayEquals(new boolean[] { false, false, false, true, false }, rerolls);
	}

}
