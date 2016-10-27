package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import dataTypes.Person;
import db.PeopleGetter;
import db.PersonBus;
import javafx.application.Application;

public class StageChanger extends Application {

	private static Stage mainStage;

	public static void setMainStage(Stage s) {
		mainStage = s;
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		setMainStage(stage);
		StageChanger ms = new StageChanger();
		ms.profileView();

		}

	public static void main(String[] args) {
		Person me = PeopleGetter.getPersonFromID(1);
		PersonBus.setPerson(me, 1);
		launch(args);

	}

	public void addTagsView() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/AddTags.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainStage.setTitle("Namenesia");
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void profileView() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Profile.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
			mainStage.setTitle("Namenesia");
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void newNameView() throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("../view/NewName.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
		mainStage.setTitle("Namenesia");
		mainStage.setScene(scene);
		mainStage.show();
	}
}
