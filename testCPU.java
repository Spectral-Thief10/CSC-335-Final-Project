
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import model.CPU;
import model.Dice;
import model.DiceSet;
import model.Mode;
import model.ScoreSheet;
import model.ScoreSheet.Category;


public class testCPU {
	DiceSet dice;
	CPU cpu;
	@Test
	public void testConstructor() {
		dice = new DiceSet();
		dice.rollAll();
		cpu = new CPU(Mode.HARD,0,dice.getResult());
		cpu = new CPU(Mode.EASY,0,dice.getResult());
	}
	@Test
	public void testGetCategory() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[]{5,5,5,5,5});
		cpu = new CPU(Mode.EASY,0,diceSet);
		Category category = cpu.getCategory();
		assertTrue(category == Category.ONE);
	}
	@Test
	public void testSetDice() {
		ArrayList<Dice> diceSet = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			diceSet.add(new Dice(6));
		}
		cpu = new CPU(Mode.HARD,0,diceSet);
		cpu.setDice(diceSet);
		assertTrue(cpu.getCategory()==Category.YAHTZEE);
		
	}
	@Test
	public void testChooseScoreRerolls() {
		ArrayList<Dice> diceSet = TestHelper.createResult(new int[] {6,6,6,6,6});
		cpu = new CPU(Mode.EASY,0,diceSet);
		
		assertArrayEquals(new boolean[] {true,true,true,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.ONE);
		assertArrayEquals(new boolean[] {true,true,true,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.TWO);
		assertArrayEquals(new boolean[] {true,true,true,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.THREE);
		assertArrayEquals(new boolean[] {true,true,true,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.FOUR);
		assertArrayEquals(new boolean[] {true,true,true,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.FIVE);
		diceSet = TestHelper.createResult(new int[] {1,1,1,1,1});
		assertArrayEquals(new boolean[] {true,true,true,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.SIX);
		diceSet = TestHelper.createResult(new int[] {2,4,4,1,3});
		assertArrayEquals(new boolean[] {true,false,false,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.THREE_OF_A_KIND);
		assertArrayEquals(new boolean[] {true,false,false,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.FOUR_OF_A_KIND);
		diceSet = TestHelper.createResult(new int[] {4,4,4,3,3});
		assertArrayEquals(new boolean[] {false,false,false,false,false},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.FULL_HOUSE);
		diceSet = TestHelper.createResult(new int[] {5,2,4,1,3});
		assertArrayEquals(new boolean[] {false,false,false,false,false},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.SMALL_STRAIGHT);
		assertArrayEquals(new boolean[] {false,false,false,false,false},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.LARGE_STRAIGHT);
		assertArrayEquals(new boolean[] {false,true,true,true,true},cpu.chooseScoreRerolls(diceSet));
		cpu.removeCategory(Category.YAHTZEE);
		diceSet = TestHelper.createResult(new int[] {5,6,4,6,3});
		assertArrayEquals(new boolean[] {true,false,true,false,true},cpu.chooseScoreRerolls(diceSet));
	}


	public void a() {
		dice = new DiceSet();
		dice.rollAll();
		ArrayList<Dice> diceSet = dice.getResult();
		cpu = new CPU(Mode.HARD,0,diceSet);
		boolean[]rerolls = cpu.chooseScoreRerolls(diceSet);
		for(int i = 0;  i< 13;i++) {
			while(dice.canRoll()) {
				diceSet = dice.getResult();
				System.out.println(diceSet);
				rerolls = cpu.chooseScoreRerolls(diceSet);
				System.out.println(cpu.getCategory());
				for(int e = 0; e < 5; e++) {
					System.out.print(rerolls[e] + " | ");
				}
				System.out.println();
				
				dice.rollDiceAt(rerolls);
			}
			diceSet = dice.getResult();
			cpu.chooseScoreRerolls(diceSet);
			Category c = cpu.getCategory();
			cpu.putScore(c, diceSet);
			System.out.println(c);
			System.out.println(diceSet);
			System.out.println();
			dice.reset();
			dice.rollAll();
			diceSet = dice.getResult();
		}
		
		
	}
}
