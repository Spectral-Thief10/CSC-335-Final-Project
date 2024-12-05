import model.ScoreSheet.Category;

public interface Observer {
	int getId();
	void update(Category category, int val);
	void makeCurrentPlayer();
	void removeCurrentPlayer();

}
