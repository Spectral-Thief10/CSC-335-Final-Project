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
		for(Category i : Category.values()) {
			categories.add(i);
		}
	}
	public int getID() {
		return id;
	}
	public int getScoreCategory(Category category) {
		return scoreSheet.getScoreCategory(category);
	}
	public ArrayList<Category> categoriesLeft() {
		ArrayList<Category> categories = new ArrayList<>();
		for(Category i : this.categories) {
			categories.add(i);
		}
		return categories;
	}
	/*
	 * @pre categories.getIndexOf(remove) != -1
	 */
	public boolean removeCategory(Category remove) {
		int index = getIndexOf(remove);
		if(index != -1) {
			categories.remove(index);
			return true;
		}
		return false;
	}
	private int getIndexOf(Category cat) {
		for(int i = 0; i < categories.size();i++) {
			if(categories.get(i)==cat) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isDone() {
		return scoreSheet.isComplete();
	}
	public void putScore(Category category, ArrayList<Dice> diceSet) {
		// TODO Auto-generated method stub
		if(getIndexOf(category)!=-1) { // checks to see if the category is yet to be completed.
			// sets the score for the category if it has not been completed
scoreSheet.setScoreCategory(category, diceSet);

if(categoriesLeft().contains(Category.YAHTZEE) && category == Category.YAHTZEE) {
removeCategory(category);
}
else {
removeCategory(category);}
}
		
	}
	
}
