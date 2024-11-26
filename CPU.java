
import model.ScoreSheet.Category;

public class CPU extends Player{
	private Mode mode;
	private Strategy strategy;
	public CPU(Mode mode) {
		this.mode = mode;
		if(this.mode.ordinal() == Mode.EASY.ordinal()) {
			strategy = new EasyMode(this);
		}
		if(this.mode.ordinal()==Mode.HARD.ordinal()){
			strategy = new HardMode(this);
		}
	}
	public boolean[] chooseUpperScoreRerolls(DiceSet dice) {
		boolean[] rerolls = {false,false,false,false,false};
		for(int i = 0; i < dice.getResult().size();i++) {
			if(dice.getResult().get(i).VALUE != getCategory().ordinal()+1) {
				rerolls[i]=true;
			}
		}
		return rerolls;
	}
	public boolean[] chooseLowerScoreRerolls(DiceSet dice) {
		boolean[] rerolls = {false, false, false,false,false};
		if(getCategory()==Category.THREE_OF_A_KIND) {
			rerolls = strategy.kindRerolls(dice, rerolls);
		}
		else if(getCategory()==Category.FOUR_OF_A_KIND) {
			rerolls = strategy.kindRerolls(dice,rerolls);
			
		}else if(getCategory()==Category.FULL_HOUSE) {
			rerolls = strategy.fullHouseRerolls(dice,rerolls);
			
		}
		else if(getCategory()==Category.SMALL_STRAIGHT){
			rerolls = strategy.straightRerolls(dice,rerolls);
		}
		else if(getCategory()==Category.LARGE_STRAIGHT) {
			rerolls = strategy.straightRerolls(dice, rerolls);
		}
		else if(getCategory()==Category.YAHTZEE) {
			rerolls = strategy.yahtzeeRerolls(dice,rerolls);
		}
		else {
			rerolls = strategy.chanceRerolls(dice,rerolls);
		}
		return rerolls;
		
	}
	public Category getCategory() {
		return strategy.chooseCategory();
	}
}
