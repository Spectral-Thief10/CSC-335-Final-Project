/*
 * File: CPU.java
 * Purpose: This file creates the CPU for the yahtzee game
 * It has a hard mode and an easy mode
 */
package model;

import java.util.ArrayList;

import model.ScoreSheet.Category;

public class CPU extends Player {
	private Mode mode;
	private ArrayList<Dice> diceSet = new ArrayList<>();
	private Strategy strategy;

	/*
	 * @pre dice.size() == 5
	 */
	public CPU(Mode mode, int id, ArrayList<Dice> dice) {
		super(id);
		setDice(dice);
		this.mode = mode;
		// sets the strategy to hard or easy
		if (this.mode == Mode.EASY) {
			strategy = new EasyMode(this, diceSet);
		}
		if (this.mode == Mode.HARD) {
			strategy = new HardMode(this, diceSet);
		}
	}

	/*
	 * Sets all the dice in the diceSet to the parameter's dice returns void
	 * 
	 * @pre diceSet.size() == 5
	 */
	public void setDice(ArrayList<Dice> diceSet) {
		this.diceSet = new ArrayList<>();
		for (int i = 0; i < diceSet.size(); i++) {
			Dice dice = new Dice(diceSet.get(i).VALUE);
			this.diceSet.add(dice);
		}
	}

	/*
	 * Chooses the dice to re-roll based off its category returns boolean[]
	 * 
	 * @pre dice.size() == 5
	 */
	public boolean[] chooseScoreRerolls(ArrayList<Dice> dice) {
		boolean[] rerolls = { false, false, false, false, false };
		setDice(dice);
		// chooses the rerolls based off the upper section scores
		if (getCategory() == Category.ONE || getCategory() == Category.TWO || getCategory() == Category.THREE
				|| getCategory() == Category.FOUR || getCategory() == Category.FIVE || getCategory() == Category.SIX) {
			rerolls = strategy.upperKindRerolls(diceSet, rerolls);
		}
		// chooses the rerolls based on the three of a kind category
		else if (getCategory() == Category.THREE_OF_A_KIND) {
			rerolls = strategy.kindRerolls(diceSet, rerolls);
		}
		// chooses the rerolls based on the four of a kind category
		else if (getCategory() == Category.FOUR_OF_A_KIND) {
			rerolls = strategy.kindRerolls(diceSet, rerolls);

		}
		// chooses the rerolls based on the full house category
		else if (getCategory() == Category.FULL_HOUSE) {
			rerolls = strategy.fullHouseRerolls(diceSet, rerolls);

		}
		// chooses the rerolls based on the small straight category
		else if (getCategory() == Category.SMALL_STRAIGHT) {
			rerolls = strategy.straightRerolls(diceSet, rerolls);
		}
		// chooses the rerolls based on the large straight category
		else if (getCategory() == Category.LARGE_STRAIGHT) {
			rerolls = strategy.straightRerolls(diceSet, rerolls);
		}
		// chooses the rerolls based on the yahtzee category
		else if (getCategory() == Category.YAHTZEE) {
			rerolls = strategy.yahtzeeRerolls(diceSet, rerolls);
		}
		// chooses the rerolls based on the chance category
		else {
			rerolls = strategy.chanceRerolls(diceSet, rerolls);
		}
		return rerolls;

	}

	/*
	 * chooses a category based on the strategy for the cpu to go for next returns
	 * Category
	 */
	public Category getCategory() {
		return strategy.chooseCategory(diceSet);
	}
}
