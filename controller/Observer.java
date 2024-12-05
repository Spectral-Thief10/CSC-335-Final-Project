package controller;
import model.ScoreSheet.Category;

public interface Observer {
	int getId();
	void update(Category category, String val);

}
