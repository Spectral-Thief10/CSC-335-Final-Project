//Adapter Class for DiceSet GUI that implements observer
package view;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import model.Dice;
import model.ScoreSheet.Category;
import controller.GameManager;
import controller.Observer;

public class DiceSetGUI implements Observer {
	private static AudioClip diceRoll = new AudioClip("file:UIAssets/diceRoll.mp3");
	private static AudioClip buttonPress = new AudioClip("file:UIAssets/buttonPress.mp3");
	private boolean[] rerolls = {true, true, true, true, true};
	
	private HBox diceRow;
	private Button buttonRoll;
	
	private GameManager game;
	
	public DiceSetGUI(VBox root, GameManager game) {
		this.game = game;
		
		// initialize dice row
		diceRow = new HBox();
		diceRow.setSpacing(15);
		for (int i = 0; i < 5; i++) {
			Button diceButton = new Button(Integer.toString(i));
			ImageView diceFace = new ImageView(new Image("UIAssets/dice"+game.getDiceSet().get(i).VALUE+".png", 100, 100, true, false));
			diceButton.setGraphic(diceFace);
			diceButton.setPadding(new Insets(-1, -1, -1, -1));
			diceButton.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; -fx-text-fill: transparent");
			diceButton.setMaxWidth(100);	
			
			diceButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					if (game.canRoll()) {
						buttonPress.play();
						if (!rerolls[diceButton.getText().charAt(0)-'0']) {
							ColorAdjust undarken = new ColorAdjust();
							undarken.setBrightness(0);
							diceButton.setEffect(undarken);
							rerolls[diceButton.getText().charAt(0)-'0'] = true;
						} else {
							ColorAdjust darken = new ColorAdjust();
							darken.setBrightness(-0.9);
							diceButton.setEffect(darken);
							rerolls[diceButton.getText().charAt(0)-'0'] = false;
						}
					}
				}
			});
			diceRow.getChildren().add(diceButton);
		}

		// create roll button
		buttonRoll = new Button("Roll Dice");
		buttonRoll.setFont(Font.font("Times New Roman", 20));
		buttonRoll.setStyle("-fx-background-color: #FCD060;");
		buttonRoll.setOnAction(e -> {
			if (game.canRoll() && !game.isCPUTurn()) {
				diceRoll.play();
				game.updateDices(rerolls);
			}
		});
		
		root.getChildren().add(diceRow);
		root.getChildren().add(buttonRoll);
		diceRow.setAlignment(Pos.CENTER);
	}
	
	public int getId() {
		/*
		 * @return ID of the dice set observer
		 */
		return -1;
	}
	
	public void update(ArrayList<Dice> result) {
		/*
		 * Updates the dice display with new dice
		 * 
		 * @parameter result, the result of a dice roll
		 */
		
		// play sound effect
		if (diceRoll.isPlaying()) {
			diceRoll.stop();
		}
		
		diceRoll.play();
		
		// reset dice row
		for (int i = 0; i < result.size(); i++) {
			ImageView diceFace = new ImageView(new Image("UIAssets/dice"+result.get(i).VALUE+".png", 100, 100, true, false));
			Button diceButton = (Button) diceRow.getChildren().get(i);
			ColorAdjust undarken = new ColorAdjust();
			undarken.setBrightness(0);
			diceButton.setEffect(undarken);
			diceButton.setGraphic(diceFace);
		}
		
		// disable roll button if no more rolls
		if (!game.canRoll()) {
			buttonRoll.setDisable(true);
		} else {
			buttonRoll.setDisable(false);
		}
		
		rerolls = new boolean[] {true, true, true, true, true};
	}
	
	// default for adapter class
	public void update(Category category, int val) {}
	public void makeCurrentPlayer() {}
	public void removeCurrentPlayer() {}
}
