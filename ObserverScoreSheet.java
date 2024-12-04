/*
 * 	Interface for Scoresheet Observer
 */

import model.ScoreSheet.Category;

public interface ObserverScoreSheet {	
	    void update(GameManager control, Category category, String val);
}
