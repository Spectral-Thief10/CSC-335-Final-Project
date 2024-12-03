package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import model.ScoreSheet.Category;

public class HardMode implements Strategy{
	private CPU cpu;
	private ArrayList<Dice> diceSet;
	/*
	 * @pre dice.size() == 5
	 */
	public HardMode(CPU cpu,ArrayList<Dice> dice) {
		// TODO Auto-generated constructor stub
		this.cpu = cpu;
		this.diceSet = new ArrayList<>();
		setDice(diceSet);
	}
	/*
	 * @pre dice.size() == 5
	 */
	@Override
	public Category chooseCategory(ArrayList<Dice> dice) {
		// TODO Auto-generated method stub
		setDice(dice);
		ArrayList<Category> left = cpu.categoriesLeft();
		
		if(yahtzeeRoll() == true) {
			return Category.YAHTZEE;
		}
		else {
			if(left.size()==2 && left.contains(Category.YAHTZEE) && left.contains(Category.CHANCE)) {
				return Category.CHANCE;
			}
			else if(left.size()==1 && left.get(0)==Category.CHANCE) {
				return Category.CHANCE;
			}
			else if(left.size()==1&&left.get(0)==Category.YAHTZEE) {
				return Category.YAHTZEE;
			}
			else {
				boolean[] rerolls = resetRerolls();
				rerolls = this.straightRerolls(diceSet, rerolls);
				if(loopThrough(rerolls)==true && left.contains(Category.LARGE_STRAIGHT)) {
					return Category.LARGE_STRAIGHT;
				}
				rerolls = this.kindRerolls(diceSet, rerolls);
				if(this.kindCount(diceSet) == 1 && left.contains(Category.FOUR_OF_A_KIND)) {
					return Category.FOUR_OF_A_KIND;
				}
				rerolls = resetRerolls();
				rerolls = this.fullHouseRerolls(diceSet, rerolls);
				if(loopThrough(rerolls)==true && left.contains(Category.FULL_HOUSE)) {
					return Category.FULL_HOUSE;
				}
				rerolls = resetRerolls();
				rerolls = this.straightRerolls(diceSet, rerolls);
				if(this.smallStraight(rerolls) && left.contains(Category.SMALL_STRAIGHT)) {
					return Category.SMALL_STRAIGHT;
				}
				rerolls = resetRerolls();
				rerolls = this.kindRerolls(diceSet, rerolls);
				if(this.kindCount(diceSet)==2 && left.contains(Category.THREE_OF_A_KIND)) {
					return Category.THREE_OF_A_KIND;
				}
			}
			return upperSectionChoice();
		}
	}
	private Category upperSectionChoice() {
		if(yahtzeeRoll()==true) {
			return Category.YAHTZEE;
		}
	int[]numberCounts = this.countsOfNums(diceSet);
	ArrayList<Category> left = cpu.categoriesLeft();
	int highestCount = numberCounts[5];
	for(int i = numberCounts.length-1;i>=0;i--) {
		highestCount = numberCounts[i];
		boolean check = true;
		for(int e = numberCounts.length-1;e>=0;e--) {
			if(numberCounts[e]>highestCount) {
				check = false;
			}
		}
		if(check == true) {
			if(i == 5 && left.contains(Category.SIX)) {
				return Category.SIX;
			}
			else if(i == 4 && left.contains(Category.FIVE)) {
				return Category.FIVE;
			}
			else if(i == 3 && left.contains(Category.FOUR)) {
				return Category.FOUR;
			}
			else if(i == 2 && left.contains(Category.THREE)) {
				return Category.THREE;
			}
			else if(i == 1 && left.contains(Category.TWO)) {
				return Category.TWO;
			}
			else if(i == 0 && left.contains(Category.ONE)) {
				return Category.ONE;
			}
		}
	}
	return chooseRemainingCategory();
	}
	private Category chooseRemainingCategory() {
		ArrayList<Category> left = cpu.categoriesLeft();
		if(left.contains(Category.CHANCE)) {
			return Category.CHANCE;
		}
		if(left.size()>0) {
		return left.get(0);}
		return Category.YAHTZEE;
	}
	
	/*
	 * @pre dice.size() == 5
	 */
	private int[] countsOfNums(ArrayList<Dice> dice) {
		int[] numbers = {0,0,0,0,0,0};
		setDice(dice);
		for(int i = 0; i < diceSet.size();i++) {
			numbers[diceSet.get(i).VALUE-1]=numbers[diceSet.get(i).VALUE-1]+1;
		}
		return numbers;
	}
	/*
	 * @pre dice.size() == 5
	 */
	private int kindCount(ArrayList<Dice> dice) {
		setDice(dice);
		int count = 0;
		boolean[] rerolls = resetRerolls();
		rerolls = this.kindRerolls(diceSet, rerolls);
		for(int i = 0; i < rerolls.length;i++) {
			if(rerolls[i]==true) {
				count++;
			}
		}
		return count;
	}
	private boolean[]resetRerolls() {
		boolean[] rerolls = {false,false,false,false,false};
		return rerollsCopy(rerolls);
	}
	
