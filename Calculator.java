
public class Calculator {

	
	public int upperSectionCalculator(int[] dice, int categoryval) {
		int total = 0;

		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == categoryval) {
				total += dice[i];
			}
		}

		return total;
	}

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

	public int chanceCalculator(int[] dice) {
		int total = 0;

		for (int i = 0; i < dice.length; i++) {
			total += dice[i];
		}

		return total;
	}
}
