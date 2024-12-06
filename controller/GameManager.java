/*
 *  Main controller that manages the yahtzee game 
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Dice;
import model.DiceSet;
import model.Player;
import model.ScoreSheet;
import model.ScoreSheet.Category;
import view.GameView;
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
		 * 
		 * @param num (int): number of players
		 */

		activePlayers = new ArrayList<Player>();
		wonPlayers = new ArrayList<Player>();
		observers = new HashMap<>();
		diceSet = new DiceSet();
		resetDices();

		for (int i = 1; i <= num; i++) {
			activePlayers.add(new Player(i));
		}

		// setting the first active player to be the current player
		currentPlayer = activePlayers.get(0);

	}

	public GameManager(int num, Mode gameMode) {
		/*
		 * Constructor for GameManager when CPU wants to be added
		 * 
		 * @param num (int): number of players
		 * 
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

		activePlayers.add(new CPU(gameMode, num + 1, diceSet.getResult()));
		currentPlayer = activePlayers.get(0);
	}

	public ArrayList<Category> getCurrentPlayerCategories() {
		return currentPlayer.categoriesLeft();

	}

	public void startsGame() {
		/*
		 * It sets the initial current Player
		 */
		observers.get(currentPlayer.getID()).makeCurrentPlayer();

	}

	public int getWinner() {
		/*
		 * This function returns the ID number of the player who won
		 * 
		 * @return the winner id
		 */

		if (wonPlayers.size() == 0) {
			return -1;
		}
		Player winner = wonPlayers.get(0);

		// loops over the players done with the game and finds the one with the highest
		// total score
		for (Player p : wonPlayers) {
			if (p.getTotalScore() > winner.getTotalScore()) {
				winner = p;
			}
		}

		return winner.getID();
	}

	public boolean nextPlayer() {
		/*
		 * The game flow will move on to the next player and update the list of
		 * currentPlayers according
		 * 
		 * @return boolean: true we successfully moved to the next player, false if
		 */

		// checks if the current player is done
		if (currentPlayer.isDone()) {
			activePlayers.remove(currentPlayer);
			wonPlayers.add(currentPlayer);
		}

		// game is over
		if (activePlayers.size() == 0) {
			return false;
		}
		
		// get new current player
		int indexOfCurrentPlayer = activePlayers.indexOf(currentPlayer);
		currentPlayer = activePlayers.get((indexOfCurrentPlayer + 1) % activePlayers.size());
	
		// if the next player is the CPU, decide the next set of moves for it
		if (isCPUTurn()) {			
			
			CPU cpuPlayer = (CPU) currentPlayer;
			changeCurrentPlayer(cpuPlayer.getID());

			// while there are rolls left
			resetDices();
			boolean[] rerolls = cpuPlayer.chooseScoreRerolls(diceSet.getResult());
			while (diceSet.canRoll()) {
				// let the cpu decide the next reroll
				rerolls = cpuPlayer.chooseScoreRerolls(diceSet.getResult());
				diceSet.rollDiceAt(rerolls);
				if (observers.get(-1) != null) observers.get(-1).update(getDiceSet());			

			}
			cpuPlayer.chooseScoreRerolls(diceSet.getResult());
			
			// the cpu decides what category to choose and updates it
			boolean flag = false;
			ScoreSheet.Category category = cpuPlayer.getCategory();
			if (category != null) {
				flag = updateScore(category);
			}

			// if update was successful
			if (flag) {
				boolean playerFlag = nextPlayer();
				// if next player was successful
				if (!playerFlag) {
					changeCurrentPlayer(-100);
					if (observers.get(-1) != null) GameView.endScreen(this);
					return false;
				}
			}
		} 

		// update the GUI using observers
		resetDices();
		if (observers.get(-1) != null) observers.get(-1).update(getDiceSet());
		changeCurrentPlayer(currentPlayer.getID());

		return true;
	}

	public boolean isCPUTurn() {
		/*
		 * Checks if current player is an instance of CPU
		 * 
		 * @return true if the currentPlayer is an instance of CPU otherwise return
		 * false
		 */
		return currentPlayer instanceof CPU;
	}

	public void registerObserver(int id, Observer observer) {
		/*
		 * Adds register
		 * 
		 * @param id: the key for thr hashmap
		 * 
		 * @param observer: the observer object to be added
		 */

		observers.put(id, observer);
	}

	public void deregisterObserver(int id) {
		/*
		 * Adds register
		 * 
		 * @param id: the key for the hashmap
		 */

		observers.remove(id);
	}

	public void changeCurrentPlayer(int id) {
		/*
		 * It reflects the current player changes in the GUI using observers
		 * 
		 * @param id: player id which acts like a key for the hasmap
		 * 
		 */

		for (Integer key : observers.keySet()) {
			if (key == id) {
				System.out.println(key);
				observers.get(key).makeCurrentPlayer();
			} else {
				observers.get(key).removeCurrentPlayer();
			}
		}
	}

	public boolean playerIsDone(int id) {
		/*
		 * Checks if the player is done
		 * 
		 * @param id: id value of the player
		 * 
		 * @return true if the player is done, else return false
		 */
		if (wonPlayers.size() <= 0) {
			return false;
		}

		for (Player p : wonPlayers) {
			if (p.getID() == id) {
				return true;
			}
		}

		return false;
	}

	public int getActivePlayers() {
		/*
		 * Returns the size of active players Used for testing
		 */
		return activePlayers.size();
	}

	public int getWonPlayers() {
		/*
		 * Returns the size of won players Used for testing
		 */
		return wonPlayers.size();
	}

	public int getPlayerIndex() {
		/*
		 * Returns the index of the current player
		 */

		return currentPlayer.getID();
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
			if (observers.get(currentPlayer.getID()) != null) observers.get(currentPlayer.getID()).update(category, currentPlayer.getScoreCategory(category));

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

	public boolean getCurrentBonus() {
		return currentPlayer.hasBonus();
	}

	public int getCurrentTotalScore() {
		/*
		 * Gets the total score of the current players scoresheet
		 * 
		 * @return int total score
		 */

		return currentPlayer.getTotalScore();
	}

	public ArrayList<Dice> getDiceSet() {
		/*
		 * @return a copy of arraylist of diceset
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

		// make sure this is being used in GUI or delete it
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

	public void updateDices(boolean[] rerolls) {
		/*
		 * Checks if the game is over
		 * 
		 * @return true if the game is over, false otheriwse
		 */
		diceSet.rollDiceAt(rerolls);
		observers.get(-1).update(getDiceSet());

	}
}
