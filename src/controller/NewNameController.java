package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dataTypes.Person;
import db.PeopleSetter;
import db.PersonBus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mvvm.NewNameViewModel;

/**
 *
 * @author Milesgwood
 */
public class NewNameController implements Initializable {
    
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
        
        NewNameViewModel nnvm = new NewNameViewModel();
        //Connect the ViewModel
        firstName.textProperty().bindBidirectional(nnvm.firstNameProperty());
        middleName.textProperty().bindBidirectional(nnvm.middleNameProperty());
        lastName.textProperty().bindBidirectional(nnvm.lastNameProperty());
    }
}
