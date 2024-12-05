/*
 * File: Player.java
 * Purpose: this file creates the player for the game of yahtzee
 */

package model;

import java.util.ArrayList;

import model.ScoreSheet.Category;

public class Player {
	private ArrayList<Category> categories;
	private ScoreSheet scoreSheet;
	private int id;

	public Player(int idVal) {
		id = idVal;
		scoreSheet = new ScoreSheet();
		categories = new ArrayList<>();
		for (Category i : Category.values()) {
			categories.add(i);
		}
	}

	/*
	 * returns an integer
	 */
	public int getID() {
		return id;
	}

	/*
	 * This gives the client access to remove a given category from the categories
	 * ArrayList from outside the class returns void
	 * 
	 * @pre category != null
	 */
	public void removeCategory(Category category) {
		if (categories.contains(category)) {
			categories.remove(category);
		}
	}

	/*
	 * This returns the score for a given category returns int
	 * 
	 * @pre category != null
	 */
	public int getScoreCategory(Category category) {
		return scoreSheet.getScoreCategory(category);
	}

	/*
	 * This gets all the categories that are left for the player returns
	 * ArrayList<Category>
	 */
	public ArrayList<Category> categoriesLeft() {
		ArrayList<Category> categories = new ArrayList<>();
		for (Category i : this.categories) {
			categories.add(i);
		}
		return categories;
	}

	/*
	 * This checks to see if the scoreSheet is complete returns boolean
	 */
	public boolean isDone() {
		return scoreSheet.isComplete();
	}

	/*
	 * This puts a diceSet to be scored into the category that was chosen returns
	 * void
	 * 
	 * @pre category != null && diceSet.size() == 5
	 */
	public void putScore(Category category, ArrayList<Dice> diceSet) {
		scoreSheet.setScoreCategory(category, diceSet);
		if (categories.contains(category)) {
			categories.remove(category);
		}

	}

}
