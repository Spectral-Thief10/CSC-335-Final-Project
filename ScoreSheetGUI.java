//GUI for the Scoresheet

import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.ScoreSheet;

public class ScoreSheetGUI implements Observer{
	
	private HashMap<ScoreSheet.Category, Label> map = new HashMap<>();
	private GridPane gridPane = new GridPane();
	private int idVal;
	
	//Note: you can change the hbox to the most convenient layout. Thought it might give be easier to manipulate than this.add()  	
	public ScoreSheetGUI(HBox hbox, String playerName, int id) {
		/*
		 * Constructor 
		 * 
		 * @param hbox: The hbox in which the scoresheet is to be displayed 
		 * @param playerName: The playerName to display at the top of the screen
		 * @param id: The id to identify the observer with
		 */		
		idVal = id;
        Label heading = new Label(playerName);
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px; -fx-background-color: black; -fx-font-family: 'Courier New';");
        heading.setMinWidth(200);
        gridPane.add(heading, 0, 0); 
        
        int row = 1; 
        for (ScoreSheet.Category category : ScoreSheet.Category.values()) {
            Label categoryLabel = new Label(category.toString().replace("_", " ") + ": 0"); 
            categoryLabel.setMinWidth(200);
            
            //change the colours to fit the theme
            if(row%2==0) {
                categoryLabel.setStyle("-fx-padding: 10px; -fx-font-weight: bold; -fx-font-size: 15px; -fx-background-color: white; -fx-font-family: 'Courier New';");
            }
            else {
                categoryLabel.setStyle("-fx-padding: 10px;-fx-font-weight: bold;  -fx-font-size: 15px; -fx-background-color: lightblue; -fx-font-family: 'Courier New';");

            }
            map.put(category, categoryLabel);
            gridPane.add(categoryLabel, 0, row); 
            row++; 
        }
        
        gridPane.setGridLinesVisible(true);
        hbox.getChildren().add(gridPane);		
	}
	
	@Override
	public void update(ScoreSheet.Category category, String val) {
		/*
		 * It updates the text label for that scoresheet category with the new value val
		 * 
		 * @param category The category whose score label should be updated
		 * @param val The new value to be updated with
		 */
		 Label label = map.get(category);
		 label.setText(category + ": "+val);
		
	}

	@Override
	public int getId() {
		return idVal;
	}
	
	

}


