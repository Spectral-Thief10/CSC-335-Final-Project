import model.ScoreSheet;

public interface Strategy {
	public ScoreSheet.Category chooseCategory();
	public boolean[] kindRerolls(DiceSet dice,boolean[] rerolls);
	public boolean[] fullHouseRerolls(DiceSet dice,boolean[] rerolls);
	public boolean[] straightRerolls(DiceSet dice, boolean[] rerolls);
	public boolean[] yahtzeeRerolls(DiceSet dice, boolean[] rerolls);
	public boolean[] chanceRerolls(DiceSet dice, boolean[] rerolls);
	public boolean[] upperKindRerolls(DiceSet dice, boolean[] rerolls);
}
