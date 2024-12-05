package testCases;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controller.GameManager;
import model.Dice;
import model.Mode;
import model.ScoreSheet.Category;

public class testGameManager {
	
	private GameManager gmWithoutCPU = new GameManager(1);
	private GameManager gmWithCpuEasy = new GameManager(2, Mode.EASY);
	private GameManager gmWithCpuHard = new GameManager(1, Mode.HARD);
	
	@Test
	public void testPlayerLogic() {		
		
		//checking wrap around
		assertEquals(gmWithoutCPU.getPlayerIndex(),0);
		assertTrue(gmWithoutCPU.nextPlayer());
		assertEquals(gmWithoutCPU.getPlayerIndex(),0);
		
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),1);
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),2);
		
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),0);
		
		//checks if isDone is running correctly
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.CHANCE));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FIVE));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.ONE));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.TWO));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.THREE));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FOUR));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.SIX));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.THREE_OF_A_KIND));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FOUR_OF_A_KIND));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FULL_HOUSE));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.SMALL_STRAIGHT));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.LARGE_STRAIGHT));
		assertTrue(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.YAHTZEE));
		
		assertFalse(gmWithCpuEasy.updateScore(model.ScoreSheet.Category.YAHTZEE));
				
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),0);
		assertEquals(gmWithCpuEasy.getActivePlayers(),2);
		assertEquals(gmWithCpuEasy.getWonPlayers(),1);
		
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.CHANCE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FIVE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.ONE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.TWO);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.THREE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FOUR);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.SIX);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.THREE_OF_A_KIND);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FOUR_OF_A_KIND);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FULL_HOUSE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.SMALL_STRAIGHT);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.LARGE_STRAIGHT);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.YAHTZEE);
		
		assertTrue(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getPlayerIndex(),0);
		assertEquals(gmWithCpuEasy.getActivePlayers(),1);
		assertEquals(gmWithCpuEasy.getWonPlayers(),2);
		
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.CHANCE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FIVE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.ONE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.TWO);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.THREE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FOUR);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.SIX);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.THREE_OF_A_KIND);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FOUR_OF_A_KIND);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.FULL_HOUSE);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.SMALL_STRAIGHT);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.LARGE_STRAIGHT);
		gmWithCpuEasy.updateScore(model.ScoreSheet.Category.YAHTZEE);
		
		assertEquals(gmWithCpuEasy.getPlayerIndex(),0);
		assertFalse(gmWithCpuEasy.nextPlayer());
		assertEquals(gmWithCpuEasy.getActivePlayers(),0);
		assertEquals(gmWithCpuEasy.getWonPlayers(),3);
		
		assertTrue(gmWithCpuEasy.isGameOver());
		assertFalse(gmWithoutCPU.isGameOver());

	}
	
	@Test
	public void testGetCategories() {
		
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.add(model.ScoreSheet.Category.ONE);
		categories.add(model.ScoreSheet.Category.TWO);
		categories.add(model.ScoreSheet.Category.THREE);
		categories.add(model.ScoreSheet.Category.FOUR);
		categories.add(model.ScoreSheet.Category.FIVE);
		categories.add(model.ScoreSheet.Category.SIX);
		categories.add(model.ScoreSheet.Category.THREE_OF_A_KIND);
		categories.add(model.ScoreSheet.Category.FOUR_OF_A_KIND);
		categories.add(model.ScoreSheet.Category.FULL_HOUSE);
		categories.add(model.ScoreSheet.Category.SMALL_STRAIGHT);
		categories.add(model.ScoreSheet.Category.LARGE_STRAIGHT);
		categories.add(model.ScoreSheet.Category.YAHTZEE);
		categories.add(model.ScoreSheet.Category.CHANCE);
		
		assertEquals(categories,gmWithCpuHard.getCurrentPlayerCategories());
		
	}

	@Test
	public void testDiceMethods() {
		
		boolean[] shouldReroll = {true,false,true,false,true};
		
		assertTrue(gmWithoutCPU.canRoll());
		gmWithoutCPU.reRoll(shouldReroll);
		assertTrue(gmWithoutCPU.canRoll());

		gmWithoutCPU.reRoll(shouldReroll);
		assertTrue(gmWithoutCPU.canRoll());

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

	}
	
}
