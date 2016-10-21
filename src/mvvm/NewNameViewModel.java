package mvvm;

import java.awt.event.ActionListener;

import dataTypes.Person;
import db.PeopleSetter;
import db.PersonBus;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewNameViewModel {
	
	/*
	private final StringProperty userName = new SimpleStringProperty();
	private final StringProperty password = new SimpleStringProperty();
	private final ReadOnlyBooleanWrapper loginPossible = new ReadOnlyBooleanWrapper();

	public LoginViewModel() {
	                //Logic to check, whether the login is possible or not
			loginPossible.bind(userName.isNotEmpty().and(password.isNotEmpty()));
		}

	public StringProperty userNameProperty() {
		return userName;
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public ReadOnlyBooleanProperty isLoginPossibleProperty() {
		return loginPossible.getReadOnlyProperty();
	}
	*/
	
	private final StringProperty first = new SimpleStringProperty();
	private final StringProperty middle = new SimpleStringProperty();
	private final StringProperty last = new SimpleStringProperty();
	
	//This would be where name validation would occour - write tests for validation
	public NewNameViewModel()
	{
	}
	
	public StringProperty firstNameProperty()
	{
		return first;
	}
	
	public StringProperty middleNameProperty()
	{
		return middle;
	}
	
	public StringProperty lastNameProperty()
	{
		return last;
	}

	@FXML
    private void handleSubmitButtonAction(ActionEvent event) throws Exception {
        //actiontarget.setText("Added");
        Person p = new Person(firstNameProperty().get());
		p.middle = middleNameProperty().get();
		p.lastName = lastNameProperty().get();
		
		int pidInDatabase  = PeopleSetter.addPersonToDatabase(p);
		PersonBus.setPerson(p, pidInDatabase);
		System.out.println("Submit Clicked");
		
		//Forward the stage to the next view where tags can be added
		Parent root = FXMLLoader.load(getClass().getResource("../view/AddTags.fxml"));
		Stage appStage = (Stage) ((Button)event.getSource()).getScene().getWindow();
		Scene addTagScene = new Scene(root);
		addTagScene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
		appStage.setScene(addTagScene);
		appStage.show();
    }
    
	@FXML
    private void handleResetButtonAction(ActionEvent event) {
        //actiontarget.setText("Reset");
    	firstNameProperty().set("");
    	middleNameProperty().set("");
    	lastNameProperty().set("");
		System.out.println("Reset Clicked");
    }
}
