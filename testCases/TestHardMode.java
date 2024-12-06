package testCases;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.CPU;
import model.Dice;
import model.HardMode;
import model.Mode;
import model.ScoreSheet.Category;

public class TestHardMode {
	@Test
	public void testConstructor() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {6,6,6,6,6});
		CPU cpu = new CPU(Mode.HARD,0,diceSet);
		HardMode hard = new HardMode(cpu,diceSet);
	}
	
	@Test
	public void testUpperKindRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {3,2,3,6,6});
		CPU cpu = new CPU(Mode.HARD,0,diceSet);
		HardMode hard = new HardMode(cpu,diceSet);
		boolean[] rerolls = {false,false,false,false,false};
		rerolls = hard.upperKindRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {true,true,true,false,false},rerolls);
		cpu.removeCategory(Category.SIX);
		
		diceSet = TestHelper.createResult(new int[] {1,2,6,5,5});
		cpu.setDice(diceSet);
		rerolls = hard.upperKindRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {true,true,true,false,false},rerolls);
		cpu.removeCategory(Category.FIVE);
		
		diceSet = TestHelper.createResult(new int[] {1,2,6,4,4});
		cpu.setDice(diceSet);
		rerolls = hard.upperKindRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {true,true,true,false,false},rerolls);
		cpu.removeCategory(Category.FOUR);
		
		diceSet = TestHelper.createResult(new int[] {1,2,6,3,3});
		cpu.setDice(diceSet);
		rerolls = hard.upperKindRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {true,true,true,false,false},rerolls);
		cpu.removeCategory(Category.THREE);
		
		diceSet = TestHelper.createResult(new int[] {1,2,6,2,5});
		cpu.setDice(diceSet);
		rerolls = hard.upperKindRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {true,false,true,false,true},rerolls);
		cpu.removeCategory(Category.TWO);
		
		diceSet = TestHelper.createResult(new int[] {1,2,6,1,5});
		cpu.setDice(diceSet);
		rerolls = hard.upperKindRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {false,true,true,false,true},rerolls);
		cpu.removeCategory(Category.ONE);
	}
	
	@Test public void testKindRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {3,3,3,1,6});
		CPU cpu = new CPU(Mode.HARD,0,diceSet);
		HardMode hard = new HardMode(cpu,diceSet);
		boolean[] rerolls = {false,false,false,false,false};
		rerolls = hard.kindRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {false,false,false,true,true},rerolls);
	}
	
	@Test
	public void testFullHouseRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {3,3,3,6,6});
		CPU cpu = new CPU(Mode.HARD,0,diceSet);
		HardMode hard = new HardMode(cpu,diceSet);
		boolean[] rerolls = {false,false,false,false,false};
		rerolls = hard.fullHouseRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {false,false,false,false,false},rerolls);
		
		diceSet = TestHelper.createResult(new int[] {3,3,3,6,3});
		cpu = new CPU(Mode.HARD,0,diceSet);
		hard = new HardMode(cpu,diceSet);
		rerolls = new boolean[]{false,false,false,false,false};
		rerolls = hard.fullHouseRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {true,false,false,false,false},rerolls);
	}
	
	@Test
	public void testYahtzeeRerolls() {
			ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {3,3,3,3,4});
			CPU cpu = new CPU(Mode.HARD,0,diceSet);
			HardMode hard = new HardMode(cpu,diceSet);
			boolean[] rerolls = {false,false,false,false,false};
			rerolls = hard.yahtzeeRerolls(diceSet, new boolean[] {false,false,false,false,false});
			assertArrayEquals(new boolean[] {false,false,false,false,true},rerolls);
			
			
			diceSet = TestHelper.createResult(new int[] {1,2,3,4,5});
			cpu = new CPU(Mode.HARD,0,diceSet);
			hard = new HardMode(cpu,diceSet);
			rerolls = hard.yahtzeeRerolls(diceSet, new boolean[] {false,false,false,false,false});
			assertArrayEquals(new boolean[] {true,true,true,true,false},rerolls);
	}
	
	@Test
	public void testChanceRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {6,6,6,5,6});
		CPU cpu = new CPU(Mode.HARD,0,diceSet);
		HardMode hard = new HardMode(cpu,diceSet);
		boolean[] rerolls = {false,false,false,false,false};
		rerolls = hard.chanceRerolls(diceSet, new boolean[] {false,false,false,false,false});
		assertArrayEquals(new boolean[] {false,false,false,true,false},rerolls);
	}

	@Test
	public void testChooseCategory() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {6,6,6,6,6});
		CPU cpu = new CPU(Mode.HARD,0,diceSet);
		HardMode hard = new HardMode(cpu,diceSet);
		assertTrue(hard.chooseCategory(diceSet)==Category.YAHTZEE);
		cpu.removeCategory(Category.ONE);
		cpu.removeCategory(Category.TWO);
		cpu.removeCategory(Category.THREE);
		cpu.removeCategory(Category.FOUR);
		cpu.removeCategory(Category.FIVE);
		cpu.removeCategory(Category.SIX);
		cpu.removeCategory(Category.FOUR_OF_A_KIND);
		cpu.removeCategory(Category.THREE_OF_A_KIND);
		cpu.removeCategory(Category.LARGE_STRAIGHT);
		cpu.removeCategory(Category.SMALL_STRAIGHT);
		cpu.removeCategory(Category.FULL_HOUSE);
		diceSet = TestHelper.createResult(new int[] {1,4,5,2,1});
		assertTrue(hard.chooseCategory(diceSet)==Category.CHANCE);
		
		cpu.removeCategory(Category.YAHTZEE);
		assertTrue(hard.chooseCategory(diceSet)==Category.CHANCE);
		
		cpu = new CPU(Mode.HARD,0,diceSet);
		hard = new HardMode(cpu,diceSet);
		cpu.removeCategory(Category.ONE);
		cpu.removeCategory(Category.TWO);
		cpu.removeCategory(Category.THREE);
		cpu.removeCategory(Category.FOUR);
		cpu.removeCategory(Category.FIVE);
		cpu.removeCategory(Category.SIX);
		cpu.removeCategory(Category.FOUR_OF_A_KIND);
		cpu.removeCategory(Category.THREE_OF_A_KIND);
		cpu.removeCategory(Category.LARGE_STRAIGHT);
		cpu.removeCategory(Category.SMALL_STRAIGHT);
		cpu.removeCategory(Category.FULL_HOUSE);
		cpu.removeCategory(Category.CHANCE);
		assertTrue(hard.chooseCategory(diceSet)==Category.YAHTZEE);
		
		diceSet = TestHelper.createResult(new int[] {1,4,5,3,2});
		cpu = new CPU(Mode.HARD,0,diceSet);
		hard = new HardMode(cpu,diceSet);
		assertTrue(hard.chooseCategory(diceSet)==Category.LARGE_STRAIGHT);
		cpu.removeCategory(Category.LARGE_STRAIGHT);
		assertTrue(hard.chooseCategory(diceSet)==Category.SMALL_STRAIGHT);
		
		diceSet = TestHelper.createResult(new int[] {4,4,5,4,4});
		assertTrue(hard.chooseCategory(diceSet)==Category.FOUR_OF_A_KIND);
		
		diceSet = TestHelper.createResult(new int[] {5,4,5,4,4});
		assertTrue(hard.chooseCategory(diceSet)==Category.FULL_HOUSE);
		
		diceSet = TestHelper.createResult(new int[] {4,1,5,4,4});
		assertTrue(hard.chooseCategory(diceSet)==Category.THREE_OF_A_KIND);
		
		diceSet = TestHelper.createResult(new int[] {1,2,5,6,5});
		assertTrue(hard.chooseCategory(diceSet)==Category.FIVE);
		
		cpu.removeCategory(Category.ONE);
		diceSet = TestHelper.createResult(new int[] {1,2,1,6,5});
		assertTrue(hard.chooseCategory(diceSet)==Category.CHANCE);
		
		cpu.removeCategory(Category.CHANCE);
		diceSet = TestHelper.createResult(new int[] {1,2,1,6,5});
		assertTrue(hard.chooseCategory(diceSet)==Category.TWO);
		
		for(Category category : cpu.categoriesLeft()) {
			cpu.removeCategory(category);
		}
		assertTrue(hard.chooseCategory(diceSet)==Category.YAHTZEE);
	}

}
