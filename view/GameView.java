/*
 * GUI Viewer
 */
package view;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.Mode;
import controller.GameManager;


public class GameView extends Application {

	private static Stage primaryStage;
	private static AudioClip buttonPress = new AudioClip("file:UIAssets/buttonPress.mp3");
	private static AudioClip titleMusic = new AudioClip("file:UIAssets/titleMusic.mp3");
	private static AudioClip endMusic = new AudioClip("file:UIAssets/endMusic.mp3");
	private static HBox scoreRoot;

	private static int playerNumber = 0;
	private static Optional<Mode> mode = Optional.ofNullable(null);

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = arg0;
		primaryPage();
		primaryStage.show();
		scoreRoot = new HBox();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static void primaryPage() {
		BorderPane BPane = new BorderPane();
		VBox root = new VBox(200);
		
		playerNumber = 0;
		mode = Optional.ofNullable(null);

		Text welcomeMsg = new Text("Let's play Yahtzee!!");
		welcomeMsg.setFont(Font.font("Times New Roman", FontWeight.BOLD, 100));
		welcomeMsg.setStyle("-fx-fill: #FCD060;");
		welcomeMsg.setStroke(Color.BLACK);
		welcomeMsg.setStrokeWidth(1.5);

		Button button1 = new Button("Rules");
		button1.setFont(Font.font("Times New Roman", 20));
		button1.setStyle("-fx-background-color: #FCD060;");
		button1.setPrefWidth(250);
		button1.setOnAction(e -> { buttonPress.play(); rulesPage();}); 

		Button button2 = new Button("Singleplayer");
		button2.setFont(Font.font("Times New Roman", 20));
		button2.setStyle("-fx-background-color: #FCD060;");
		button2.setPrefWidth(250);
		button2.setOnAction(e -> { buttonPress.play(); titleMusic.stop(); setupScreenSingle();});
		
		Button button3 = new Button("Multiplayer");
		button3.setFont(Font.font("Times New Roman", 20));
		button3.setStyle("-fx-background-color: #FCD060;");
		button3.setPrefWidth(250);
		button3.setOnAction(e -> { buttonPress.play(); titleMusic.stop(); setupScreenMulti();});

		VBox buttons = new VBox(30);
		buttons.getChildren().addAll(button1, button2, button3);
		buttons.setAlignment(Pos.CENTER);

		root.getChildren().addAll(welcomeMsg, buttons);
		root.setAlignment(Pos.CENTER);

		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		Scene startingPage = new Scene(BPane, 1300, 900);
		primaryStage.setScene(startingPage);

		titleMusic.setVolume(0.45);
		if (!titleMusic.isPlaying()) titleMusic.play();
	}

