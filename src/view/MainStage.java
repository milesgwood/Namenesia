package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import dataTypes.Person;
import db.PeopleGetter;
import db.PersonBus;
import javafx.application.Application;

public class MainStage extends Application {

	private static Stage mainStage;

	public static void setMainStage(Stage s) {
		mainStage = s;
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("Namenesia");
		stage.setScene(scene);
		mainStage = stage;
		stage.show();
	}

	public static void main(String[] args) {
		Person me = PeopleGetter.getPersonFromID(1);
		PersonBus.setPerson(me, 1);
		launch(args);
	}

	public void addTagsView() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("AddTags.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainStage.setTitle("Namenesia");
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
