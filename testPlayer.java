import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.ScoreSheet.Category;
import model.Dice;
import model.DiceSet;
import model.Player;
public class testPlayer {
	Player p1 = new Player(1);
	@Test
	public void testPutScore() {
		ArrayList<Dice> diceSet = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			diceSet.add(new Dice(1));
		}
		p1.putScore(Category.ONE,diceSet);
		assertTrue(p1.getScoreCategory(Category.ONE) == 5);
		p1.putScore(Category.ONE, diceSet);
	}
	@Test
	public void testGetId() {
		assertTrue(p1.getID()==1);
	}
	@Test
	public void testIsNotDone() {
		assertFalse(p1.isDone());
		DiceSet diceSet = new DiceSet();
		for(Category c : p1.categoriesLeft()) {
			p1.putScore(c,diceSet.getResult());
		}
		assertTrue(p1.isDone());
	}
	@Test
	public void testRemoveCategory() {
		p1.removeCategory(Category.ONE);
		assertFalse(p1.categoriesLeft().contains(Category.ONE));
		p1.removeCategory(Category.ONE);
	}

}