	private static void rulesPage() {
		BorderPane BPane = new BorderPane();

		Text heading = new Text("Yahtzee Rules:");
		Text rules = new Text(
				"1. Roll five dice and select the scoring category for each roll.\n2. Pick which dice to keep and reroll the others for a better result.\n"
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

		backButton.setOnAction(e -> { buttonPress.play(); primaryPage();});

		VBox rulesRoot = new VBox(20);
		rulesRoot.getChildren().addAll(heading, rules, backButton);
		rulesRoot.setAlignment(Pos.CENTER);

		BPane.setPadding(new Insets(50));
		BPane.setCenter(rulesRoot);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover;");

		Scene rulesScene = new Scene(BPane, 1300,  900);
		primaryStage.setScene(rulesScene);
		

	}

	private static void setupScreenSingle() {
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
				mode = Optional.ofNullable(Mode.HARD);
				gameScreen(new GameManager(1, Mode.HARD));
			}
		});
		
		// easy cpu button
		easyButton.setFont(Font.font("Times New Roman", 20));
		easyButton.setStyle("-fx-background-color: #FCD060;");
		easyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				buttonPress.play();
				mode = Optional.ofNullable(Mode.EASY);
				gameScreen(new GameManager(1, Mode.EASY));
			}
		});
		
		modeContainer.getChildren().addAll(easyButton, hardButton);
		root.getChildren().addAll(modeLabel, modeContainer);
		modeContainer.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);
		
		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		Scene setupScene = new Scene(BPane, 1300,  900);
		primaryStage.setScene(setupScene);
	}
	
	private static void setupScreenMulti() {
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
					playerNumber = b.getText().charAt(0) - '0';
					gameScreen(new GameManager(playerNumber));
				}
			});
			buttons.getChildren().add(b);
		}
		
		root.setAlignment(Pos.CENTER);
		buttons.setAlignment(Pos.CENTER);
		
		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		Scene setupScene = new Scene(BPane, 1300, 900);
		primaryStage.setScene(setupScene);
	}

	private static void gameScreen(GameManager game) {
		BorderPane BPane = new BorderPane();
		GridPane root = new GridPane();
		
		// configure score sheet
		scoreRoot = new HBox();
		scoreRoot.setAlignment(Pos.CENTER);
		root.add(scoreRoot, 0, 0);
		if (mode.isEmpty()) {
			for (int i = 1; i <= game.getActivePlayers(); i++) {
				game.registerObserver(i, new ScoreSheetGUI(scoreRoot, "Player " + i, i, game));
			}
		} else {
			game.registerObserver(1, new ScoreSheetGUI(scoreRoot, "You", 1, game));
			game.registerObserver(2, new ScoreSheetGUI(scoreRoot, "Computer", 2, game));
		}
	

		/* DICE DISPLAY LOGIC */
		VBox diceRoot = new VBox();
		diceRoot.setPadding(new Insets(10));
		diceRoot.setSpacing(10);
		diceRoot.setAlignment(Pos.CENTER);
		
		game.registerObserver(-1, new DiceSetGUI(diceRoot, game));
		/* DICE DISPLAY LOGIC */
		
		root.setPadding(new Insets(20));
		root.setVgap(30);
		root.add(diceRoot, 0, 1);
		root.setAlignment(Pos.CENTER);
		
		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		
		Scene SceneGame = new Scene(BPane, 1300, 900);
		primaryStage.setScene(SceneGame);
		
		game.startsGame();
	}
	
	public static void endScreen(GameManager game) {
		BorderPane BPane = new BorderPane();
		VBox root = new VBox(20);
		
		HBox buttonBar = new HBox(20);
		
		Button button1 = new Button("Main Menu");
		button1.setFont(Font.font("Times New Roman", 20));
		button1.setStyle("-fx-background-color: #FCD060;");
		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				buttonPress.play();
				endMusic.stop();
				primaryPage();
			}
		});
		
		Button button2 = new Button("Play Again");
		button2.setFont(Font.font("Times New Roman", 20));
		button2.setStyle("-fx-background-color: #FCD060;");
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				buttonPress.play();
				
				if (mode.isPresent()) {
					gameScreen(new GameManager(1, mode.get()));
				} else {
					gameScreen(new GameManager(playerNumber));
				}

				endMusic.stop();
			}
		});
		
		buttonBar.getChildren().addAll(button1, button2);
		
		Text winnerLabel = new Text();
		if (mode.isPresent()) {
			if (game.getWinner() == 2) {
				winnerLabel.setText("The computer wins the game!");
			} else {
				winnerLabel.setText("You win the game!");
			}
		} else {
			winnerLabel.setText("Player " + game.getWinner() + " wins the game!");
		}
		
		winnerLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		winnerLabel.setStyle("-fx-fill: #FFFDD0;");
		
		root.getChildren().addAll(scoreRoot, winnerLabel, buttonBar);
		root.setAlignment(Pos.CENTER);
		buttonBar.setAlignment(Pos.CENTER);

		BPane.setPadding(new Insets(20));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('UIAssets/woodGrain.jpeg'); -fx-background-size: cover; ");
		
		Scene SceneGame = new Scene(BPane, 1300,  900);
		primaryStage.setScene(SceneGame);
		
		endMusic.setVolume(0.1);
		if (!endMusic.isPlaying()) endMusic.play();
	}

}