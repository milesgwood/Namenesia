package view;

import java.net.URL;
import java.util.ResourceBundle;

import dataTypes.Person;
import db.PeopleSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Milesgwood
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML 
    private Text actiontarget;
    @FXML 
    private TextField firstName;
    @FXML 
    private TextField middleName;
    @FXML 
    private TextField lastName;
    @FXML 
    private Button submit;
    @FXML 
    private Button reset;
    
    private DropShadow shadow = new DropShadow();

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Added");
        Person p = new Person(firstName.getText());
		p.middle = middleName.getText();
		p.lastName = lastName.getText();
		PeopleSetter.addPersonToDatabase(p);
		System.out.println("Submit Clicked");
    }
    
    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        actiontarget.setText("Reset");
        firstName.setText("");
        middleName.setText("");
        lastName.setText("");
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
    }    
    
}
