package view.HomeView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class HomeView extends Application{
    public static final double WIDTH = 800;
	public static final double HEIGHT = 600;
	
	public static double width, height;
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("homeview.fxml"));

			setScene(stage, root, WIDTH, HEIGHT);
			stage.setOnCloseRequest(event -> {
				event.consume();
				quit(stage);
			});
			stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void quit(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You're about to quit");
        alert.setContentText("Do you want this?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
	}
	
	public static void setScene(Stage stage, Parent root, double width, double height) {
	    Scene scene = new Scene(root, width, height);
	    stage.setScene(scene);
	    stage.show();
	}
	
	public static void main(String[] args) {	
		launch(args);
    }
}
