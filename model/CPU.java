package model;


import java.util.ArrayList;

import model.ScoreSheet.Category;

public class CPU extends Player{
	private Mode mode;
	private ArrayList<Dice> diceSet = new ArrayList<>();
	private Strategy strategy;
	/*
	 * @pre dice.size() == 5
	 */
	public CPU(Mode mode,int id,ArrayList<Dice> dice) {
		super(id);
		setDice(dice);
		this.mode = mode;
		if(this.mode == Mode.EASY) {
			strategy = new EasyMode(this,diceSet);
		}
		if(this.mode==Mode.HARD){
			strategy = new HardMode(this,diceSet);
		}
	}
	/*
	 * @pre diceSet.size() == 5
	 */
	public void setDice(ArrayList<Dice> diceSet) {
		this.diceSet = new ArrayList<>();
		for(int i = 0; i < diceSet.size();i++) {
			Dice dice = new Dice(diceSet.get(i).VALUE);
			this.diceSet.add(dice);
		}
	}
	/*
	 * @pre dice.size() == 5
	 */
	public boolean[] chooseScoreRerolls(ArrayList<Dice> dice) {
		boolean[] rerolls = {false, false, false,false,false};
		setDice(dice);
		if(getCategory()==Category.ONE || getCategory()==Category.TWO || getCategory()==Category.THREE ||getCategory()==Category.FOUR || getCategory()==Category.FIVE || getCategory()==Category.SIX) {
			rerolls = strategy.upperKindRerolls(diceSet,rerolls);
		}
		else if(getCategory()==Category.THREE_OF_A_KIND) {
			rerolls = strategy.kindRerolls(diceSet, rerolls);
		}
		else if(getCategory()==Category.FOUR_OF_A_KIND) {
			rerolls = strategy.kindRerolls(diceSet,rerolls);
			
		}else if(getCategory()==Category.FULL_HOUSE) {
			rerolls = strategy.fullHouseRerolls(diceSet,rerolls);
			
		}
		else if(getCategory()==Category.SMALL_STRAIGHT){
			rerolls = strategy.straightRerolls(diceSet,rerolls);
		}
		else if(getCategory()==Category.LARGE_STRAIGHT) {
			rerolls = strategy.straightRerolls(diceSet, rerolls);
		}
		else if(getCategory()==Category.YAHTZEE) {
			rerolls = strategy.yahtzeeRerolls(diceSet,rerolls);
		}
		else {
			rerolls = strategy.chanceRerolls(diceSet,rerolls);
		}
		return rerolls;
		
	}
	public Category getCategory() {
		return strategy.chooseCategory(diceSet);
	}
}
