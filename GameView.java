/*
 * GUI Viewer
 */
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
import javafx.stage.Stage;

public class GameView extends Application {

	private static Stage primaryStage;
	private static Calculator calculator = new Calculator();

	@Override
	public void start(Stage arg0) throws Exception {

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
		button1.setPrefWidth(100);
		button1.setPrefHeight(50);
		button1.setOnAction(e -> rulesPage(primaryStage));

		Button button2 = new Button("Players");
		button2.setFont(Font.font("Times New Roman", 20));
		button2.setStyle("-fx-background-color: #FCD060;");
		button2.setPrefWidth(100);
		button2.setPrefHeight(50);
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Slider slide = new Slider(1, 5, 1);
				slide.setOrientation(Orientation.HORIZONTAL);
				slide.setMajorTickUnit(1);
				slide.setSnapToTicks(true);
				slide.setMinorTickCount(0);
				slide.setBlockIncrement(1);
				slide.setShowTickLabels(true);
				slide.setMaxWidth(300);
				slide.setStyle("-fx-tick-label-fill: black; -fx-background-color: #FCD060;");

				Button setPlayers = new Button("Set");
				setPlayers.setFont(Font.font("Times New Roman", 20));
				setPlayers.setStyle("-fx-background-color: #FCD060;");
				setPlayers.setPrefWidth(100);
				setPlayers.setPrefHeight(50);

				setPlayers.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {

						// add players
						primaryPage(primaryStage);
					}
				});

				HBox playerBox = new HBox(30);
				playerBox.getChildren().addAll(slide, setPlayers);
				playerBox.setAlignment(Pos.CENTER);
				root.getChildren().addAll(playerBox);
			}
		});

		Button button3 = new Button("Mode");
		button3.setFont(Font.font("Times New Roman", 20));
		button3.setStyle("-fx-background-color: #FCD060;");
		button3.setPrefWidth(100);
		button3.setPrefHeight(50);

		button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Label label = new Label("Select Game Mode:");
				label.setStyle("-fx-text-fill: #FCD060;");
				label.setFont(Font.font("Times New Roman", 20));

				RadioButton easyButton = new RadioButton("Easy");
				easyButton.setFont(Font.font("Times New Roman", 20));
				easyButton.setStyle("-fx-text-fill: #FCD060;");

				RadioButton difficultButton = new RadioButton("Difficult");
				difficultButton.setFont(Font.font("Times New Roman", 20));
				difficultButton.setStyle("-fx-text-fill: #FCD060;");

				ToggleGroup modeGroup = new ToggleGroup();
				easyButton.setToggleGroup(modeGroup);
				difficultButton.setToggleGroup(modeGroup);
				easyButton.setSelected(true);

				HBox radio = new HBox(100);
				radio.setAlignment(Pos.CENTER);

				Button setMode = new Button("Set Mode");
				setMode.setFont(Font.font("Times New Roman", 15));
				setMode.setStyle("-fx-background-color: #FCD060;");
				setMode.setPrefWidth(100);
				setMode.setPrefHeight(50);

				setMode.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {

						// add mode
						primaryPage(primaryStage);
					}
				});

				radio.getChildren().addAll(label, easyButton, difficultButton, setMode);
				root.getChildren().addAll(radio);

			}
		});

		Button button4 = new Button("Start");
		button4.setFont(Font.font("Times New Roman", 20));
		button4.setStyle("-fx-background-color: #FCD060;");
		button4.setPrefWidth(100);
		button4.setPrefHeight(50);
		button4.setOnAction(e -> gameScreen(primaryStage));

		HBox buttons = new HBox(20);
		buttons.getChildren().addAll(button1, button2, button3, button4);
		buttons.setAlignment(Pos.CENTER);

		root.getChildren().addAll(welcomeMsg, buttons);
		root.setAlignment(Pos.BASELINE_CENTER);

		BPane.setPadding(new Insets(50));
		BPane.setCenter(root);
		BPane.setStyle("-fx-background-image: url('woodGrain.jpeg'); -fx-background-size: cover; ");
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

		Button backButton = new Button("Main");
		backButton.setFont(Font.font("Times New Roman", 20));
		backButton.setStyle("-fx-background-color: #FCD060;");
		backButton.setPrefWidth(150);
		backButton.setPrefHeight(50);

		backButton.setOnAction(e -> primaryPage(primaryStage));

		VBox rulesRoot = new VBox(20);
		rulesRoot.getChildren().addAll(heading, rules, backButton);
		rulesRoot.setAlignment(Pos.CENTER);

		BPane.setPadding(new Insets(50));
		BPane.setCenter(rulesRoot);
		BPane.setStyle("-fx-background-image: url('woodGrain.jpeg'); -fx-background-size: cover;");

		Scene rulesScene = new Scene(BPane, 1300, 700);
		primaryStage.setScene(rulesScene);
	}

	private static void gameScreen(Stage primaryStage) {
		BorderPane BPane = new BorderPane();

		GridPane Sheet = new GridPane();
		Sheet.setHgap(30);
		Sheet.setVgap(0);
		Sheet.setStyle(
				"-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10;-fx-background-color: #9c9c9c;");

		Label category = new Label("Category");
		category.setStyle("-fx-font-weight: bold;");

		Label you = new Label("You");
		you.setStyle("-fx-font-weight: bold;");

		Label player1 = new Label("Player 1");
		player1.setStyle("-fx-font-weight: bold;");

		Sheet.add(category, 0, 0);
		Sheet.add(you, 1, 0);
		Sheet.add(player1, 2, 0);

		String[] categories = { "Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Sum", "Bonus", "Three of a kind",
				"Four of a kind", "Full House", "Small straight", "Large straight", "Chance", "YAHTZEE",
				"TOTAL SCORE" };

		Label sum = new Label("0");
		sum.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-min-width: 50px; -fx-text-alignment: center;");

		Label bonus = new Label();
		bonus.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-min-width: 50px; -fx-text-alignment: center;");

		ScoreSheet scoreSheet = new ScoreSheet();
		DiceSet diceSet = new DiceSet();
		boolean[] hold = new boolean[5];

		for (int i = 0; i < categories.length; i++) {
			Label categoryType = new Label(categories[i]);
			categoryType.setStyle("-fx-padding: 5; -fx-font-size: 14px;");

			Label yourScore = new Label();
			yourScore.setStyle(
					"-fx-border-color: black; -fx-padding: 5; -fx-min-width: 50px; -fx-text-alignment: center;");

			final int index = i;

			yourScore.setOnMouseClicked(e -> {
				if (yourScore.getText().isEmpty() && diceSet.hasResult()) {
					int[] diceResults = new int[diceSet.getResult().size()];
					for (int z = 0; z < diceSet.getResult().size(); ++z) {
						diceResults[z] = diceSet.getResult().get(z).VALUE;
					}

					boolean updated = false;

					switch (categories[index]) {
					case "Ones":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.ONE,
								calculator.upperSectionCalculator(diceResults, 1));
						break;
					case "Twos":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.TWO,
								calculator.upperSectionCalculator(diceResults, 2));
						break;
					case "Threes":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.THREE,
								calculator.upperSectionCalculator(diceResults, 3));
						break;
					case "Fours":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR,
								calculator.upperSectionCalculator(diceResults, 4));
						break;
					case "Fives":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.FIVE,
								calculator.upperSectionCalculator(diceResults, 5));
						break;
					case "Sixes":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.SIXE,
								calculator.upperSectionCalculator(diceResults, 6));
						break;
					case "Three of a kind":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.THREE_OF_A_KIND,
								calculator.threeOfAKindCalculator(diceResults));
						break;
					case "Four of a kind":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.FOUR_OF_A_KIND,
								calculator.fourOfAKindCalculator(diceResults));
						break;
					case "Full House":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.FULL_HOUSE,
								calculator.fullHouseCalculator(diceResults));
						break;
					case "Small straight":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.SMALL_STRAIGHT,
								calculator.smallStraightCalculator(diceResults));
						break;
					case "Large straight":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.LARGE_STRAIGHT,
								calculator.largeStraightCalculator(diceResults));
						break;
					case "Chance":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.CHANCE,
								calculator.chanceCalculator(diceResults));
						break;
					case "YAHTZEE":
						updated = scoreSheet.setScoreCategory(ScoreSheet.Category.YAHTZEE,
								calculator.yahtzeeCalculator(diceResults));
						break;
					}

					if (updated) {
						yourScore.setText(
								String.valueOf(scoreSheet.getScoreCategory(ScoreSheet.Category.values()[index])));
						diceSet.reset();

						int sumUS = scoreSheet.totalScoreForUpperSection();
						sum.setText(String.valueOf(sumUS));

						if (sumUS >= 63) {
							bonus.setText("35");
						} else {
							bonus.setText("0");
						}
					}
				}
			});

			Label playerScore = new Label();
			playerScore.setStyle(
					"-fx-border-color: black; -fx-padding: 5; -fx-min-width: 50px; -fx-text-alignment: center;");

			Sheet.add(categoryType, 0, i + 1);
			Sheet.add(yourScore, 1, i + 1);
			Sheet.add(playerScore, 2, i + 1);

			if (categories[i].equals("Sum")) {
				Sheet.add(sum, 1, i + 1);
			} else if (categories[i].equals("Bonus")) {
				Sheet.add(bonus, 1, i + 1);
			}
		}

		BorderPane.setMargin(Sheet, new Insets(0, 0, 100, 50));
		BPane.setLeft(Sheet);

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
			if (diceSet.canRoll()) {
				boolean[] roll = new boolean[5];
				for (int i = 0; i < hold.length; i++) {
					roll[i] = !hold[i];
				}
				diceSet.rollDiceAt(roll);

				diceRow.getChildren().clear();
				for (int i = 0; i < diceSet.getResult().size(); i++) {
					Label dLabel = new Label(String.valueOf(diceSet.getResult().get(i).VALUE));
					if (hold[i]) {
						dLabel.setStyle("-fx-font-size: 20px; -fx-background-color: #9c9c9c;; -fx-padding: 5px;");
					} else {
						dLabel.setStyle("-fx-font-size: 30px; -fx-border-color: black; -fx-padding: 10px;");
					}

					final int dIndex = i;
					dLabel.setOnMouseClicked(event -> {
						hold[dIndex] = !hold[dIndex];
						if (hold[dIndex]) {
							dLabel.setStyle("-fx-font-size: 20px; -fx-background-color: #9c9c9c;; -fx-padding: 5px;");
						} else {
							dLabel.setStyle("-fx-font-size: 30px; -fx-border-color: black; -fx-padding: 10px;");
						}
					});
					diceRow.getChildren().add(dLabel);
				}

			}
		});

		diceRoot.getChildren().addAll(diceRow, buttonRoll);
		BPane.setCenter(diceRoot);

		Scene SceneGame = new Scene(BPane, 800, 550);
		primaryStage.setScene(SceneGame);
	}

}
