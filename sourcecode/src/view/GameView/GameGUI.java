package view.GameView;
import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameGUI extends Application {
    @FXML
    private GridPane boardGridPane;
    @FXML
    private Label player1ScoreLabel, player2ScoreLabel, currentPlayerLabel;

    private GameController gameController;
    private Player player1;
    private Player player2;
    private Board board;

    private Button[][] squareButtons;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Khởi tạo GameController và các đối tượng liên quan
        player1 = new Player(1, "Player 1", /* danh sách các Square */);
        player2 = new Player(2, "Player 2", /* danh sách các Square */);
        board = new Board(/* danh sách các Square */);
        gameController = new GameController(player1, player2, board);

        // Tải FXML và khởi tạo giao diện
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game_gui.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gem Spreading Game");
        primaryStage.show();

        // Tạo các nút trên bảng
        squareButtons = new Button[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Button button = new Button();
                button.setOnAction(event -> handleSquareClick(button));
                squareButtons[i][j] = button;
                boardGridPane.add(button, j, i);
            }
        }
    }

    private void handleSquareClick(Button button) {
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);
        int squareId = row * 5 + col;

        gameController.gameMethod(0, squareId);
        updateUI();
    }

    private void updateUI() {
        Player activePlayer = gameController.getActivePlayer();
        player1ScoreLabel.setText("Player 1 Score: " + player1.getScore());
        player2ScoreLabel.setText("Player 2 Score: " + player2.getScore());

        if (gameController.getIsTurn()) {
            currentPlayerLabel.setText("Current Player: Player 1");
        } else {
            currentPlayerLabel.setText("Current Player: Player 2");
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int squareId = i * 5 + j;
                Square square = board.getSquare(squareId);
                if (square.getGemQuantity() > 0) {
                    squareButtons[i][j].setText(String.valueOf(square.getGemQuantity()));
                } else {
                    squareButtons[i][j].setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
