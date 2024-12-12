package view;

import entity.*;
import controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.ArrayList;

public class GameView {
    @FXML
    private HBox hBox1;
    @FXML
    private HBox hBox2;
    @FXML
    private Label point1Label;
    @FXML
    private Label point2Label;
    @FXML
    private Label turnLabel;
    @FXML
    private Button exitBtn;
    @FXML
    private ComboBox<String> directionComboBox; // ComboBox để chọn hướng
    @FXML
    private Button spreadButton; // Nút để thực hiện rải gem

    private GameController gameController;
    private int selectedSquareId = -1; // ID ô được chọn

    public GameView(GameController controller) {
        this.gameController = controller;
    }

    @FXML
    private void initialize() {
        initBoard();
        updateScores();
        updateTurnLabel();
        setupDirectionComboBox(); // Thiết lập ComboBox hướng
    }

    private void setupDirectionComboBox() {
        directionComboBox.getItems().addAll("Clockwise", "Counterclockwise");
        directionComboBox.setValue("Clockwise"); // Giá trị mặc định
    }

    private void initBoard() {
        for (int i = 0; i < 12; i++) {
            Square square = gameController.getBoard().getSquare(i);
            createSquareUI(square);
        }
    }

    private void createSquareUI(Square square) {
        VBox squareBox = new VBox();
        squareBox.setAlignment(Pos.CENTER);
        squareBox.setPrefSize(75, 75);
        squareBox.setStyle("-fx-background-color: rgb(205, 161, 128); -fx-border-color: rgb(102, 66, 40); -fx-border-width: 5;");

        Label pointLabel = new Label(String.valueOf(square.getGemQuantity()));
        pointLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
        pointLabel.setTextFill(Color.rgb(102, 66, 40));

        FlowPane gemPane = new FlowPane();
        gemPane.setPrefWidth(60);
        gemPane.setPrefHeight(60);
        gemPane.setHgap(0.5);
        gemPane.setVgap(0.5);
        updateGemDisplay(gemPane, square.getGemQuantity());

        squareBox.getChildren().addAll(gemPane, pointLabel);
        squareBox.setOnMouseClicked(event -> handleSquareClick(square.getSquareId()));

        if (square.getSquareId() < 6) {
            hBox1.getChildren().add(squareBox);
        } else {
            hBox2.getChildren().add(0, squareBox);
        }
    }

    private void updateGemDisplay(FlowPane gemPane, int gemQuantity) {
        gemPane.getChildren().clear();
        for (int i = 0; i < gemQuantity; i++) {
            gemPane.getChildren().add(new Circle(4.0));
        }
    }

    private void handleSquareClick(int squareId) {
        selectedSquareId = squareId; // Lưu ID ô được chọn
        System.out.println("Square clicked: " + squareId);
        highlightSelectedSquare(squareId); // Nổi bật ô được chọn
    }

    private void highlightSelectedSquare(int squareId) {
        for (Node node : hBox1.getChildren()) {
            if (node instanceof VBox) {
                VBox squareBox = (VBox) node;
                if (squareBox.getId() != null && Integer.parseInt(squareBox.getId()) == squareId) {
                    squareBox.setStyle("-fx-background-color: yellow; -fx-border-color: rgb(102, 66, 40); -fx-border-width: 5;");
                } else {
                    squareBox.setStyle("-fx-background-color: rgb(205, 161, 128); -fx-border-color: rgb(102, 66, 40); -fx-border-width: 5;");
                }
            }
        }
    }

    @FXML
    private void handleSpreadButtonClick() {
        if (selectedSquareId == -1) {
            showAlert("Please select a square first.");
            return;
        }
        String direction = directionComboBox.getValue();
        int directionValue = direction.equals("Clockwise") ? 1 : -1;
        gameController.gameMethod(selectedSquareId, directionValue);
        updateScores();
        updateTurnLabel();
        checkGameOver();
        selectedSquareId = -1; // Reset selected square ID
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateScores() {
        point1Label.setText("Player 1 Score: " + gameController.getPlayer1().getScore());
        point2Label.setText("Player 2 Score: " + gameController.getPlayer2().getScore());
    }

    public void updateTurnLabel() {
        Player currentPlayer = gameController.getActivePlayer();
        turnLabel.setText("Current Turn: Player " + (currentPlayer == gameController.getPlayer1() ? "1" : "2"));
    }

    private void checkGameOver() {
        if (gameController.isGameOver()) {
            showGameOverDialog();
        }
    }

    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("The game has ended!");
        alert.showAndWait();
    }

    @FXML
    void ExitBtnClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Click OK to return to the home screen or Cancel to continue playing.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                goToHomeScreen();
            }
        });
    }

    private void goToHomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeview.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
