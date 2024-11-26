import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.Dice;
import model.ScoreSheet.Category;

public class HardMode implements Strategy{
	private CPU cpu;

	public HardMode(CPU cpu) {
		// TODO Auto-generated constructor stub
		this.cpu = cpu;
	}

	private Category category = Category.LARGE_STRAIGHT;
	@Override
	public Category chooseCategory() {
		// TODO Auto-generated method stub
		ArrayList<Category> left = cpu.categoriesLeft();
		
		if(category == Category.YAHTZEE) {
			return category;
		}
		else {
			if(left.size()==1 && left.get(0)==Category.CHANCE) {
				category = Category.CHANCE;
			}
			else if(left.size()==1&&left.get(0)==Category.YAHTZEE) {
				category = left.get(0);
			}
			else {
				
			}
			return Category.FULL_HOUSE;
		}
	}
	private boolean loopThrough(boolean[]rerolls) {
		for(int i = 0; i < rerolls.length;i++) {
			if(rerolls[i]==true) {
				return false;
			}
		}
		return true;
	}
	public boolean yahtzeeRoll(DiceSet dice) {
		ArrayList<Dice> diceSet = dice.getResult();
		int first = diceSet.get(0).VALUE;
		for(int i = 0; i < diceSet.size();i++) {
			if(first != diceSet.get(i).VALUE) {
				return false;
			}
		}
		category = Category.YAHTZEE;
		return true;
	}
	@Override
	public boolean[] kindRerolls(DiceSet dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		int[] numbers = {0,0,0,0,0,0};
		if(yahtzeeRoll(dice)) {
			return rerolls;
		}
		ArrayList<Dice> diceSet = dice.getResult();
		for(int i = 0; i < rerolls.length;i++) {
			numbers[diceSet.get(i).VALUE-1] = numbers[diceSet.get(i).VALUE-1]+1;
		}
		int highest = 0;
		int index = 0;
		for(int i = 0; i < 6; i++) {
			if(numbers[i]==highest && i > index) {
				index = i;
			}
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
		if(yahtzeeRoll(dice)) {
			return rerolls;
		}
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
			if(map.get(i)==highestCount) {
				if(numWithHighCount < i) {
					numWithHighCount = i;
				}
			}
			else if(map.get(i) > highestCount) {
				numWithHighCount = i;
				highestCount = map.get(numWithHighCount);
			}
		}
		if(highestCount == 4) {
			for(int i : indexMap.keySet()) {
				if(indexMap.get(i).size()==1) {
					rerolls[indexMap.get(i).get(0)]=true;
				}
			}
			return rerolls;
		}
		int numWithSecondHighCount = 0;
		int secondHighestCount = 0;
		for(int i : map.keySet()) {
			if(map.get(i)==secondHighestCount && i > numWithSecondHighCount) {
				if(i != numWithHighCount) {
				numWithSecondHighCount = i;}
			}
			else if(map.get(i)>secondHighestCount) {
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
		if(yahtzeeRoll(dice)) {
			return rerolls;
		}
		int highestStraight = 1;
		int currentStraight = 1;
		ArrayList<Dice> diceSet = dice.getResult();
		
		HashMap<Integer,ArrayList<Integer>> indexMap = new HashMap<>();
		for(int i = 0; i < diceSet.size();i++) {
			if(indexMap.containsKey(diceSet.get(i).VALUE)) {
				indexMap.get(diceSet.get(i).VALUE).add(i);
			}
			else {
				ArrayList<Integer> index = new ArrayList<>();
				index.add(i);
				indexMap.put(diceSet.get(i).VALUE,index);
			}
		}
		Collections.sort(diceSet);
		ArrayList<Integer> rerollNot = new ArrayList<>();
		int fakeStraight = 1;
		
		for(int i = 1; i < diceSet.size();i++) {
			int current = diceSet.get(i).VALUE;
			int prev = diceSet.get(i-1).VALUE;
			if(current == prev) {
				fakeStraight++;
			}
			else if(current == prev+1) {
				currentStraight++;
				fakeStraight++;
			}
			
			else {
				fakeStraight = 1;
				currentStraight = 1;
			}
			if(currentStraight == highestStraight) {
				ArrayList<Integer> tmpNot = new ArrayList<>();
				for(int e = i; e > i-fakeStraight;e--) {
					if(!tmpNot.contains(diceSet.get(e).VALUE)) {
						tmpNot.add(diceSet.get(e).VALUE);
					}
					
				}
				int tmpSum = 0;
				int rerollNotSum = 0;
				if(rerollNot.size()==tmpNot.size()) {
				for(int e = 0; e < tmpNot.size();e++) {
					tmpSum+=tmpNot.get(e);
					rerollNotSum+=rerollNot.get(e);
				}
				}
				if(tmpSum >= rerollNotSum) {
					rerollNot = tmpNot;
				}
				
			}
			else if(currentStraight > highestStraight) {
				highestStraight = currentStraight;
				rerollNot = new ArrayList<>();
				for(int e = i; e > i-fakeStraight;e--) {
					if(!rerollNot.contains(diceSet.get(e).VALUE)) {
						rerollNot.add(diceSet.get(e).VALUE);
					}
					
				}
			}
			
		}
		
		ArrayList<Integer> rerollAt = new ArrayList<>();

			for(int i : indexMap.keySet()) {
				while(indexMap.get(i).size()>1) {
					rerollAt.add(indexMap.get(i).remove(0));
				}
			}
			for(int i : indexMap.keySet()) {
				if(!rerollNot.contains(i)) {
					rerollAt.add(indexMap.get(i).get(0));
				}
			}
		for(int i : rerollAt) {
			rerolls[i]=true;
		}
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
		if(yahtzeeRoll(dice)) {
			return rerolls;
		}
		ArrayList<Dice> diceSet = dice.getResult();
		for(int i = 0; i < diceSet.size();i++) {
			if(diceSet.get(i).VALUE!=6) {
				rerolls[i]=true;
			}
		}
		return rerolls;
	}

}
