package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import db.AddImage;
import db.PersonBus;
import db.TagSearchEngine;
import javafx.stage.FileChooser;
import view.StageChanger;

public class ProfileController implements Initializable {
	
	
	DropShadow shadow = new DropShadow();
	
	@FXML 
    private Label name;
	
	@FXML
	private GridPane grid;
	
    @FXML 
    private ImageView image;
    
    @FXML 
    private Button addTags;
    
    @FXML
    private void handlePicturePressed(MouseEvent event) throws FileNotFoundException, IOException, SQLException
    {
    	final FileChooser fileChooser = new FileChooser();
    	File file = fileChooser.showOpenDialog(StageChanger.getMainStage());
        if (file != null) {
        	AddImage.setImage(file.getAbsolutePath(), PersonBus.id);
        	image.setImage(AddImage.getImage(PersonBus.id));
        }
    }
    
    @FXML
    private void handleAddTagsButton(ActionEvent event) 
    {
    	StageChanger ms = new StageChanger();
    	ms.addTagsView();
    }
    
    
    
    /**
     * Initializes the event handlers for the buttons when they are scrolled over.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(PersonBus.wholeName);
        try {
			image.setImage(AddImage.getImage(PersonBus.id));
			//ArrayList<String> tags = TagSearchEngine.getTagsForOnePersonID(27);
			ArrayList<String> tags = TagSearchEngine.getTagsForOnePersonID(PersonBus.id);
			for(int row  = 0 ; row < tags.size() ; row++)
			{
				Button delete = new Button("Delete");
				Label label = new Label(tags.get(row));
				delete.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->
				{
		            delete.setEffect(shadow);
	            });
				delete.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)->
				{
		            delete.setEffect(null); 
	            });
				delete.setOnAction((ActionEvent e)-> {
					TagSearchEngine.deleteString(label.getText(), PersonBus.id);
					//TagSearchEngine.deleteString(label.getText(), 27);
					grid.getChildren().remove(delete);
					grid.getChildren().remove(label);
				});
				grid.addRow(row);
				grid.add(label, 0 , row);
				grid.add(delete, 1, row);
			}
			
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }     
}
