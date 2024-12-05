//GUI for the Scoresheet

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import model.Dice;
import model.ScoreSheet;
import model.ScoreSheet.Category;

public class ScoreSheetGUI implements Observer {

	private HashMap<ScoreSheet.Category, Button> map = new HashMap<>();
	private Label heading;
	private GridPane gridPane = new GridPane();
	private int idVal;

	private GameManager control;

	private static AudioClip scorePress = new AudioClip("file:UIAssets/scorePress.mp3");

	// Note: you can change the hbox to the most convenient layout. Thought it might
	// give be easier to manipulate than this.add()

	public ScoreSheetGUI( HBox hbox, String playerName, int id, GameManager gm) {
		 /* Constructor
		 * 
		 * @param hbox: The hbox in which the scoresheet is to be displayed
		 * 
		 * @param playerName: The playerName to display at the top of the screen
		 * 
		 * @param id: The id to identify the observer with
		 */

		control = gm;
		idVal = id;
		heading = new Label(playerName);
		heading.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: black; -fx-font-family: 'Courier New';");
		heading.setMinWidth(200);
		gridPane.add(heading, 0, 0);

		int row = 1;
		for (ScoreSheet.Category category : ScoreSheet.Category.values()) {
			Button categoryButton = new Button(category.toString().replace("_", " ") + ": ");
			categoryButton.setMinWidth(200);

			// change the colours to fit the theme
			if (row % 2 == 0) {
				categoryButton.setStyle(
						"-fx-padding: 10px; -fx-font-weight: bold; -fx-font-size: 15px; -fx-background-color: white; -fx-font-family: 'Courier New';");
			} else {
				categoryButton.setStyle(
						"-fx-padding: 10px;-fx-font-weight: bold;  -fx-font-size: 15px; -fx-background-color: #FCD060; -fx-font-family: 'Courier New';");

			}

			categoryButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					if (control.getPlayerIndex() == idVal) {
						
						boolean scoreflag = control.updateScore(category);

						if (scoreflag) {
							scorePress.play();
							boolean flag = control.nextPlayer();
							if (!flag) {
								// show endScreen
								System.out.println("end game!!");
							}
						}

					}

				}
			});

			map.put(category, categoryButton);
			gridPane.add(categoryButton, 0, row);
			row++;
		}

		gridPane.setGridLinesVisible(true);
		hbox.getChildren().add(gridPane);
	}

	@Override
	public void update(ScoreSheet.Category category, int val) {
		/*
		 * It updates the text label for that scoresheet category with the new value val
		 * 
		 * @param category The category whose score label should be updated
		 * 
		 * @param val The new value to be updated with
		 */
		Button label = map.get(category);
		label.setText(category.toString().replace("_", " ") + ": " + val);

	}

	public void makeCurrentPlayer() {
		heading.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: green; -fx-font-family: 'Courier New';");

	}

	public void removeCurrentPlayer() {
		heading.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: black; -fx-font-family: 'Courier New';");

	}

	@Override
	public int getId() {
		return idVal;
	}
	
	public void update(ArrayList<Dice> result) {}

}
