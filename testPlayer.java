import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
public class testPlayer {
	@Test
	public void testRemoveEmptyCategory() {
		Player p1 = new Player();
		p1.removeCategory(Category.ONES);
		assertEquals(false,p1.removeCategory(Category.ONES));
	}
	@Test
	public void testRemoveNotEmptyCategory() {
		Player p1 = new Player();
		assertTrue(p1.categoriesLeft()==13);
		for(Category i: Category.values()) {
			p1.removeCategory(i);
		}
		assertTrue(p1.categoriesLeft()==0);
	}
	@Test
	public void testIsDoneFinished() {
		Player p1 = new Player();
		for(Category i : Category.values()) {
			p1.removeCategory(i);
		}
		assertTrue(p1.isDone());
	}
	@Test
	public void testIsDoneNotFinished() {
		Player p1 = new Player();
		assertFalse(p1.isDone());
	}
	
}
