/*
 * GUI Viewer
 */

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.DiceSet;
import model.ScoreSheet;
import model.Mode;

public class GameView extends Application {

	private static Stage primaryStage;
	private static Optional<Mode> cpuMode;
	private static AudioClip buttonPress = new AudioClip("file:UIAssets/buttonPress.mp3");
	private static AudioClip scorePress = new AudioClip("file:UIAssets/scorePress.mp3");
	private static AudioClip diceRoll = new AudioClip("file:UIAssets/diceRoll.mp3");

	@Override
	public void start(Stage arg0) throws Exception {
		cpuMode = Optional.ofNullable(null);
		primaryStage = arg0;
		primaryPage(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static void primaryPage(Stage primaryStage) {
		BorderPane BPane = new BorderPane();
		VBox root = new VBox(200);

		Text welcomeMsg = new Text("Let's play Yahtzee!!");
		welcomeMsg.setFont(Font.font("Times New Roman", FontWeight.BOLD, 100));
		welcomeMsg.setStyle("-fx-fill: #FCD060;");
		welcomeMsg.setStroke(Color.BLACK);
		welcomeMsg.setStrokeWidth(1.5);

		Button button1 = new Button("Rules");
		button1.setFont(Font.font("Times New Roman", 20));
		button1.setStyle("-fx-background-color: #FCD060;");
		button1.setPrefWidth(250);
		button1.setOnAction(e -> { buttonPress.play(); rulesPage(primaryStage);});

		Button button2 = new Button("Singleplayer");
		button2.setFont(Font.font("Times New Roman", 20));
		button2.setStyle("-fx-background-color: #FCD060;");
		button2.setPrefWidth(250);
		button2.setOnAction(e -> { buttonPress.play(); setupScreenSingle(primaryStage);});
		
		Button button3 = new Button("Multiplayer");
		button3.setFont(Font.font("Times New Roman", 20));
		button3.setStyle("-fx-background-color: #FCD060;");
		button3.setPrefWidth(250);
		button3.setOnAction(e -> { buttonPress.play(); setupScreenMulti(primaryStage);});

		VBox buttons = new VBox(30);
		buttons.getChildren().addAll(button1, button2, button3);
		buttons.setAlignment(Pos.CENTER);

		root.getChildren().addAll(welcomeMsg, buttons);
		root.setAlignment(Pos.CENTER);

		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		Scene startingPage = new Scene(BPane, 1300, 700);
		primaryStage.setScene(startingPage);

	}

	private static void rulesPage(Stage primaryStage) {
		BorderPane BPane = new BorderPane();

		Text heading = new Text("Yahtzee Rules:");
		Text rules = new Text(
				"1. Roll five dice and select the scoring category for each roll.\n2. Score categories include Full House, Three of a Kind, etc.\n"
						+ "3. Complete the scorecard by filling in all categories.\n"
						+ "4. The player with the highest score wins!");

		heading.setFont(Font.font("Times New Roman", FontWeight.BOLD, 100));
		heading.setStyle("-fx-fill: #FCD060;");
		heading.setStroke(Color.BLACK);
		heading.setStrokeWidth(1.5);

		rules.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		rules.setStyle("-fx-fill: #FFFDD0;");

		Button backButton = new Button("Back");
		backButton.setFont(Font.font("Times New Roman", 20));
		backButton.setStyle("-fx-background-color: #FCD060;");
		backButton.setPrefWidth(150);
		backButton.setPrefHeight(50);

		backButton.setOnAction(e -> { buttonPress.play(); primaryPage(primaryStage);});

		VBox rulesRoot = new VBox(20);
		rulesRoot.getChildren().addAll(heading, rules, backButton);
		rulesRoot.setAlignment(Pos.CENTER);

		BPane.setPadding(new Insets(50));
		BPane.setCenter(rulesRoot);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover;");

		Scene rulesScene = new Scene(BPane, 1300, 700);
		primaryStage.setScene(rulesScene);
		

	}

	private static void setupScreenSingle(Stage primaryStage) {
		BorderPane BPane = new BorderPane();
		VBox root = new VBox(50);
		HBox modeContainer = new HBox(20);
		
		Text modeLabel = new Text("Select CPU Difficulty");
		Button easyButton = new Button("Easy CPU");
		Button hardButton = new Button("Hard CPU");
		
		modeLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		modeLabel.setStyle("-fx-fill: #FFFDD0;");
		
		// hard cpu buton
		hardButton.setFont(Font.font("Times New Roman", 20));
		hardButton.setStyle("-fx-background-color: #FCD060;");
		hardButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				buttonPress.play();
				cpuMode = Optional.of(Mode.HARD);
				gameScreen(primaryStage);
			}
		});
		
		// easy cpu button
		easyButton.setFont(Font.font("Times New Roman", 20));
		easyButton.setStyle("-fx-background-color: #FCD060;");
		easyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				buttonPress.play();
				cpuMode = Optional.of(Mode.EASY);
				gameScreen(primaryStage);
			}
		});
		
		modeContainer.getChildren().addAll(easyButton, hardButton);
		root.getChildren().addAll(modeLabel, modeContainer);
		modeContainer.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);
		
		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		Scene setupScene = new Scene(BPane, 1300, 700);
		primaryStage.setScene(setupScene);
	}
	
	private static void setupScreenMulti(Stage primaryStage) {
		BorderPane BPane = new BorderPane();
		VBox root = new VBox(50);
		HBox buttons = new HBox(20);
		
		Text playerLabel = new Text("Select number of players");
		playerLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		playerLabel.setStyle("-fx-fill: #FFFDD0;");
		
		root.getChildren().addAll(playerLabel, buttons);
		
		for (int i = 2; i <= 4; i++) {
			Button b = new Button(i + " Player");
			b.setFont(Font.font("Times New Roman", 20));
			b.setStyle("-fx-background-color: #FCD060;");
			b.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					buttonPress.play();
					gameScreen(primaryStage);
				}
			});
			buttons.getChildren().add(b);
		}
		
		root.setAlignment(Pos.CENTER);
		buttons.setAlignment(Pos.CENTER);
		
		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		Scene setupScene = new Scene(BPane, 1300, 700);
		primaryStage.setScene(setupScene);
	}

	private static void gameScreen(Stage primaryStage) {
		BorderPane BPane = new BorderPane();

		VBox diceRoot = new VBox();
		diceRoot.setPadding(new Insets(10));
		diceRoot.setSpacing(10);
		diceRoot.setAlignment(Pos.CENTER);

		HBox diceRow = new HBox();
		diceRow.setSpacing(15);
		diceRow.setAlignment(Pos.CENTER);

		Button buttonRoll = new Button("Roll Dice");
		buttonRoll.setStyle(
				"-fx-border-color: black; -fx-border-radius: 3px; -fx-font-size: 20px; -fx-background-color: #9c9c9c;");

		buttonRoll.setOnAction(e -> {
			buttonPress.play();
		});

		diceRoot.getChildren().addAll(diceRow, buttonRoll);
		BPane.setCenter(diceRoot);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		
		Scene SceneGame = new Scene(BPane, 1300, 700);
		primaryStage.setScene(SceneGame);
	}

}
