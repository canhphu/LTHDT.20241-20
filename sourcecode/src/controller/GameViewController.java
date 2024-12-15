package src.controller;


import src.entity.Player;
import src.entity.Square;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameViewController {
    private GameController gameController;
    @FXML
    private StackPane homePane; // StackPane cho màn Home
    @FXML
    private StackPane guidePane; // StackPane cho màn Hướng Dẫn
    @FXML
    private AnchorPane gamePane; //AnchorPane cho màn chơi
   @FXML
    private StackPane insertNamePane; // màn nhập tên người chơi trước khi chơi
    @FXML
    private StackPane endGamePane;
    //element cho home view
    @FXML
    private Button newGameButton;
    @FXML
    private Button guideButton;
    @FXML
    private Button exitButton;
    @FXML
    //element cho guide view
    private Button guideCloseButton;
    //element cho insert name view
    @FXML
    private TextField player1TextField;
    @FXML
    private TextField player2TextField;
    @FXML
    private Button confirmNameButton;
    //element cho game view
    @FXML
    private GridPane squareGrid;
    @FXML
    private StackPane square0;
    @FXML
    private StackPane square1;
    @FXML
    private StackPane square2;
    @FXML
    private StackPane square3;
    @FXML
    private StackPane square4;
    @FXML
    private StackPane square5;
    @FXML
    private StackPane square6;
    @FXML
    private StackPane square7;
    @FXML
    private StackPane square8;
    @FXML
    private StackPane square9;
    @FXML
    private StackPane square10;
    @FXML
    private StackPane square11;
    @FXML
    private Arc square0Arc;
    @FXML
    private Arc square6Arc;
    @FXML
    private final List<StackPane> squares = new ArrayList<>();
    @FXML
    private Text currentPlayerText;
    @FXML
    private Label namePlayer1;
    @FXML
    private Label namePlayer2;
    @FXML
    private Label scorePlayer1;
    @FXML
    private Label scorePlayer2;
    @FXML
    private Label borrowGemPlayer1;
    @FXML
    private Label borrowGemPlayer2;
    @FXML
    private Button rightButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button spreadGemButton;
    @FXML
    private StackPane pickSquare;
    ///element cho màn end Game
    @FXML
    private Label winnerLabel;
    @FXML
    private Label player1ScoreLabel;
    @FXML
    private Label player2ScoreLabel;

    private StackPane lastSelectedSquare = null; // ô được chọn
    private Button lastSelectedDirection = null;
    private boolean clockwise;// chiều được chọn
    ////////
//Controller cho giao diện
////////
    @FXML
    private void initialize() {
        squares.add(0,square0);
        squares.add(1,square1);
        squares.add(2,square2);
        squares.add(3,square3);
        squares.add(4,square4);
        squares.add(5,square5);
        squares.add(6,square6);
        squares.add(7,square7);
        squares.add(8,square8);
        squares.add(9,square9);
        squares.add(10,square10);
        squares.add(11,square11);
        if(newGameButton!=null) {
            // Event handler for "Game Mới"
            newGameButton.setOnAction(event -> startNewGame());
        }
        if(guideButton!=null) {
            // Event handler for "Hướng Dẫn"
            guideButton.setOnAction(event -> showGuide());
        }
        if(exitButton!=null) {
            // Event handler for "Thoát"
            exitButton.setOnAction(event -> exitGame());
        }
        if(guideCloseButton!=null) {
            guideCloseButton.setOnAction(event -> closeGuide());
        }
        if(confirmNameButton!=null) {
            confirmNameButton.setOnAction(event -> confirmInsertName() );
        }
    }
    private void startNewGame() {
        System.out.println("Bắt đầu game mới");
        homePane.setManaged(false);
        homePane.setVisible(false);
        insertNamePane.setVisible(true);
        insertNamePane.setManaged(true);
    }

    private void showGuide() {
        System.out.println("Hiển thị hướng dẫn");
        homePane.setVisible(false);
        homePane.setManaged(false);
        guidePane.setVisible(true);
        guidePane.setManaged(true);
    }

    private void exitGame() {
        System.out.println("Thoát game");
        // Thoát ứng dụng hoặc quay lại màn hình chính.
        System.exit(0);
    }


    //Controller cho GuideView
    // Phương thức đóng cửa sổ hướng dẫn
    @FXML
    private void closeGuide() {
        System.out.println("đhdhhdha");
        homePane.setVisible(true);
        homePane.setManaged(true);
        guidePane.setVisible(false);
        guidePane.setManaged(false);
    }
    //Controller cho InsertName view
    @FXML
    private void confirmInsertName(){
        insertNamePane.setVisible(false);
        insertNamePane.setManaged(false);
        initGameView();
        gamePane.setVisible(true);
        gamePane.setManaged(true);
    }


    //Controller cho PlayView
    @FXML
      public void initGameView(){
        String player1Name = player1TextField.getText();
        String player2Name = player2TextField.getText();
        gameController = new GameController(player1Name, player2Name);
        List<Square> squaresLogic = gameController.getBoard().getSquareList();
        for(int i =0;i<squaresLogic.size();i++){
            StackPane square = squares.get(i);
            Square squareEntity = squaresLogic.get(i);
            deleteAllGemImageInSquare(square);
            if(i%6==0){
                Image bigGemImage = new Image("/image/big_gem.png");
                ImageView bigGemView = new ImageView(bigGemImage);
                bigGemView.setFitHeight(40);
                bigGemView.setFitWidth(40);
                square.getChildren().add(bigGemView);
            }else {
                for (int j = 0; j < squareEntity.getGemsQuantity(); j++) {
                    addGemImage(square,false);
                }
            }
        }
        currentPlayerText.setText("Player 1: " + player1Name);
        namePlayer1.setText("Player1: "+gameController.getPlayer1().getName());
        namePlayer2.setText("Player2: "+gameController.getPlayer2().getName());
        updateScoreAndBorrowGem();

    }
    public void updateScoreAndBorrowGem(){
        scorePlayer1.setText("Score: "+gameController.getPlayer1().getScore());
        scorePlayer2.setText("Score: "+ gameController.getPlayer2().getScore());
        borrowGemPlayer1.setText("Borrow gems: "+ gameController.getPlayer1().getBorrowGems());
        borrowGemPlayer2.setText("Borrow gems: "+ gameController.getPlayer2().getBorrowGems());
        currentPlayerText.setText("Lượt: Player"+gameController.getCurrentPlayer().getId()+" "+gameController.getCurrentPlayer().getName());
    }
    @FXML
    public void deleteAllGemImageInSquare(StackPane square){
        square.getChildren().removeIf(node -> node instanceof ImageView);

    }

    @FXML
    public void addGemImage(StackPane square, boolean isQuan){
        Image smallGemImage = new Image("image/small_gem.png");
        ImageView gemImage = new ImageView(smallGemImage);
        gemImage.setFitHeight(20);
        gemImage.setFitWidth(20);
        if(!isQuan) {
            double paneWidth = square.getWidth();
            double paneHeight = square.getHeight();
            double margin = 40;

            // Giới hạn X và Y để đảm bảo cách cạnh 40px
            double randomX = (Math.random() * (paneWidth - 2 * margin)) - (paneWidth / 2 - margin);
            double randomY = (Math.random() * (paneHeight - 2 * margin)) - (paneHeight / 2 - margin);
            gemImage.setTranslateX(randomX);
            gemImage.setTranslateY(randomY);
        }else {
            double radiusX = 140; // Bán kính X của elip
            double radiusY = 140; // Bán kính Y của elip

            // Sinh góc ngẫu nhiên trong khoảng từ 90° đến 270° (bán nguyệt)
            double angle = Math.toRadians(90 + Math.random() * 180);

            // Sinh bán kính ngẫu nhiên trong khoảng từ 0 đến 1 (chuẩn hóa)
            double normalizedRadius = Math.sqrt(Math.random()); // sqrt để phân bố đều trong diện tích

            // Tính tọa độ Descartes
            double randomX = normalizedRadius * Math.cos(angle) * radiusX;
            double randomY = normalizedRadius * Math.sin(angle) * radiusY;

            // Đặt tọa độ cho hình ảnh
            gemImage.setTranslateX(randomX);
            gemImage.setTranslateY(randomY);

        }
        square.getChildren().add(gemImage);

    }

    @FXML
    public void handleSquareClick(MouseEvent event) {
        // Get the clicked square
        StackPane selectedSquare = (StackPane) event.getSource();

        // Reset color of the previously selected square
        if (lastSelectedSquare != null) {
            resetSquareColor(lastSelectedSquare);
        }
        highlightSquare(selectedSquare);

        lastSelectedSquare = selectedSquare;

        // Show the buttons
        rightButton.setVisible(true);
        leftButton.setVisible(true);
    }

    private void highlightSquare(StackPane square) {
        String id = square.getId();
        switch (id){
            case "square0":
                square0Arc.setFill(Color.YELLOW);
                break;
            case "square6":
                square6Arc.setFill(Color.YELLOW);
                break;
            default:
                square.setStyle("-fx-background-color: yellow; -fx-border-width: 1; -fx-border-color: black");
                break;
        }
    }

    private void resetSquareColor(StackPane square) {
        // Reset the color of the square back to its default color
        String id = square.getId();
        switch (id){
            case "square0":
                square0Arc.setFill(Color.DEEPSKYBLUE);
                break;
            case "square6":
                square6Arc.setFill(Color.DEEPSKYBLUE);
                break;
            default:
                square.setStyle("-fx-background-color: lightblue;-fx-border-width: 1; -fx-border-color: black");
                break;
        }
    }

    @FXML
    public void handleButtonDirectionClick(MouseEvent event) {
        // Determine which button is clicked
        Button clickedButton = (Button) event.getSource();

        if (lastSelectedDirection != null) {
            resetButtonColor(lastSelectedDirection);
        }

        // Highlight the clicked button
        highlightButton(clickedButton);

        // Save the selected button for later
        lastSelectedDirection = clickedButton;

        // Make the "Spread Gem" button visible
        spreadGemButton.setVisible(true);
    }

    private void highlightButton(Button button) {
        // Set the color of the selected button to yellow
        button.setStyle("-fx-background-color: yellow;");
    }

    private void resetButtonColor(Button button) {
        // Reset the color of the button back to its default color
        button.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;");
    }

    @FXML
    public void handleSpreadGemButtonClick(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        if(clickedButton == spreadGemButton){
            highlightButton(clickedButton);
        }
        int pickSquareId = squares.indexOf(lastSelectedSquare);
        if (pickSquareId <= 5 && pickSquareId >= 1)
            clockwise = !Objects.equals(lastSelectedDirection.getId(), "leftButton");
        else
            clockwise= Objects.equals(lastSelectedDirection.getId(), "leftButton");
        for(StackPane square : squares){
            System.out.println("bababaa"+square.getId());
        }
        spreadGem(pickSquareId, clockwise);
        handleAfterTurn();
    }
    private void spreadGem(int pickSquareId, boolean clockwise ) {

        pickSquareAnimation();
        // Sử dụng Timeline để đợi 2 giây trước khi thực hiện spreadGemsAnimation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> {
                    List<Integer> spreadSquareId;
                    spreadSquareId = gameController.spreadGems(pickSquareId, clockwise);
                    spreadGemsAnimation(spreadSquareId);

                })
        );
        timeline.play();

    }
    public void pickSquareAnimation(){
        // Lấy tất cả các ImageView con từ lastSelectedSquare
        List<TranslateTransition> transitions = new ArrayList<>();

        for (var child : new ArrayList<>(lastSelectedSquare.getChildren())) {
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                imageView.toFront();
                // Tạo hoạt ảnh cho từng ImageView
                TranslateTransition transition = new TranslateTransition(Duration.seconds(1), imageView);

                // Lấy vị trí tuyệt đối của pickSquare và lastSelectedSquare trong scene
                double deltaX = pickSquare.localToScene(pickSquare.getBoundsInLocal()).getMinX() - lastSelectedSquare.localToScene(lastSelectedSquare.getBoundsInLocal()).getMinX();
                double deltaY = pickSquare.localToScene(pickSquare.getBoundsInLocal()).getMinY() - lastSelectedSquare.localToScene(lastSelectedSquare.getBoundsInLocal()).getMinY();

                // Set chuyển động bay (di chuyển theo vector từ pan1 đến pan2)
                transition.setToX(deltaX);
                transition.setToY(deltaY);

                // Lưu vị trí của các viên đá khi chúng đến đích (pickSquare)
                transition.setOnFinished(event -> {
                    addGemImage(pickSquare,false);  // Thêm viên đá vào pickSquare
                });

                // Thêm hoạt ảnh vào danh sách để đồng bộ hóa
                transitions.add(transition);
            }
        }
        // Chạy tất cả hoạt ảnh cùng lúc
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(transitions);
        parallelTransition.play();
        parallelTransition.setOnFinished(event -> {
            parallelTransition.stop();  // Dừng và xóa hoạt ảnh khỏi parallelTransition

        });
    }
    public void spreadGemsAnimation(List<Integer> targetSquareIds) {
        // Tạo danh sách các ImageView từ lastSelectedSquareSy

        List<ImageView> gemsToMove = new ArrayList<>();
        for (var child : pickSquare.getChildren()) {
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;

                gemsToMove.add(imageView);
            }
        }
        System.out.println("kakakakaka"+gemsToMove.size());
        // Gọi hàm đệ quy để xử lý từng viên đá với độ trễ
        moveGemsRecursively(gemsToMove, targetSquareIds, 0);
    }

    private void moveGemsRecursively(List<ImageView> gemsToMove, List<Integer> targetSquareIds, int index) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(400 ), event -> {
        if (index >= gemsToMove.size()) {
            int currentIndex = targetSquareIds.getLast();
            int nextLastIndex = gameController.getNextSquareIndex(currentIndex, clockwise);
            int response = gameController.handleNextEndSquare(nextLastIndex,clockwise);
            if(response == 0){
                int nextTwoLastIndex = gameController.getNextSquareIndex(nextLastIndex, clockwise);
                squares.get(nextTwoLastIndex).getChildren().clear();
                updateScoreAndBorrowGem();
            }else {
                lastSelectedSquare = squares.get(nextLastIndex);
                spreadGem(nextLastIndex,clockwise);

            }
            return; // Thoát khi đã xử lý hết các viên đá
        }

        ImageView gem = gemsToMove.get(index);
        int targetSquareId = targetSquareIds.get(index);  // Lấy ID ô đích tương ứng
        StackPane targetSquare = squares.get(targetSquareId);

        // Tạo hoạt ảnh di chuyển cho viên đá
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), gem);

        // Lấy tọa độ của pickSquare và targetSquare trong hệ tọa độ của Scene
        Bounds pickBounds = pickSquare.localToScene(pickSquare.getBoundsInLocal());
        Bounds targetBounds = targetSquare.localToScene(targetSquare.getBoundsInLocal());

        // Tính khoảng cách delta giữa pickSquare và targetSquare
        double deltaX = targetBounds.getMinX() - pickBounds.getMinX();
        double deltaY = targetBounds.getMinY() - pickBounds.getMinY();

        // Set chuyển động (di chuyển gem từ vị trí cũ đến vị trí mới trong Parent)
        transition.setByX(deltaX);
        transition.setByY(deltaY);

        // Khi hoạt ảnh hoàn thành, xử lý xóa và thêm viên đá vào ô đích
        transition.setOnFinished(event1 -> {
            transition.stop();
            pickSquare.getChildren().removeFirst();
            // Xóa viên đá khỏi pickSquare
            addGemImage(targetSquare,targetSquareId%6==0);
            // Dừng 2 giây trước khi thực hiện lần lặp tiếp theo
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> moveGemsRecursively(gemsToMove, targetSquareIds, index + 1));
            pause.play();
        });

        transition.play(); // Bắt đầu hoạt ảnh
    }));
    timeline.play();
    }
    public void handleAfterTurn() {
        if(gameController.isGameOver())  endGame();
        switch (gameController.handleRefillGems()){
            case 2:
                endGame();
                break;
            case 1:
                refillGems(gameController.getCurrentPlayer());
                break;
        }

    }

    @FXML
    public void refillGems( Player currentPlayer) {
        if(currentPlayer.getId()==1){
            for(int i=1;i<=5;i++){
                addGemImage(squares.get(i),false);
            }
        }else {
            for(int i=7;i<=11;i++){
                addGemImage(squares.get(i),false);
            }
        }
        updateScoreAndBorrowGem();
    }


    ////////
//Controller cho màn endGame
////////
   @FXML
    public void endGame(){
        gamePane.setVisible(false);
        endGamePane.setVisible(true);
        player1ScoreLabel.setText("Điểm của player1: "+gameController.getPlayer1().getScore());
        player2ScoreLabel.setText("Điểm của player2: "+gameController.getPlayer2().getScore());
        if(gameController.getPlayer1().getScore()>gameController.getPlayer2().getScore()){
            winnerLabel.setText("Người chiến thắng là: Player1 "+gameController.getPlayer1().getName());
        }else {
            if(gameController.getPlayer2().getScore()>gameController.getPlayer1().getScore()){
                winnerLabel.setText("Người chiến thắng là: Player2 "+gameController.getPlayer2().getName());
            }
            else {
                winnerLabel.setText("Hòa");
            }
        }

   }
}


