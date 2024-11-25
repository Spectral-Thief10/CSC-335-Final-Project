import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.ScoreSheet.Category;

public class EasyMode implements Strategy{
	private CPU cpu;
	public EasyMode(CPU cpu) {
		this.cpu = cpu;
	}
	public Category chooseCategory() {
		return cpu.categoriesLeft().get(0);
	}
	
	@Override
	public boolean[] kindRerolls(DiceSet dice,boolean []rerolls) {
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
		ArrayList<Dice> diceSet = dice.getResult();
		HashMap<Integer,Integer> map = new HashMap<>();
		HashMap<Integer,ArrayList<Integer>> indexMap = new HashMap<>();
		for(int i = 0; i < diceSet.size();i++) {
			if(map.containsKey(diceSet.get(i).VALUE)) {
				int num = map.get(diceSet.get(i).VALUE);
				num++;
				map.remove(diceSet.get(i).VALUE);
				map.put(diceSet.get(i).VALUE, num);
				indexMap.get(diceSet.get(i).VALUE).add(i);
			}
			else {
				ArrayList<Integer> index = new ArrayList<>();
				index.add(i);
				indexMap.put(diceSet.get(i).VALUE,index);
				map.put(diceSet.get(i).VALUE, 1);
			}
		}
		int numWithHighCount = 0;
		int highestCount = 0;
		for(int i : map.keySet()) {
			if(map.get(i) > highestCount) {
				numWithHighCount = i;
				highestCount = map.get(numWithHighCount);
			}
		}
		if(highestCount == 5) {
			rerolls[4]=true;
			rerolls[3]=true;
			return rerolls;
		}
		if(highestCount == 4) {
			int index = diceSet.indexOf(numWithHighCount);
			rerolls[index] = true;
			return rerolls;
		}
		int numWithSecondHighCount = 0;
		int secondHighestCount = 0;
		for(int i : map.keySet()) {
			if(map.get(i)>secondHighestCount) {
				if(i != numWithHighCount && map.get(i) > secondHighestCount) {
					numWithSecondHighCount = i;
					secondHighestCount = map.get(i);
				}
			}
		}
		if(highestCount == 3 && secondHighestCount == 2) {
			return rerolls;
		}
		for(int i : indexMap.keySet()) {
			for(int e : indexMap.get(i)) {
				if(i != numWithHighCount && i != numWithSecondHighCount) {
					rerolls[e]=true;
				}
			}
			
		}
		return rerolls;
	}
	@Override
	public boolean[] straightRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		return rerolls;
	}
	@Override
	public boolean[] yahtzeeRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		HashMap<Integer,Integer> map = new HashMap<>();
		HashMap<Integer,ArrayList<Integer>> indexMap = new HashMap<>();
		ArrayList<Dice> diceSet = dice.getResult();
		
		for(int i = 0; i < diceSet.size();i++) {
			if(map.containsKey(diceSet.get(i).VALUE)) {
				int num = map.get(diceSet.get(i).VALUE);
				num++;
				map.remove(diceSet.get(i).VALUE);
				map.put(diceSet.get(i).VALUE, num);
				indexMap.get(diceSet.get(i).VALUE).add(i);
			}
			else {
				ArrayList<Integer> index = new ArrayList<>();
				index.add(i);
				indexMap.put(diceSet.get(i).VALUE,index);
				map.put(diceSet.get(i).VALUE, 1);
			}
		}
		int highestCount = 0;
		int numWithHighCount = 0;
		for(int i : map.keySet()) {
			if(map.get(i)>highestCount) {
				highestCount = map.get(i);
				numWithHighCount = i;
			}
		}
		for(int i : indexMap.keySet()) {
			for(int e : indexMap.get(i)) {
				if(i != numWithHighCount) {
					rerolls[e]=true;
				}
			}
		}
		return rerolls;
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
