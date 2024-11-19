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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameView extends Application{
	
	private static Stage primaryStage;
	
	@Override
	public void start(Stage arg0) throws Exception {

		primaryStage = arg0;
		primaryPage(primaryStage);
		primaryStage.show();
	}
	
	private static void primaryPage(Stage primaryStage) { 
		BorderPane BPane = new BorderPane();
		VBox root = new VBox(200); 
		
		Text welcomeMsg = new Text("Let's play Yahtzee!!");   
		welcomeMsg.setFont(Font.font("Times New Roman", FontWeight.BOLD,100));
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
				slide.setStyle("-fx-tick-label-fill: black; -fx-background-color: #FCD060;" ); 	
				
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
				playerBox.getChildren().addAll(slide,setPlayers);
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
		        
		        
		        radio.getChildren().addAll(label,easyButton,difficultButton, setMode);
		        root.getChildren().addAll(radio);
		        
		    }
		});
		
		Button button4 = new Button("Start");
		button4.setFont(Font.font("Times New Roman", 20));
		button4.setStyle("-fx-background-color: #FCD060;");
		button4.setPrefWidth(100);  
		button4.setPrefHeight(50);
		
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
        Text rules = new Text ("1. Roll five dice and select the scoring category for each roll.\n2. Score categories include Full House, Three of a Kind, etc.\n"
                + "3. Complete the scorecard by filling in all categories.\n"
                + "4. The player with the highest score wins!");
         
        heading.setFont(Font.font("Times New Roman", FontWeight.BOLD,100));
 		heading.setStyle("-fx-fill: #FCD060;");
 		heading.setStroke(Color.BLACK);
 		heading.setStrokeWidth(1.5);
 		
 		rules.setFont(Font.font("Times New Roman", FontWeight.BOLD,40));
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
	
}
