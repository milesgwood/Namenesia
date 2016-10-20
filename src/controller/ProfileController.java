package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import db.AddImage;
import db.PersonBus;



import javafx.stage.FileChooser;
import view.MainStage;

public class ProfileController implements Initializable {
	
	
	
	@FXML 
    private Label name;
	
    @FXML 
    private ImageView image;
    
    
    @FXML
    private void handlePicturePressed(MouseEvent event) throws FileNotFoundException, IOException, SQLException
    {
    	final FileChooser fileChooser = new FileChooser();
    	File file = fileChooser.showOpenDialog(MainStage.getMainStage());
        if (file != null) {
        	AddImage.setImage(file.getAbsolutePath(), PersonBus.id);
        	image.setImage(AddImage.getImage(PersonBus.id));
        }
    }
    
    /**
     * Initializes the event handlers for the buttons when they are scrolled over.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(PersonBus.wholeName);
    }     
}
