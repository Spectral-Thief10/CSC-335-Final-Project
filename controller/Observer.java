/*
 *  Observer Interafce for the Scoresheet and DiceSet GUI
*/

package controller;
import java.util.ArrayList;

import model.Dice;
import model.ScoreSheet.Category;

public interface Observer {
	int getId();
	void update(Category category, int val);
	void update(ArrayList<Dice> result);
	void makeCurrentPlayer();
	void removeCurrentPlayer();

}
