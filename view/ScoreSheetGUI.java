//Adapter Class for Scoresheet GUI that implements observer
package view;

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
import controller.GameManager;
import controller.Observer;

public class ScoreSheetGUI implements Observer {

	private HashMap<ScoreSheet.Category, Button> map = new HashMap<>();
	private Label heading;
	private Label bonus;
	private Label total;
	private GridPane gridPane = new GridPane();
	private int idVal;

	private GameManager control;

	private static AudioClip scorePress = new AudioClip("file:UIAssets/scorePress.mp3");

	public ScoreSheetGUI( HBox hbox, String playerName, int id, GameManager gm) {
		 /* Constructor
		 * 
		 * @param hbox: The hbox in which the scoresheet is to be displayed
		 * 
		 * @param playerName: The playerName to display at the top of the screen
		 * 
		 * @param id: The id to identify the observer with
		 * 
		 * @param gm: The game manager object
		 */

		control = gm;
		idVal = id;
		
		//heading for player name
		heading = new Label(playerName);
		heading.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: black; -fx-font-family: 'Courier New';");
		heading.setMinWidth(200);
		gridPane.add(heading, 0, 0);

		//creating buttons for each category
		int row = 1;
		for (ScoreSheet.Category category : ScoreSheet.Category.values()) {
			Button categoryButton = new Button(category.toString().replace("_", " ") + ": ");
			categoryButton.setMinWidth(200);

			//alternating colours for buttons
			if (row % 2 == 0) {
				categoryButton.setStyle(
						"-fx-padding: 10px; -fx-font-weight: bold; -fx-font-size: 15px; -fx-background-color: white; -fx-font-family: 'Courier New';");
			} else {
				categoryButton.setStyle(
						"-fx-padding: 10px;-fx-font-weight: bold;  -fx-font-size: 15px; -fx-background-color: #FCD060; -fx-font-family: 'Courier New';");

			}

			//validating and updating player score when button is pressed
			categoryButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					if (control.getPlayerIndex() == idVal) {
						
						boolean scoreflag = control.updateScore(category);

						if (scoreflag) {
							scorePress.play();
							boolean playerFlag = control.nextPlayer();
							if (!playerFlag) {
								removeCurrentPlayer();
								GameView.endScreen(gm);
							}
						}

					}

				}
			});

			map.put(category, categoryButton);
			gridPane.add(categoryButton, 0, row);
			row++;
			if (category == ScoreSheet.Category.SIX) {
				bonus = new Label("BONUS: 0");
				bonus.setStyle(
						"-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: black; -fx-font-family: 'Courier New';");
				bonus.setMinWidth(200);
				gridPane.add(bonus, 0, row);
			}
			row++;
		}
		
		total = new Label("GRAND TOTAL: 0");
		total.setStyle(
				"-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: black; -fx-font-family: 'Courier New';");
		total.setMinWidth(200);
		gridPane.add(total, 0, row);

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
		if (control.getCurrentBonus()) {
			bonus.setText("BONUS: 35");
		}
		total.setText("GRAND TOTAL: " + control.getCurrentTotalScore());
	}

	public void makeCurrentPlayer() {
		/*
		 * Makes the scorecard heading green to indicate current player
		 */
		heading.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: green; -fx-font-family: 'Courier New';");

	}

	public void removeCurrentPlayer() {
		/*
		 * Makes the scorecard heading black to indicate not currently playing
		 */
		heading.setStyle(
				"-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: black; -fx-font-family: 'Courier New';");
	}

	@Override
	public int getId() {
		//getter method
		return idVal;
	}
	
	//default implementation for adapter class
	public void update(ArrayList<Dice> result) {}

}
