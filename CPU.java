

import model.Player;
import model.ScoreSheet.Category;

public class CPU extends Player{
	private Mode mode;
	private DiceSet dice;
	private Strategy strategy;
	public CPU(Mode mode,DiceSet dice) {
		super(0);
		this.dice = dice;
		this.mode = mode;
		if(this.mode == Mode.EASY) {
			strategy = new EasyMode(this,dice);
		}
		if(this.mode==Mode.HARD){
			strategy = new HardMode(this,dice);
		}
	}
	public boolean[] chooseScoreRerolls(DiceSet dice) {
		boolean[] rerolls = {false, false, false,false,false};
		this.dice = dice;
		if(getCategory()==Category.ONE || getCategory()==Category.TWO || getCategory()==Category.THREE
				||getCategory()==Category.FOUR || getCategory()==Category.FIVE || getCategory()==Category.SIX) {
			rerolls = strategy.upperKindRerolls(this.dice,rerolls);
		}
		else if(getCategory()==Category.THREE_OF_A_KIND) {
			rerolls = strategy.kindRerolls(this.dice, rerolls);
		}
		else if(getCategory()==Category.FOUR_OF_A_KIND) {
			rerolls = strategy.kindRerolls(this.dice,rerolls);
			
		}else if(getCategory()==Category.FULL_HOUSE) {
			rerolls = strategy.fullHouseRerolls(this.dice,rerolls);
			
		}
		else if(getCategory()==Category.SMALL_STRAIGHT){
			rerolls = strategy.straightRerolls(this.dice,rerolls);
		}
		else if(getCategory()==Category.LARGE_STRAIGHT) {
			rerolls = strategy.straightRerolls(this.dice, rerolls);
		}
		else if(getCategory()==Category.YAHTZEE) {
			rerolls = strategy.yahtzeeRerolls(this.dice,rerolls);
		}
		else {
			rerolls = strategy.chanceRerolls(this.dice,rerolls);
		}
		return rerolls;
		
	}
	public Category getCategory() {
		return strategy.chooseCategory();
	}
}
