package testCases;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.GameManager;
import model.Calculator;
import model.Dice;
import model.Mode;
import model.ScoreSheet;
import model.ScoreSheet.Category;

public class TestGameManager {
	
	private GameManager gmWithoutCPU = new GameManager(1);
	private GameManager gmWithCpuEasy = new GameManager(2, Mode.EASY);
	private GameManager gmWithCpuHard = new GameManager(1, Mode.HARD);
	
	@Test
	public void testPlayerLogic() {	
		
		//checking wrap around
		assertEquals(gmWithoutCPU.getPlayerIndex(),1);
		assertTrue(gmWithoutCPU.nextPlayer());
		assertEquals(gmWithoutCPU.getPlayerIndex(),1);
		
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),2);
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),1);
		
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),2);
		
		
		
		for (Category category : Category.values()) {
			assertTrue(gmWithCpuEasy.updateScore(category));
		}	
		
		assertFalse(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.YAHTZEE));
				
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),1);
		assertEquals(gmWithCpuEasy.getActivePlayers(),2);
		assertEquals(gmWithCpuEasy.getWonPlayers(),1);
		
		for (Category category : Category.values()) {
			assertTrue(gmWithCpuEasy.updateScore(category));
		}
		
		assertEquals(gmWithCpuEasy.getPlayerIndex(),1);
		assertFalse(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getActivePlayers(),0);
		assertEquals(gmWithCpuEasy.getWonPlayers(),3);
		
		assertTrue(gmWithCpuEasy.isGameOver());
		assertFalse(gmWithoutCPU.isGameOver());

	}
	
	@Test
	public void testGetCategories() {
		
		ArrayList<Category> categories = new ArrayList<Category>();
		
		for (Category category : Category.values()) {
			categories.add(category);
		}		
		assertEquals(categories,gmWithCpuHard.getCurrentPlayerCategories());
		
	}

	@Test
	public void testDiceMethods() {
		
		boolean[] shouldReroll = {true,false,true,false,true};
		
		assertTrue(gmWithoutCPU.canRoll());
		gmWithoutCPU.reRoll(shouldReroll);
		assertTrue(gmWithoutCPU.canRoll());
		gmWithoutCPU.reRoll(shouldReroll);
		assertFalse(gmWithoutCPU.canRoll());

		gmWithoutCPU.reRoll(shouldReroll);
		assertFalse(gmWithoutCPU.canRoll());
		
		ArrayList<Dice> prevDices = gmWithoutCPU.getDiceSet();
		
		gmWithoutCPU.resetDices();
		gmWithoutCPU.reRoll(shouldReroll);
		ArrayList<Dice> currDices = gmWithoutCPU.getDiceSet();
		int count = 0;
		
		for (int i = 0; i < 5; i++) {
            Dice prevDice = prevDices.get(i);
            Dice currDice = currDices.get(i);

            if(prevDice.equals(currDice)) {
            	count++;
            }
        }		
		assertTrue(count!=5);
		
		gmWithoutCPU.updateDices(shouldReroll);
		
		

	}
	
	@Test
	public void testGetWinner() {
		
		assertFalse(gmWithoutCPU.getCurrentBonus());
		assertTrue(gmWithCpuHard.getWinner()==-1);
		assertFalse(gmWithCpuHard.playerIsDone(1));
		for (Category category : Category.values()) {
			gmWithCpuHard.updateScore(category);
		}
		
		int score1 = gmWithCpuHard.getCurrentTotalScore();	
		System.out.println(score1);

		
		gmWithCpuHard.nextPlayer();
		gmWithCpuHard.resetDices();
		
		for (Category category : Category.values()) {
			gmWithCpuHard.updateScore(category);
		}
		
		int score2 = gmWithCpuHard.getCurrentTotalScore();
		System.out.println(score2);
		int winner = -1;
		if(score1>score2) {
			winner=1;
		}
		else {
			winner=2;
		}
		gmWithCpuHard.nextPlayer();
		assertTrue(gmWithCpuHard.getWinner()==winner);
		assertTrue(gmWithCpuHard.playerIsDone(1));
		assertFalse(gmWithCpuHard.playerIsDone(15));
		
	}
	
	@Test
	public void testGetCurrentScore() {
		assertTrue(gmWithoutCPU.getCurrentScore(ScoreSheet.Category.FOUR_OF_A_KIND)==0);
		gmWithoutCPU.updateScore(ScoreSheet.Category.FOUR_OF_A_KIND);
		assertTrue(gmWithoutCPU.getCurrentScore(ScoreSheet.Category.FOUR_OF_A_KIND)==Calculator.fourOfAKindCalculator(gmWithoutCPU.getDiceSet()));

	}
	
}
