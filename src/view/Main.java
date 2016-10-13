package view;

import dataTypes.Person;
import db.PeopleSetter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	public void start(Stage primaryStage) {
		try {
			//Sets the grid and alignment as well as the gaps
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			
			grid.setPadding(new Insets(20,20,20,20));
			
			Scene scene = new Scene(grid, 300, 275);
			Text scenetitle = new Text("What's the name?");
			//Sets the font and size
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			Label firstLabel = new Label("First");
			TextField firstText = new TextField();
			Labeled middleLabel = new Label("Middle");
			TextField middleText = new TextField();
			Label lastLabel = new Label("Last");
			TextField lastText = new TextField();
			
			//puts the title in col-0, row-0, spans 2 col and only 1 row
			grid.add(scenetitle, 	0, 0, 2, 1);
			grid.add(firstLabel, 	0, 1);
			grid.add(middleLabel, 	0, 2);
			grid.add(lastLabel, 	0, 3);

			grid.add(firstText, 	1, 1);
			grid.add(middleText, 	1, 2);
			grid.add(lastText,	 	1, 3);
			
			//Makes the grid visible
			//grid.setGridLinesVisible(true);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Adds a button in a new layout
			Button btn = new Button("Add Person");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn);
			grid.add(hbBtn, 1, 5);
			
			//Creates a shadow over the button when it is hovered over
			DropShadow shadow = new DropShadow();
			btn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
	            btn.setEffect(shadow); });
			btn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)->{
	            btn.setEffect(null); });
			
			//Makes the button add the name to the database when it is clicked
			btn.setOnAction((ActionEvent e) -> {
				
			});

			primaryStage.setTitle("Namenesia");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
