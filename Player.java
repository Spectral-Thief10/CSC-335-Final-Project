import java.util.ArrayList;

public class Player {
	private ArrayList<Category> categories;
	private boolean finished;
	public Player() {
		categories = new ArrayList<>();
		finished = false;
		for(Category i :Category.values()) {
			categories.add(i);
		}
	}
	public int categoriesLeft() {
		return categories.size();
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
	public int getIndexOf(Category cat) {
		for(int i = 0; i < categories.size();i++) {
			if(categories.get(i)==cat) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isDone() {
		if(categories.size()==0) {
			finished = true;
		}
		else {
			finished = false;
		}
		return finished;
	}
	
}
