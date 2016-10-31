package controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import db.PeopleSetter;
import db.PersonBus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class AddTagsController implements Initializable{
	
	private DropShadow shadow = new DropShadow();

    @FXML 
    private Button submit;
    @FXML 
    private Button reset;
    @FXML 
    private TextField tags;
    @FXML 
    private Label name;
    @FXML 
    private Label actiontarget;
    
    
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws Exception {
    	List<String> tagsList = Arrays.asList(tags.getText().split(","));
		PeopleSetter.insertTags(PersonBus.id, tagsList);
		System.out.println("Info Added");
    }
    
    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        actiontarget.setText("Reset");
        tags.setText("");
		System.out.println("Reset Clicked");
    }
	
	
	/**
     * Initializes the event handlers for the buttons when they are scrolled over.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submit.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{ 
        	submit.setEffect(shadow); });
        submit.addEventHandler(MouseEvent.MOUSE_EXITED,  (MouseEvent e)->{ 
        	submit.setEffect(null); });
        reset.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{ 
        	reset.setEffect(shadow); });
        reset.addEventHandler(MouseEvent.MOUSE_EXITED,  (MouseEvent e)->{ 
        	reset.setEffect(null); });
        name.setText(db.PersonBus.wholeName);
    }    
}
