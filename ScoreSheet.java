import java.util.HashMap;
import java.util.Map;

public class ScoreSheet {

	private Map<Category, Integer> categoryScores;
	private boolean upperBonus;
	private int bonus;

	public enum Category {
		ONE, TWO, THREE, FOUR, FIVE, SIXE, THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT, LARGE_STRAIGHT,
		YAHTZEE, CHANCE
	}
 
	public ScoreSheet() {

		categoryScores = new HashMap<>();
		Category[] category = Category.values();
		for (int i = 0; i < category.length; i++) {
			categoryScores.put(category[i], null);
		}
		upperBonus = false;
		this.bonus = 63;
	}


	public boolean setScoreCategory(Category category, int score) {
		if (categoryScores.containsKey(category) && categoryScores.get(category) == null) {
			categoryScores.put(category, score);

			stateForUpperBonus();

			return true;
		}
		return false;
	}

	public Integer getScoreCategory(Category category) {
		return categoryScores.get(category);
	}

	
	public int totalScoreForUpperSection() {
		int total = 0;
		Category[] category = Category.values();
		for (int i = 0; i < category.length; i++) {
			if (category[i].ordinal() <= Category.SIXE.ordinal() && categoryScores.get(category[i]) != null) {
				total += categoryScores.get(category[i]);
			}
		}
		return total;
	}

	
	public void stateForUpperBonus() {
		if (!upperBonus && totalScoreForUpperSection() >= bonus) {
			upperBonus = true;
		}
	}

	
	public boolean isComplete() {
		for (Integer i : categoryScores.values()) {
			if (i == null) {
				return false;
			}
		}
		return true;
	}

	
	public int totalScore() {

		if (!isComplete()) {
			return 0;
		}

		int total = 0;

		for (Integer i : categoryScores.values()) {
			if (i != null) {
				total += i;
			}
		}
		if (upperBonus) {
			total += 35;
		}
		return total;
	}

}
