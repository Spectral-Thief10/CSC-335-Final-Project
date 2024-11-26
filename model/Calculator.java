/**
 * The Calculator class is going to calculate the score for each category.
 */
package model;

import java.util.ArrayList;

public class Calculator {

	/*
	 * This method will calculate the score for the upper section of the game which
	 * is the category from one to six
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @param categoryVal is the dice face category to add up
	 * 
	 * @return its return the total
	 **/
	public static int upperCalculator(ArrayList<Dice> dice, int categoryVal) {
		int sum = 0;
		for (int i = 0; i < dice.size(); i++){
			if (dice.get(i).VALUE == categoryVal) {
				sum += dice.get(i).VALUE;
			}
		}

		return sum;
	}

	/*
	 * This method will calculate the score for the three of a kind category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public static int threeOfAKindCalculator(ArrayList<Dice> dice) {

		for (int i = 0; i < dice.size(); i++) {

			int count = 0;
			for (int j = 0; j < dice.size(); j++) {
				if (dice.get(i) == dice.get(j)) {
					count++;
				}
			}
			if (count >= 3) {
				int total = 0;
				for (int j = 0; j < dice.size(); j++) {
					total = total + dice.get(j).VALUE;
				}
				return total;
			}
		}
		return 0;
	}

	/*
	 * This method will calculate the score for the four of a kind category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public static int fourOfAKindCalculator(ArrayList<Dice> dice) {

		for (int i = 0; i < dice.size(); i++) {

			int count = 0;
			for (int j = 0; j < dice.size(); j++) {
				if (dice.get(i) == dice.get(j)) {
					count++;
				}
			}
			if (count >= 4) {
				int total = 0;
				for (int j = 0; j < dice.size(); j++) {
					total += dice.get(j).VALUE;
				}
				return total;
			}
		}
		return 0;
	}

	/*
	 * This method will calculate the score for the full House category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public static int fullHouseCalculator(ArrayList<Dice> dice) {
		boolean threeOfAKind = false;
		boolean pair = false;

		for (int i = 0; i < dice.size(); i++) {
			int count = 0;

			for (int j = 0; j < dice.size(); j++) {
				if (dice.get(i) == dice.get(j)) {
					count++;
				}
			}
			if (count == 3) {
				threeOfAKind = true;
			}
			if (count == 2) {
				pair = true;
			}
		}
		if (threeOfAKind && pair) {
			return 25;
		} else {
			return 0;
		}
	}

	/*
	 * This method will calculate the score for the small Straight category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public static int smallStraightCalculator(ArrayList<Dice> dice) {
		boolean[] number = new boolean[5];

		for (int i = 0; i < dice.size(); i++) {
			number[dice.get(i).VALUE - 1] = true;
		}

		if ((number[0] && number[1] && number[2] && number[3]) || (number[1] && number[2] && number[3] && number[4])
				|| (number[2] && number[3] && number[4] && number[5])) {
			return 30;
		}

		return 0;
	}

	/*
	 * This method will calculate the score for the large Straight category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public static int largeStraightCalculator(ArrayList<Dice> dice) {
		boolean[] number = new boolean[5];

		for (int i = 0; i < dice.size(); i++) {
			number[dice.get(i).VALUE - 1] = true;
		}

		if ((number[0] && number[1] && number[2] && number[3]) && number[4]
				|| (number[1] && number[2] && number[3] && number[4] && number[5])) {
			return 40;
		}

		return 0;
	}

	/*
	 * This method will calculate the score for the yahtzee category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public static int yahtzeeCalculator(ArrayList<Dice> dice) {

		for (int i = 0; i < dice.size(); i++) {

			int count = 0;
			for (int j = 0; j < dice.size(); j++) {
				if (dice.get(i) == dice.get(j)) {
					count++;
				}
			}
			if (count >= 5) {
				return 50;
			}
		}
		return 0;
	}

	/*
	 * This method will calculate the score for the chance category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public static int chanceCalculator(ArrayList<Dice> dice) {
		int total = 0;

		for (int i = 0; i < dice.size(); i++) {
			total += dice.get(i).VALUE;
		}

		return total;
	}
}
