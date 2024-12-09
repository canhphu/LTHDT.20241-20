package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.HomeView.HomeView;

public class HomeController {

    @FXML
    private Button Exitbtn;

    @FXML
    private Button Guidebtn;

    @FXML
    private Button Playbtn;

    @FXML
    void PlaybtnClicked(MouseEvent event) throws IOException {
 
        Stage stage = (Stage) Playbtn.getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/play.fxml"));
        
        double height = Playbtn.getScene().getWindow().getHeight();
        double width = Playbtn.getScene().getWindow().getWidth();
        
        HomeView.setScene(stage, loader.load(), width, height);
        stage.show();
    }

    @FXML
    void GuidebtnClicked(MouseEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/rule.fxml"));
        Parent ruleScreen = loader.load();
        
        double height = Guidebtn.getScene().getWindow().getHeight()-15;
        double width = Guidebtn.getScene().getWindow().getWidth()-37;
 
        Scene ruleScene = new Scene(ruleScreen, width, height);

        Stage stage = (Stage) Guidebtn.getScene().getWindow();

        stage.setScene(ruleScene);
        stage.show();
    }
   

    @FXML
    void ExitbtnClicked(MouseEvent event) {
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
         alert.setTitle("Quit");
         alert.setHeaderText("You're about to quit");
         alert.setContentText("Do you want this ?");
         if (alert.showAndWait().get()== ButtonType.OK){
         Stage stage = (Stage) Exitbtn.getScene().getWindow();
         stage.close();
         }
    }

}

