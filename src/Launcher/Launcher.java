package Launcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Launcher extends Application {
	static LauncherController controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Configuration.fxml"));
        Parent root = loader.load();
        controller = (LauncherController)loader.getController();
        primaryStage.setTitle("Game Board Launcher");
        primaryStage.setScene(new Scene(root, 700, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