	/*
	 * @pre rerolls.length == 5
	 */
	private boolean smallStraight(boolean[]rerolls) {
		int differences = 0;
		for(int i = 0; i < rerolls.length;i++) {
			if(rerolls[i]==true) {
				differences++;
			}
		}
		return differences==1;
	}
	/*
	 * @pre rerolls.length == 5
	 */
	private boolean loopThrough(boolean[]rerolls) {
		for(int i = 0; i < rerolls.length;i++) {
			if(rerolls[i]==true) {
				return false;
			}
		}
		return true;
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
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] upperKindRerolls(ArrayList<Dice> dice, boolean[]rerolls) {
		setDice(dice);
		for(int i = 0; i < 5; i++) {
			if(cpu.getCategory()==Category.ONE) {
				if(diceSet.get(i).VALUE!=1) {
					rerolls[i]=true;
				}
			}
			if(cpu.getCategory()==Category.TWO) {
				if(diceSet.get(i).VALUE!=2) {
					rerolls[i]=true;
				}
			}
			if(cpu.getCategory()==Category.THREE) {
				if(diceSet.get(i).VALUE!=3) {
					rerolls[i]=true;
				}
			}
			if(cpu.getCategory()==Category.FOUR) {
				if(diceSet.get(i).VALUE!=4) {
					rerolls[i]=true;
				}
			}
			if(cpu.getCategory()==Category.FIVE) {
				if(diceSet.get(i).VALUE!=5) {
					rerolls[i]=true;
				}
			}
			if(cpu.getCategory()==Category.SIX) {
				if(diceSet.get(i).VALUE!=6) {
					rerolls[i]=true;
				}
			}
		}
		return rerollsCopy(rerolls);
	}
	private boolean yahtzeeRoll() {
		int first = diceSet.get(0).VALUE;
		for(int i = 0; i < diceSet.size();i++) {
			if(first != diceSet.get(i).VALUE) {
				return false;
			}
		}
		return true;
	}
	/*
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] kindRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		int[] numbers = {0,0,0,0,0,0};
		setDice(dice);
		
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
		return rerollsCopy(rerolls);
	}
	/*
	 * @pre rerolls.length == 5
	 */
	private boolean[] rerollsCopy(boolean[]rerolls) {
		boolean[] copy = {false,false,false,false,false};
		for(int i = 0; i < copy.length;i++) {
			copy[i]=rerolls[i];
		}
		return copy;
	}
	/*
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] fullHouseRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
		
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
				if(indexMap.get(i).size()!=1) {
					rerolls[indexMap.get(i).get(0)]=true;
				}
			}
			return rerollsCopy(rerolls);
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
			return rerollsCopy(rerolls);
		}
		for(int i : indexMap.keySet()) {
			for(int e : indexMap.get(i)) {
				if(i != numWithHighCount && i != numWithSecondHighCount) {
					rerolls[e]=true;
				}
			}
			
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] straightRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
		ArrayList<Dice> diceSet = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			diceSet.add(new Dice(this.diceSet.get(i).VALUE));
		}
		int highestStraight = 1;
		int currentStraight = 1;
		
		
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
		return rerollsCopy(rerolls);
	}

	/*
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] yahtzeeRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
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
		int highestCount = 0;
		int numWithHighCount = 0;
		for(int i : map.keySet()) {
			if(map.get(i)>highestCount) {
				highestCount = map.get(i);
				numWithHighCount = i;
			}
		}
		for(int i : map.keySet()) {
			if(map.get(i)==highestCount && numWithHighCount < i) {
				numWithHighCount = i;
				highestCount = map.get(i);
			}
		}
		if(highestCount == 1) {
			int max = 0;
			int index = 0;
			for(int i = 0; i <diceSet.size();i++) {
				if(max < diceSet.get(i).VALUE) {
					max = diceSet.get(i).VALUE;
					index = i;
				}
			}
			for(int i = 0; i < diceSet.size();i++) {
				if(index != i) {
					rerolls[i]=true;
				}
			}
			return rerollsCopy(rerolls);
		}
		for(int i : indexMap.keySet()) {
			for(int e : indexMap.get(i)) {
				if(i != numWithHighCount) {
					rerolls[e]=true;
				}
			}
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] chanceRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
		for(int i = 0; i < diceSet.size();i++) {
			if(diceSet.get(i).VALUE!=6) {
				rerolls[i]=true;
			}
		}
		return rerollsCopy(rerolls);
	}
	
	

}
