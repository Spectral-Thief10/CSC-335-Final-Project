import java.util.ArrayList;
import java.util.HashMap;

import model.Dice;
import model.DiceSet;
import model.Player;
import model.ScoreSheet; 
import model.ScoreSheet.Category;
import model.Mode;
import model.CPU;

public class GameManager {

	private ArrayList<Player> activePlayers;
	private Player currentPlayer;
	private ArrayList<Player> wonPlayers;
	private DiceSet diceSet;
	private HashMap<Integer, Observer> observers;

	public GameManager(int num) {
		/*
		 * Constructor for GameManager when its just players
		 * @param num (int): number of players
		 * @param gameMode(Mode): hard, easy
		 */

		activePlayers = new ArrayList<Player>();
		wonPlayers = new ArrayList<Player>();
		observers = new HashMap<>();
		diceSet = new DiceSet();
		resetDices();

		for (int i = 1; i <= num; i++) {
			activePlayers.add(new Player(i));
		}

		currentPlayer = activePlayers.get(0);

	}

	public GameManager(int num, Mode gameMode) {
		/*
		 * Constructor for GameManager when CPU wants to be added
		 * 
		 * @param num (int): number of players
		 * @param gameMode(Mode): hard, easy
		 */

		activePlayers = new ArrayList<Player>();
		wonPlayers = new ArrayList<Player>();
		diceSet = new DiceSet();
		observers = new HashMap<>();
		resetDices();

		for (int i = 1; i <= num; i++) {
			activePlayers.add(new Player(i));
		}
		
		activePlayers.add(new CPU(gameMode,num+1,diceSet.getResult()));
		currentPlayer = activePlayers.get(0);
	}

	public ArrayList<Category> getCurrentPlayerCategories() {

		//make sure this is being used in the GUI, otherwise delete it 
		return currentPlayer.categoriesLeft();

	}

	public boolean nextPlayer() {
		/*
		 * The game flow will move on to the next player and update the list of
		 * currentPlayers according
		 * 
		 * @return boolean: true we successfully moved to the next player, false if 
		 */

		Player prevPlayer = currentPlayer;
		
		if(!(activePlayers.size()==1)) {
			int indexOfCurrentPlayer = activePlayers.indexOf(currentPlayer);
			currentPlayer = activePlayers.get((indexOfCurrentPlayer + 1) % activePlayers.size());
		}		
		
		// checks if the current player is done
		if (prevPlayer.isDone()) {
			activePlayers.remove(prevPlayer);
			wonPlayers.add(prevPlayer);
		}
		
		if(activePlayers.size()==0) {
			currentPlayer=null;
			return false;
		}
		
		//if the next player is the CPU, decide the next set of moves for it
		if (isCPUTurn()) {

			CPU cpuPlayer = (CPU) currentPlayer;

			while (diceSet.canRoll()) {
				boolean[] rerolls = cpuPlayer.chooseScoreRerolls(diceSet.getResult());
				diceSet.rollDiceAt(rerolls);
				notifyAllObservers();

			}

			ScoreSheet.Category category = cpuPlayer.getCategory();
			if (category != null) {
				cpuPlayer.putScore(category, diceSet.getResult());
				notifyAllObservers();
			}

			resetDices();
			notifyAllObservers();

			if (cpuPlayer.isDone()) {
				activePlayers.remove(cpuPlayer);
				wonPlayers.add(cpuPlayer);
				notifyAllObservers();
			}

		}
		
		return true;
	}
	
	public boolean isCPUTurn() {
		return currentPlayer instanceof CPU;
	}

	public void notifyAllObservers() {
		for (Observer observer : observers.values()) {
			if (observer != null) {
				//observer.update();
			}
		}
	}

	
	public void registerObserver(int id, Observer observer) {
		observers.put(id, observer);
	}
	
	public void deregisterObserver(int id) {
		observers.remove(id);
	}
	
	public void notifyObserver(int id) {
        Observer observer = observers.get(id);
        if (observer != null) {
           // observer.update();
        }
    }
	
	public int getActivePlayers() {
		/*
		 * Returns the size of active players
		 * Used for testing
		 */
		return activePlayers.size();
	}
	
	public int getWonPlayers() {
		/*
		 * Returns the size of won players
		 * Used for testing
		 */
		return wonPlayers.size();
	}
	
	public int getPlayerIndex() {
		/*
		 * Returns the index of the current player
		 * Used for testing
		 */
		
		return activePlayers.indexOf(currentPlayer);
	}
	
	public boolean updateScore(Category category) {
		/*
		 * It updates the player score for that category
		 * 
		 * @param category is the category to update the score for
		 * 
		 * @return boolean: true if the scoresheet was successfully update, false if the
		 * category has already been completed
		 */

		ArrayList<Category> categories = currentPlayer.categoriesLeft();

		if (categories.contains(category)) {
			currentPlayer.putScore(category, getDiceSet());
			return true;
		}

		return false;
	}
	
	public int getCurrentScore(Category category) {
		/*
		 * Gets the score for a specified category of the current player's scoresheet
		 * 
		 * @param category is the category to get the score for
		 * 
		 * @return integer (score for the specified category)
		 */
		
		Integer retVal = currentPlayer.getScoreCategory(category);
		
		if (retVal == null) {
			return 0;
		} else {
			return retVal;
		}
	}
	
	public int getCurrentTotalScore() {
		 /* Gets the total score of the current players scoresheet
		 * 
		 * @return int total score
		 */
		 
		return currentPlayer.getTotalScore();
	}
	

	public ArrayList<Dice> getDiceSet() {
		/*
		 * @return  a copy of arraylist of diceset
		 */

		return diceSet.getResult();
	}

	public void resetDices() {
		/*
		 * This resets the diceSet
		 */

		diceSet.reset();
		diceSet.rollAll();
	}

	public void reRoll(boolean[] indexes) {
		/*
		 * rolls dices at indexes indicated. if the diceset is empty, functions rolls
		 * all dice no matter the index.
		 * 
		 * @pre indexes.length == 5;
		 */

		diceSet.rollDiceAt(indexes);

	}

	public boolean canRoll() {
		/*
		 * Checks if there are rerolls left
		 * 
		 * @return canRoll(boolean): true is rerolls left, else false
		 */

		//make sure this is being used in GUI or delete it
		return diceSet.canRoll();
	}

	public boolean isGameOver() {
		/*
		 * Checks if the game is over
		 * 
		 * @return true if the game is over, false otheriwse
		 */
		return activePlayers.size() == 0;
	}
}
