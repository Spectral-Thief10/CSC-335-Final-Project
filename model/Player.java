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

	public int getID() {
		return id;
	}
	/*
	 * @pre category != null
	 */
	public void removeCategory(Category category) {
		if(categories.contains(category)) {
			categories.remove(category);
		}
	}
	/*
	 * @pre category != null
	 */
	public Integer getScoreCategory(Category category) {
		return scoreSheet.getScoreCategory(category);
	}
	
	public int getTotalScore() {
		return scoreSheet.getCurrentScore();
	}


	public ArrayList<Category> categoriesLeft() {
		ArrayList<Category> categories = new ArrayList<>();
		for (Category i : this.categories) {
			categories.add(i);
		}
		return categories;
	}


	public boolean isDone() {
		return scoreSheet.isComplete();
	}

	/*
	 * @pre category != null && diceSet.size() == 5
	 */
	public void putScore(Category category, ArrayList<Dice> diceSet) {
		scoreSheet.setScoreCategory(category, diceSet);
		if(categories.contains(category)) {
			categories.remove(category);
		}

	}

}
