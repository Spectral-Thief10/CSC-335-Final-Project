/*
 * File: EasyMode
 * Purpose: determines the strategy for a CPU
 * that is in easy mode
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.ScoreSheet.Category;

public class EasyMode implements Strategy {
	private CPU cpu;
	private ArrayList<Dice> diceSet;

	/*
	 * @pre dice.size() == 5
	 */
	public EasyMode(CPU cpu, ArrayList<Dice> dice) {
		this.cpu = cpu;
		setDice(dice);
	}

	/*
	 * chooses a category based on the dice that were passed in The first unused
	 * category is chosen returns Category
	 * 
	 * @pre dice.size() == 5
	 */
	public Category chooseCategory(ArrayList<Dice> dice) {
		setDice(dice);
		return cpu.categoriesLeft().get(0);
	}

	/*
	 * sets the diceSet instance variable to have the dice as the passed in
	 * ArrayList of dice returns void
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
	 * Chooses the dice to reroll based off the uppper section category. returns
	 * boolean[]
	 * 
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] upperKindRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		setDice(diceSet1);
		for (int i = 0; i < 5; i++) {
			// rerolls the dice that do not have the same value as
			// the category section
			if (cpu.getCategory() == Category.ONE) {
				if (diceSet.get(i).VALUE != 1) {
					rerolls[i] = true;
				}
			}
			if (cpu.getCategory() == Category.TWO) {
				if (diceSet.get(i).VALUE != 2) {
					rerolls[i] = true;
				}
			}
			if (cpu.getCategory() == Category.THREE) {
				if (diceSet.get(i).VALUE != 3) {
					rerolls[i] = true;
				}
			}
			if (cpu.getCategory() == Category.FOUR) {
				if (diceSet.get(i).VALUE != 4) {
					rerolls[i] = true;
				}
			}
			if (cpu.getCategory() == Category.FIVE) {
				if (diceSet.get(i).VALUE != 5) {
					rerolls[i] = true;
				}
			}
			if (cpu.getCategory() == Category.SIX) {
				if (diceSet.get(i).VALUE != 6) {
					rerolls[i] = true;
				}
			}
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * Determines the dice to rerolls to get a four of a kind or three of a kind
	 * returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] kindRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		int[] numbers = { 0, 0, 0, 0, 0, 0 };
		setDice(diceSet1);
		// loops through and stores the counts of each of the number
		// into the numbers array
		for (int i = 0; i < rerolls.length; i++) {
			numbers[diceSet.get(i).VALUE - 1] = numbers[diceSet.get(i).VALUE - 1] + 1;
		}
		int highest = 0; // most occurred number
		int index = 0; // the index of the number with the highest count
		for (int i = 0; i < 6; i++) {
			if (numbers[i] > highest) {
				highest = numbers[i];
				index = i;
			}
		}
		// rerolls the numbers from the diceSet that are not
		// the number with the highest count
		for (int i = 0; i < rerolls.length; i++) {
			if (diceSet.get(i).VALUE != index + 1) {
				rerolls[i] = true;
			}
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * determines the dice to reroll to get a full house returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] fullHouseRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(diceSet1);
		HashMap<Integer, Integer> map = new HashMap<>(); // creates a hashmap that stores the numbers and their counts
		HashMap<Integer, ArrayList<Integer>> indexMap = new HashMap<>(); // creates a hashmap that stores the numbers
																			// and their original indexes
		for (int i = 0; i < diceSet.size(); i++) {
			// increments the count for the number and adds another index to that number's
			// ArrayList
			if (map.containsKey(diceSet.get(i).VALUE)) {
				int num = map.get(diceSet.get(i).VALUE);
				num++;
				map.remove(diceSet.get(i).VALUE);
				map.put(diceSet.get(i).VALUE, num);
				indexMap.get(diceSet.get(i).VALUE).add(i);
			}
			// puts the number into each of the hashmaps
			else {
				ArrayList<Integer> index = new ArrayList<>();
				index.add(i);
				indexMap.put(diceSet.get(i).VALUE, index);
				map.put(diceSet.get(i).VALUE, 1);
			}
		}
		int numWithHighCount = 0; // the number with the highest count
		int highestCount = 0; // the highest count
		// gets the number with the highest count and its corresponding count
		for (int i : map.keySet()) {
			if (map.get(i) > highestCount) {
				numWithHighCount = i;
				highestCount = map.get(numWithHighCount);
			}
		}
		// rerolls the dice if all the dice are the same number
		if (highestCount == 5) {
			rerolls[4] = true;
			rerolls[3] = true;
			return rerolls;
		}
		// rerolls one of the dice with the highest count
		if (highestCount == 4) {
			for (int i = 1; i < diceSet.size(); i++) {
				if (diceSet.get(i).VALUE == diceSet.get(i - 1).VALUE) {
					rerolls[i] = true;
					return rerolls;
				}
			}
		}
		int numWithSecondHighCount = 0;
		int secondHighestCount = 0;
		// gets the number with the second highest count and its count
		for (int i : map.keySet()) {
			if (map.get(i) > secondHighestCount) {
				if (i != numWithHighCount && map.get(i) > secondHighestCount) {
					numWithSecondHighCount = i;
					secondHighestCount = map.get(i);
				}
			}
		}
		if (highestCount == 3 && secondHighestCount == 2) { // no dice are rerolled if it is a full house
			return rerolls;
		}
		// rerolls the dice that are don't have the first highest and second highest
		// counts
		for (int i : indexMap.keySet()) {
			for (int e : indexMap.get(i)) {
				if (i != numWithHighCount && i != numWithSecondHighCount) {
					rerolls[e] = true;
				}
			}

		}
		return rerollsCopy(rerolls);
	}

	/*
	 * makes a copy of the rerolls array returns boolean[]
	 * 
	 * @pre rerolls.length == 5
	 */
	private boolean[] rerollsCopy(boolean[] rerolls) {
		boolean[] copy = { false, false, false, false, false };
		for (int i = 0; i < copy.length; i++) {
			copy[i] = rerolls[i];
		}
		return copy;
	}

	/*
	 * chooses the dice to reroll to get a straight returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] straightRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		int highestStraight = 1;
		int currentStraight = 1;
		setDice(diceSet1);
		ArrayList<Dice> diceSet = new ArrayList<>();
		for (int i = 0; i < this.diceSet.size(); i++) {
			diceSet.add(new Dice(this.diceSet.get(i).VALUE));
		}
		// creates a hashmap that stores all the original indexes before the dice are
		// sorted
		HashMap<Integer, ArrayList<Integer>> indexMap = new HashMap<>();
		for (int i = 0; i < diceSet.size(); i++) {
			if (indexMap.containsKey(diceSet.get(i).VALUE)) {
				indexMap.get(diceSet.get(i).VALUE).add(i);
			} else {
				ArrayList<Integer> index = new ArrayList<>();
				index.add(i);
				indexMap.put(diceSet.get(i).VALUE, index);
			}
		}
		Collections.sort(diceSet); // sorts the diceSet
		ArrayList<Integer> rerollNot = new ArrayList<>(); // ArrayList of dice that should not get rerolled
		int fakeStraight = 1; // is a straight that includes duplicates
		// loops through and determines which dice to not reroll
		for (int i = 1; i < diceSet.size(); i++) {
			int current = diceSet.get(i).VALUE; // gets the current value
			int prev = diceSet.get(i - 1).VALUE; // gets the previous value
			if (current == prev) {
				fakeStraight++; // fakeStraight is incremented because a duplicate is found
			}
			// both straights are incremented if it is in order
			else if (current == prev + 1) {
				currentStraight++;
				fakeStraight++;
			}
			// resets the straight back to 1 otherwise
			else {
				fakeStraight = 1;
				currentStraight = 1;
			}
			// the highest straight is set to the current straight if the current straight
			// is higher
			if (currentStraight > highestStraight) {
				highestStraight = currentStraight;
				rerollNot = new ArrayList<>(); // creates a new arrayList of dice that should not be rerolled
				for (int e = i; e > i - fakeStraight; e--) { // adds the dice that should not be rerolled to the
																// arrayList
					if (!rerollNot.contains(diceSet.get(e).VALUE)) {
						rerollNot.add(diceSet.get(e).VALUE);
					}

				}
			}

		}

		ArrayList<Integer> rerollAt = new ArrayList<>(); // creates an ArrayList of dice's indexes that should be
															// rerolled
		// navigates through the hashmap of dice and their indexes
		for (int i : indexMap.keySet()) {
			while (indexMap.get(i).size() > 1) { // rerolls all the duplicate dice
				rerollAt.add(indexMap.get(i).remove(0));
			}
		}
		for (int i : indexMap.keySet()) {
			if (!rerollNot.contains(i)) {
				rerollAt.add(indexMap.get(i).get(0)); // rerolls all the dice that are not in the
				// reroll not ArrayList
			}
		}
		// sets the ArrayList based on the dice in the rerollAt ArrayList
		for (int i : rerollAt) {
			rerolls[i] = true;
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * rerolls the dice to score a yahtzee returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] yahtzeeRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		// creates a hash map of dice and their counts
		HashMap<Integer, Integer> map = new HashMap<>();
		// creates a hash map of the dice and their indexes
		HashMap<Integer, ArrayList<Integer>> indexMap = new HashMap<>();
		setDice(diceSet1);

		for (int i = 0; i < diceSet.size(); i++) {
			// increments the count for the number and adds another index to that number's
			// ArrayList
			if (map.containsKey(diceSet.get(i).VALUE)) {
				int num = map.get(diceSet.get(i).VALUE);
				num++;
				map.remove(diceSet.get(i).VALUE);
				map.put(diceSet.get(i).VALUE, num);
				indexMap.get(diceSet.get(i).VALUE).add(i);
			}
			// puts the number into each of the hashmaps
			else {
				ArrayList<Integer> index = new ArrayList<>();
				index.add(i);
				indexMap.put(diceSet.get(i).VALUE, index);
				map.put(diceSet.get(i).VALUE, 1);
			}
		}
		int highestCount = 0; // gets the highest count
		int numWithHighCount = 0; // gets the number with the highest count

		// loops through to get the number with the highest count, and its corresponding
		// number
		for (int i : map.keySet()) {
			if (map.get(i) > highestCount) {
				highestCount = map.get(i);
				numWithHighCount = i;
			}
		}
		// rerolls all the numbers that are not the highest if
		// the count of each number is 1
		if (highestCount == 1) {
			int max = 0;
			int index = 0;
			for (int i = 0; i < diceSet.size(); i++) {
				if (max < diceSet.get(i).VALUE) {
					max = diceSet.get(i).VALUE;
					index = i;
				}
			}
			for (int i = 0; i < diceSet.size(); i++) {
				if (index != i) {
					rerolls[i] = true;
				}
			}
			return rerollsCopy(rerolls);
		}
		// rerolls all the dice at their indexes that do not have the highest count
		for (int i : indexMap.keySet()) {
			for (int e : indexMap.get(i)) {
				if (i != numWithHighCount) {
					rerolls[e] = true;
				}
			}
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * rerolls the dice to get the maximum chance score return boolean[]
	 * 
	 * @pre rerolls.length == 5 && diceSet1.size() == 5
	 */
	@Override
	public boolean[] chanceRerolls(ArrayList<Dice> diceSet1, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(diceSet1);
		// rerolls all the dice that are not a 6
		for (int i = 0; i < diceSet.size(); i++) {
			if (diceSet.get(i).VALUE != 6) {
				rerolls[i] = true;
			}
		}
		return rerollsCopy(rerolls);
	}

}
