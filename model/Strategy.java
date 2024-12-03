package model;

import java.util.ArrayList;

public interface Strategy {
	public ScoreSheet.Category chooseCategory(ArrayList<Dice> dice);
	public boolean[] kindRerolls(ArrayList<Dice> dice,boolean[] rerolls);
	public boolean[] fullHouseRerolls(ArrayList<Dice>  dice,boolean[] rerolls);
	public boolean[] straightRerolls(ArrayList<Dice>  dice, boolean[] rerolls);
	public boolean[] yahtzeeRerolls(ArrayList<Dice>  dice, boolean[] rerolls);
	public boolean[] chanceRerolls(ArrayList<Dice>  dice, boolean[] rerolls);
	public boolean[] upperKindRerolls(ArrayList<Dice>  dice, boolean[] rerolls);
}
