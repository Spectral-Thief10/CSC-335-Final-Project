import java.util.ArrayList;

import model.Dice;
import model.ScoreSheet.Category;

public class HardMode implements Strategy{
	private CPU cpu;
	public HardMode(CPU cpu) {
		// TODO Auto-generated constructor stub
		this.cpu = cpu;
	}


	@Override
	public Category chooseCategory() {
		// TODO Auto-generated method stub
		
		return null;
	}


	@Override
	public boolean[] kindRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		int[] numbers = {0,0,0,0,0,0};
		ArrayList<Dice> diceSet = dice.getResult();
		for(int i = 0; i < rerolls.length;i++) {
			numbers[diceSet.get(i).VALUE-1] = numbers[diceSet.get(i).VALUE-1]+1;
		}
		int highest = 0;
		int index = 0;
		for(int i = 0; i < 6; i++) {
			if(numbers[i]>highest) {
				highest = numbers[i];
				index = i;
			}
		}
		for(int i = 0; i < rerolls.length;i++) {
			if(diceSet.get(i).VALUE != index+1) {
				rerolls[i]=true;
			}
		}
		return rerolls;
	}


	@Override
	public boolean[] fullHouseRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean[] straightRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean[] yahtzeeRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean[] chanceRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		ArrayList<Dice> diceSet = dice.getResult();
		for(int i = 0; i < diceSet.size();i++) {
			if(diceSet.get(i).VALUE!=6) {
				rerolls[i]=true;
			}
		}
		return rerolls;
	}

}
