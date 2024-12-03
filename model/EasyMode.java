
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.ScoreSheet.Category;

public class EasyMode implements Strategy{
	private CPU cpu;
	private ArrayList<Dice> diceSet;
	/*
	 * @pre dice.size() == 5
	 */
	public EasyMode(CPU cpu,ArrayList<Dice> dice) {
		this.cpu = cpu;
		setDice(dice);
	}
	/*
	 * @pre dice.size() == 5
	 */
	public Category chooseCategory(ArrayList<Dice> dice) {
		setDice(dice);
		return cpu.categoriesLeft().get(0);
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
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] upperKindRerolls(ArrayList<Dice> diceSet1,boolean[]rerolls) {
		setDice(diceSet1);
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
	/*
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] kindRerolls(ArrayList<Dice> diceSet1,boolean []rerolls) {
		int[] numbers = {0,0,0,0,0,0};
		setDice(diceSet1);
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
		return rerollsCopy(rerolls);
	}
	/*
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] fullHouseRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(diceSet1);
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
			for(int i = 1; i < diceSet.size();i++) {
				if(diceSet.get(i).VALUE==diceSet.get(i-1).VALUE) {
					rerolls[i]=true;
					return rerolls;
				}
			}
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
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] straightRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		int highestStraight = 1;
		int currentStraight = 1;
		setDice(diceSet1);
		ArrayList<Dice> diceSet = new ArrayList<>();
		for(int i = 0; i < this.diceSet.size();i++) {
			diceSet.add(new Dice(this.diceSet.get(i).VALUE));
		}
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
			if(currentStraight > highestStraight) {
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
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] yahtzeeRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		
		HashMap<Integer,Integer> map = new HashMap<>();
		HashMap<Integer,ArrayList<Integer>> indexMap = new HashMap<>();
		setDice(diceSet1);
		
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
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] chanceRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(diceSet1);
		for(int i = 0; i < diceSet.size();i++) {
			if(diceSet.get(i).VALUE!=6) {
				rerolls[i]=true;
			}
		}
		return rerollsCopy(rerolls);
	}
	

}
