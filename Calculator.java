/**
 * The Calculator class is going to calculate the score for each category.
 */
public class Calculator {

	/*
	 * This method will calculate the score for the upper section of the game which
	 * is the category from one to six
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @param categoryval is the type of the category that we want to calculate
	 * 
	 * @return its return the total
	 **/
	public int upperSectionCalculator(int[] dice, int categoryval) {
		int total = 0;

		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == categoryval) {
				total += dice[i];
			}
		}

		return total;
	}

	/*
	 * This method will calculate the score for the three of a kind category
	 * 
	 * @param dice is the array of the 5 dice
	 * 
	 * @return its return the total
	 **/
	public int threeOfAKindCalculator(int[] dice) {

		for (int i = 0; i < dice.length; i++) {

			int count = 0;
			for (int j = 0; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
					count++;
				}
			}
			if (count >= 3) {
				int total = 0;
				for (int j = 0; j < dice.length; j++) {
					total += dice[j];
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
	public int fourOfAKindCalculator(int[] dice) {

		for (int i = 0; i < dice.length; i++) {

			int count = 0;
			for (int j = 0; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
					count++;
				}
			}
			if (count >= 4) {
				int total = 0;
				for (int j = 0; j < dice.length; j++) {
					total += dice[j];
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
	public int fullHouseCalculator(int[] dice) {
		boolean threeOfAKind = false;
		boolean pair = false;

		for (int i = 0; i < dice.length; i++) {
			int count = 0;

			for (int j = 0; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
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
	public int smallStraightCalculator(int[] dice) {
		boolean[] number = new boolean[6];

		for (int i = 0; i < dice.length; i++) {
			number[dice[i] - 1] = true;
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
	public int largeStraightCalculator(int[] dice) {
		boolean[] number = new boolean[6];

		for (int i = 0; i < dice.length; i++) {
			number[dice[i] - 1] = true;
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
	public int yahtzeeCalculator(int[] dice) {

		for (int i = 0; i < dice.length; i++) {

			int count = 0;
			for (int j = 0; j < dice.length; j++) {
				if (dice[i] == dice[j]) {
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
	public int chanceCalculator(int[] dice) {
		int total = 0;

		for (int i = 0; i < dice.length; i++) {
			total += dice[i];
		}

		return total;
	}
}
