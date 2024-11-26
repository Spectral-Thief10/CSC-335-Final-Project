import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import model.Category;

public class testGameManager {
	
	private GameManager gmWithoutCPU = new GameManager(1);
	private GameManager gmWithCpuEasy = new GameManager(2, Mode.EASY);
	private GameManager gmWithCpuHard = new GameManager(1, Mode.HARD);
	
	
	@Test
	public void testNextPlayer() {		
		
		//checking wrap around
		assertEquals(gmWithoutCPU.getPlayerIndex(),0);
		gmWithoutCPU.nextPlayer();
		assertEquals(gmWithoutCPU.getPlayerIndex(),0);
		
		gmWithCpuEasy.nextPlayer();
		assertEquals(gmWithCpuEasy.getPlayerIndex(),1);
		gmWithCpuEasy.nextPlayer();
		assertEquals(gmWithCpuEasy.getPlayerIndex(),2);
		
		gmWithCpuEasy.nextPlayer();
		assertEquals(gmWithCpuEasy.getPlayerIndex(),0);
		
		//checks if isDone is running correctly
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
		
		gmWithCpuEasy.nextPlayer();
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
		
		gmWithCpuEasy.nextPlayer();
		assertEquals(gmWithCpuEasy.getPlayerIndex(),0);
		assertEquals(gmWithCpuEasy.getActivePlayers(),1);
		assertEquals(gmWithCpuEasy.getWonPlayers(),2);

	}

}
