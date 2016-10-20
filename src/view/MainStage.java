package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class MainStage extends Application{
	
	private static Stage mainStage;
	
	public static void setMainStage(Stage s)
	{
		mainStage = s;
	}
	
	public static Stage getMainStage()
	{
		return mainStage;
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("Namenesia");
		stage.setScene(scene);
		mainStage = stage;
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
