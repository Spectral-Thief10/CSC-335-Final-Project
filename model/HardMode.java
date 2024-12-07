/*
 * File: EasyMode
 * Purpose: determines the strategy for a CPU
 * that is in hard mode
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.ScoreSheet.Category;

public class HardMode implements Strategy {
	private CPU cpu;
	private ArrayList<Dice> diceSet;

	/*
	 * @pre dice.size() == 5
	 */
	public HardMode(CPU cpu, ArrayList<Dice> dice) {
		// TODO Auto-generated constructor stub
		this.cpu = cpu;
		this.diceSet = new ArrayList<>();
		setDice(diceSet);
	}

	/*
	 * chooses a category to roll the dice based off their roll and the unused
	 * categories returns Category
	 * 
	 * @pre dice.size() == 5
	 */
	@Override
	public Category chooseCategory(ArrayList<Dice> dice) {
		// TODO Auto-generated method stub
		setDice(dice);
		// gets the categories that are left for the CPU
		ArrayList<Category> left = cpu.categoriesLeft();
		// sets the category to YAHTZEE if a yahtzee was rolled
		if (yahtzeeRoll() == true && left.contains(Category.YAHTZEE)) {
			return Category.YAHTZEE;
		}
		// checks to see if the dice match a category with the lowest odds of getting
		// first
		// if not, then it rerolls based off what dice it has
		else {
			if (left.size() == 2 && left.contains(Category.YAHTZEE) && left.contains(Category.CHANCE)) {
				return Category.CHANCE;
			} else if (left.size() == 1 && left.get(0) == Category.CHANCE) {
				return Category.CHANCE;
			} else if (left.size() == 1 && left.get(0) == Category.YAHTZEE) {
				return Category.YAHTZEE;
			} else {
				boolean[] rerolls = resetRerolls();
				rerolls = this.straightRerolls(diceSet, rerolls);
				if (loopThrough(rerolls) == true && left.contains(Category.LARGE_STRAIGHT)) {
					return Category.LARGE_STRAIGHT;
				}
				rerolls = this.kindRerolls(diceSet, rerolls);
				if (this.kindCount(diceSet) == 1 && left.contains(Category.FOUR_OF_A_KIND)) {
					return Category.FOUR_OF_A_KIND;
				}
				rerolls = resetRerolls();
				rerolls = this.fullHouseRerolls(diceSet, rerolls);
				if (loopThrough(rerolls) == true && left.contains(Category.FULL_HOUSE)) {
					return Category.FULL_HOUSE;
				}
				rerolls = resetRerolls();
				rerolls = this.straightRerolls(diceSet, rerolls);
				if (this.smallStraight(rerolls) && left.contains(Category.SMALL_STRAIGHT)) {
					return Category.SMALL_STRAIGHT;
				}
				rerolls = resetRerolls();
				rerolls = this.kindRerolls(diceSet, rerolls);
				if (this.kindCount(diceSet) == 2 && left.contains(Category.THREE_OF_A_KIND)) {
					return Category.THREE_OF_A_KIND;
				}
			}
			return upperSectionChoice();
		}
	}

	/*
	 * determines which upper section category to choose returns Category
	 * 
	 * @return category that the CPU chose to select
	 */
	private Category upperSectionChoice() {
		int[] numberCounts = this.countsOfNums(diceSet);
		ArrayList<Category> left = cpu.categoriesLeft();
		int highestCount = numberCounts[5];
		for (int i = numberCounts.length - 1; i >= 0; i--) {
			highestCount = numberCounts[i];
			boolean check = true;
			// gets the number that is the largest and has the highest count
			for (int e = numberCounts.length - 1; e >= 0; e--) {
				if (numberCounts[e] > highestCount) {
					check = false;
				}
			}
			// if the highest count is not the highest, it chooses a category that has the
			// next highest count
			if (check == true) {
				if (i == 5 && left.contains(Category.SIX)) {
					return Category.SIX;
				} else if (i == 4 && left.contains(Category.FIVE)) {
					return Category.FIVE;
				} else if (i == 3 && left.contains(Category.FOUR)) {
					return Category.FOUR;
				} else if (i == 2 && left.contains(Category.THREE)) {
					return Category.THREE;
				} else if (i == 1 && left.contains(Category.TWO)) {
					return Category.TWO;
				} else if (i == 0 && left.contains(Category.ONE)) {
					return Category.ONE;
				}
			}
		}
		return chooseRemainingCategory();
	}

	/*
	 * chooses one of the remaining categories to complete returns Category
	 * 
	 * @return category that the CPU chose to select
	 */
	private Category chooseRemainingCategory() {
		ArrayList<Category> left = cpu.categoriesLeft();
		// chooses the CHANCE if the dice don't fit any particular category
		if (left.contains(Category.CHANCE)) {
			return Category.CHANCE;
		}
		// chooses one of the remaining categories
		if (left.size() > 0) {
			return left.get(0);
		}
		// chooses a yahtzee
		return Category.YAHTZEE;
	}

	/*
	 * gets the counts of all the numbers in the arrayList of dice returns int[]
	 * 
	 * @pre dice.size() == 5
	 */
	private int[] countsOfNums(ArrayList<Dice> dice) {
		int[] numbers = { 0, 0, 0, 0, 0, 0 };
		setDice(dice);
		// counts each of the numbers and their value-1 is their index
		for (int i = 0; i < diceSet.size(); i++) {
			numbers[diceSet.get(i).VALUE - 1] = numbers[diceSet.get(i).VALUE - 1] + 1;
		}
		return numbers;
	}

	/*
	 * gets the number of the dice that are supposed to be rerolled when going for a
	 * three of a kind or four of a kind
	 * 
	 * @pre dice.size() == 5
	 */
	private int kindCount(ArrayList<Dice> dice) {
		setDice(dice);
		int count = 0;
		boolean[] rerolls = resetRerolls();
		rerolls = this.kindRerolls(diceSet, rerolls); // gets the rerolls for the kind
		// counts the dice that are supposed to be rerolled
		for (int i = 0; i < rerolls.length; i++) {
			if (rerolls[i] == true) {
				count++;
			}
		}
		return count;
	}

	/*
	 * gets an array of 5 false booleans return boolean[]
	 * 
	 * @return dice that the cpu would like to reroll
	 */
	private boolean[] resetRerolls() {
		boolean[] rerolls = { false, false, false, false, false };
		return rerollsCopy(rerolls);
	}

	/*
	 * determines if the rerolls is a small straight returns boolean
	 * 
	 * @pre rerolls.length == 5
	 */
	private boolean smallStraight(boolean[] rerolls) {
		int differences = 0;
		// counts the number of dice that are supposed to be rerolled
		// after the straight rerolls is called in chooseCategory
		for (int i = 0; i < rerolls.length; i++) {
			if (rerolls[i] == true) {
				differences++;
			}
		}
		return differences == 1 || differences == 0;
	}

	/*
	 * checks to see if no dice should be rerolled (The rerolls matches the category
	 * exactly) return boolean
	 * 
	 * @pre rerolls.length == 5
	 */
	private boolean loopThrough(boolean[] rerolls) {
		for (int i = 0; i < rerolls.length; i++) {
			if (rerolls[i] == true) {
				return false;
			}
		}
		return true;
	}

	/*
	 * sets the diceSet instance variable to have the same dice as the diceSet that
	 * was passed in returns void
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
	 * determines the dice to reroll for the upper section returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] upperKindRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		setDice(dice);
		// rerolls the dice that do not match the Category number
		// for the upper section
		for (int i = 0; i < 5; i++) {
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
	 * checks to see if the roll was a yahztee or not returns boolean
	 */
	private boolean yahtzeeRoll() {
		int first = diceSet.get(0).VALUE;
		// returns true if all the numbers are the same
		for (int i = 0; i < diceSet.size(); i++) {
			if (first != diceSet.get(i).VALUE) {
				return false;
			}
		}
		return true;
	}

	/*
	 * determines the dice to reroll if the category is a four of a kind or three of
	 * a kind returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] kindRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
		// counts the numbers
		int[] numbers = countsOfNums(dice);
		int highest = 0; // gets the highest count and its index
		int index = 0;
		for (int i = 0; i < 6; i++) {
			// sets the index to the higher one if the highest has the same count as the
			// current index
			if (numbers[i] == highest && i > index) {
				index = i;
			}
			if (numbers[i] > highest) {
				highest = numbers[i];
				index = i;
			}
		}
		// rerolls the dice that do not have the index+1 value
		for (int i = 0; i < rerolls.length; i++) {
			if (diceSet.get(i).VALUE != index + 1) {
				rerolls[i] = true;
			}
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * makes a copy of an inputted boolean array returns boolean[]
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
	 * determines the dice to rerolls for a full house returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] fullHouseRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);

		HashMap<Integer, Integer> map = new HashMap<>(); // creates a hashmap for the counts of dice
		HashMap<Integer, ArrayList<Integer>> indexMap = new HashMap<>(); // creates a hashmap for the indexes of the
																			// dice
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
		int numWithHighCount = 0; // gets the number with the highest count
		int highestCount = 0; // gets the highest count
		// sets the number with the highest count, and the highest count
		for (int i : map.keySet()) {
			// sets the number with the highest count to the higher number
			// if they both have the same count
			if (map.get(i) == highestCount) {
				if (numWithHighCount < i) {
					numWithHighCount = i;
				}
			} else if (map.get(i) > highestCount) {
				numWithHighCount = i;
				highestCount = map.get(numWithHighCount);
			}
		}
		// rerolls one of the dice with a count of 4
		if (highestCount == 4) {
			for (int i : indexMap.keySet()) {
				if (indexMap.get(i).size() != 1) {
					rerolls[indexMap.get(i).get(0)] = true;
				}
			}
			return rerollsCopy(rerolls);
		}
		int numWithSecondHighCount = 0; // gets the number with the second highest count
		int secondHighestCount = 0; // gets the second highest count
		for (int i : map.keySet()) {
			// if the second highest count is the same as the highest count,
			// and the number is not equal to the highest count,
			// sets the number with the second highest count to the larger number
			if (map.get(i) == secondHighestCount && i > numWithSecondHighCount) {
				if (i != numWithHighCount) {
					numWithSecondHighCount = i;
				}
			}
			// sets the number with the second highest count and its count
			else if (map.get(i) > secondHighestCount) {
				if (i != numWithHighCount && map.get(i) > secondHighestCount) {
					numWithSecondHighCount = i;
					secondHighestCount = map.get(i);
				}
			}
		}
		// does not reroll anything if it is a full house already
		if (highestCount == 3 && secondHighestCount == 2) {
			return rerollsCopy(rerolls);
		}
		// rerolls the dice that are not the number with the
		// highest count and the number with the second highest count
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
	 * determines the dice to reroll to get a straight returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] straightRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
		ArrayList<Dice> diceSet = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			diceSet.add(new Dice(this.diceSet.get(i).VALUE));
		}
		int highestStraight = 1;
		int currentStraight = 1;

		HashMap<Integer, ArrayList<Integer>> indexMap = new HashMap<>(); // maps all the dice to their indexes before
		// they get sorted
		for (int i = 0; i < diceSet.size(); i++) {
			// adds the index if the dice is already in the hashmap
			if (indexMap.containsKey(diceSet.get(i).VALUE)) {
				indexMap.get(diceSet.get(i).VALUE).add(i);
			} else {
				// puts the dice into the index map along with its index
				ArrayList<Integer> index = new ArrayList<>();
				index.add(i);
				indexMap.put(diceSet.get(i).VALUE, index);
			}
		}
		// sorts the diceSet
		Collections.sort(diceSet);
		ArrayList<Integer> rerollNot = new ArrayList<>(); // creates an arraylist of dice to not reroll
		int fakeStraight = 1; // a straight that contains duplicates
		// loops through to determine which dice to not reroll
		for (int i = 1; i < diceSet.size(); i++) {
			int current = diceSet.get(i).VALUE;
			int prev = diceSet.get(i - 1).VALUE;
			if (current == prev) {
				fakeStraight++; // increments the duplicate straight if the numbers are the same
			} else if (current == prev + 1) { // increments both straights if it is in correct order
				currentStraight++;
				fakeStraight++;
			}

			else { // resets both straights otherwise
				fakeStraight = 1;
				currentStraight = 1;
			}
			// if the current straight is larger than the highest straight,
			// the highest straight is set to the current straight
			if (currentStraight == highestStraight) {
				ArrayList<Integer> tmpNot = new ArrayList<>();
				// creates a temporary arrayList of dice not to reroll
				for (int e = i; e > i - fakeStraight; e--) {
					if (!tmpNot.contains(diceSet.get(e).VALUE)) {
						tmpNot.add(diceSet.get(e).VALUE);
					}

				}
				int tmpSum = 0;
				int rerollNotSum = 0;
				// if the original sum of all the dice in the original straight
				// is greater than the tmp straight, then the tmp straight is the new current
				// straight
				if (rerollNot.size() == tmpNot.size()) {
					for (int e = 0; e < tmpNot.size(); e++) {
						tmpSum += tmpNot.get(e);
						rerollNotSum += rerollNot.get(e);
					}
				}
				if (tmpSum >= rerollNotSum) {
					rerollNot = tmpNot;
				}

			}
			// sets the gets the numbers that you are not supposed to reroll and
			// stores them in rerollNot
			else if (currentStraight > highestStraight) {
				highestStraight = currentStraight;
				rerollNot = new ArrayList<>();
				for (int e = i; e > i - fakeStraight; e--) {
					if (!rerollNot.contains(diceSet.get(e).VALUE)) {
						rerollNot.add(diceSet.get(e).VALUE);
					}

				}
			}

		}
		// creates an arraylist of dice to reroll
		ArrayList<Integer> rerollAt = new ArrayList<>();

		for (int i : indexMap.keySet()) {
			while (indexMap.get(i).size() > 1) { // rerolls all the duplicate dice
				rerollAt.add(indexMap.get(i).remove(0));
			}
		}
		for (int i : indexMap.keySet()) {// rerollAt gets the dice that are not in the rerollNot arraylist
			if (!rerollNot.contains(i)) {
				rerollAt.add(indexMap.get(i).get(0));
			}
		}
		// rerolls the dice that should be rerolled
		for (int i : rerollAt) {
			rerolls[i] = true;
		}
		return rerollsCopy(rerolls);
	}

	/*
	 * determines the dice to reroll to get a yahtzee returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] yahtzeeRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
		HashMap<Integer, Integer> map = new HashMap<>(); // creates a hashmap for the counts of dice
		HashMap<Integer, ArrayList<Integer>> indexMap = new HashMap<>(); // creates a hashmap for the indexes of the
																			// dice
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
		int highestCount = 0; // is the highest count
		int numWithHighCount = 0; // is the number with the highest count
		for (int i : map.keySet()) { // gets the highest count, and the number that has that highest count
			if (map.get(i) > highestCount) {
				highestCount = map.get(i);
				numWithHighCount = i;
			}
		}
		// if a number is larger than the number with the highest count, then the new
		// number with the
		// highest count is the higher number
		for (int i : map.keySet()) {
			if (map.get(i) == highestCount && numWithHighCount < i) {
				numWithHighCount = i;
				highestCount = map.get(i);
			}
		}
		// rerolls all the dice that are not the highest if the highest count is 1
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
		// rerolls all the dice that are not the number with the highest count
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
	 * determines the dice to reroll for the chance returns boolean[]
	 * 
	 * @pre rerolls.length == 5 && dice.size() == 5
	 */
	@Override
	public boolean[] chanceRerolls(ArrayList<Dice> dice, boolean[] rerolls) {
		// TODO Auto-generated method stub
		setDice(dice);
		// rerolls all the dice that are not 6
		for (int i = 0; i < diceSet.size(); i++) {
			if (diceSet.get(i).VALUE != 6) {
				rerolls[i] = true;
			}
		}
		return rerollsCopy(rerolls);
	}

}
