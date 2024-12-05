/**
*The ScoreSheet class is going to represent the score for the game. It calculates the total score for the game 
*after all the categories has been scored as well as add the upper bonus if the score of the upper section exceeds 63 
*/
package model;

import java.util.HashMap;
import java.util.ArrayList;


public class ScoreSheet {

	private HashMap<Category, Integer> categoryScores;

	public enum Category {
		ONE, TWO, THREE, FOUR, FIVE, SIX, THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT, LARGE_STRAIGHT,
		YAHTZEE, CHANCE
	}

	/**
	 * initialize all categories with the null score
	 */

	public ScoreSheet() {
		categoryScores = new HashMap<Category, Integer>();
		for (Category category : Category.values()){
			categoryScores.put(category, null);
		}
	}

	/**
	 * for each of the category that have null score should be updated as well as
	 * check the state of the upper bonus.
	 * 
	 * @param category that we want to update its score.
	 * @param score    for that specific category.
	 * @return true if the score was successfully updated, false if it not (duplicate).
	 */

	public boolean setScoreCategory(Category category, ArrayList<Dice> dice) {
		if (categoryScores.get(category) == null) {
			int score = 0;

			// switch gore for calcualting score
			switch(category){
				case ONE:
					score = Calculator.upperCalculator(dice, 1);
					break;
				case TWO:
					score = Calculator.upperCalculator(dice, 2);
					break;
				case THREE:
					score = Calculator.upperCalculator(dice, 3);
					break;
				case FOUR:
					score = Calculator.upperCalculator(dice, 4);
					break;
				case FIVE:
					score = Calculator.upperCalculator(dice, 5);
					break;
				case SIX:
					score = Calculator.upperCalculator(dice, 6);
					break;
				case THREE_OF_A_KIND:
					score = Calculator.threeOfAKindCalculator(dice);
					break;
				case FOUR_OF_A_KIND:
					score = Calculator.fourOfAKindCalculator(dice);
					break;
				case FULL_HOUSE:
					score = Calculator.fullHouseCalculator(dice);
					break;
				case SMALL_STRAIGHT:
					score = Calculator.smallStraightCalculator(dice);
					break;
				case LARGE_STRAIGHT:
					score = Calculator.largeStraightCalculator(dice);
					break;
				case YAHTZEE:
					score = Calculator.yahtzeeCalculator(dice);
					break;
				case CHANCE:
					score = Calculator.chanceCalculator(dice);
					break;
			}
			categoryScores.put(category, score);

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

	/*
	 * checking if all the category got updated score
	 * 
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
	 * Check if the player has the upper bonus
	 * 
	 * @return boolean if the player has enough score in the upper section to get the bonus
	 * 
	 */
	public boolean hasBonus() {
		if (totalScoreForUpperSection() >= 63) {
			return true;
		}

		return false;
	}

	private int totalScoreForUpperSection() {
		/*
		 * Helper functions for hasBonus(), calculates score of upper section
		 * 
		 * @return total of upper section categories.
		 */

		int total = 0;
		Category[] category = Category.values();
		for (int i = 0; i < category.length; i++) {
			if (category[i].ordinal() <= Category.SIX.ordinal() && categoryScores.get(category[i]) != null) {
				total += categoryScores.get(category[i]);
			}
		}

		return total;
	}

	/*
	 * calculate the total score after checking that all score get updated
	 * and adds bonus if the player reached 63 in the upper section.
	 * 
	 * @return return the total score
	 */
	public int getCurrentScore() {
		int total = 0;

		for (Integer i : categoryScores.values()) {
			if (i != null) {
				total += i;
			}
		}

		if (hasBonus()) {
			total += 35;
		}

		return total;
	}

}