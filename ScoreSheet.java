/**
*The ScoreSheet class is going to represent the score for the game. It calculates the total score for the game 
*after all the categories has been scored as well as add the upper bonus if the score of the upper section exceeds 63 
*/
import java.util.HashMap;
import java.util.Map;

public class ScoreSheet {

	private Map<Category, Integer> categoryScores;
	private boolean upperBonus;
	

	public enum Category {
		ONE, TWO, THREE, FOUR, FIVE, SIXE, THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT, LARGE_STRAIGHT,
		YAHTZEE, CHANCE
	}
	
	/**
	 * initialize all categories with the null score and false for the upperBonus since it 
	 * is not achieve yet. 
	 */
 
	public ScoreSheet() {

		categoryScores = new HashMap<>();
		Category[] category = Category.values();
		for (int i = 0; i < category.length; i++) {
			categoryScores.put(category[i], null);
		}
		upperBonus = false;
		
	}
	 /**
	 *for each of the category that have null score should be updated as well as
	 *check the state of the upper bonus.
     * 
     * @param category that we want to update its score.
     * @param score for that specific category.
     * @return true if the score was successfully updated, false if it not.
     */

	public boolean setScoreCategory(Category category, int score) {
		if (categoryScores.containsKey(category) && categoryScores.get(category) == null) {
			categoryScores.put(category, score);

			stateForUpperBonus();

			return true;
		}
		return false;
	}
	 /**
     * for specific category get the score.
     * 
     * @param category that we want its score..
     * @return score for that specific category.
     */
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

	/*
	 * checking if the upper section exceed 63 then it will be true and false otherwise. 
	 * */
	public void stateForUpperBonus() {
		if (!upperBonus && totalScoreForUpperSection() >= 63) {
			upperBonus = true;
		}
	}

	/*
	 * checking if all the category got updated score
	 * @return true if all score get updated false otherwise.
	 */
	
	public boolean isComplete() {
		for (Integer i : categoryScores.values()) {
			if (i == null) {
				return false;
			}
		}
		return true;
	}

	/*
	 * calculate the total score after checking that all score get updated
	 * and applied the bones if the upperBones is true.
	 * @return return the total score
	 * */
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
