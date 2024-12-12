package controller;

import java.util.List;
import entity.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private Player player1;
    private Player player2;
    private Board board;
    private boolean isTurn;
    private Timer timer;
    private static final int TURN_TIME_LIMIT = 30; // Giới hạn thời gian mỗi lượt chơi

    public GameController(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.isTurn = true; // Lượt chơi ban đầu của player1
        initializeGame(); // Khởi tạo giá trị ban đầu cho game
        startTurnTimer();
    }

    // Khởi tạo giá trị ban đầu cho các ô dân và ô quan
    private void initializeGame() {
        for (Square square : board.getSquareList()) {
            if (square.getSquareId() < 6) { // Giả định player1 có ô từ 0 đến 5
                square.addGem(new SmallGem(1)); // 1 gem nhỏ cho player1 trong ô dân
            } else { // Giả định player2 có ô từ 6 đến 11
                square.addGem(new SmallGem(1)); // 1 gem nhỏ cho player2 trong ô dân
            }
        }
    }

    // Xử lý lượt chơi
    public void gameMethod(int pickedSquareId, int direction) {
        Player currentPlayer = getActivePlayer();
        List<Square> playerSquares = currentPlayer.getPlayerSquares();

        // Kiểm tra xem người chơi có ô nào có gem không
        if (currentPlayer.getAllGemInPlayerSquares() == 0) {
            currentPlayer.refillPlayerSquares(); // Nạp lại gem nếu không có gem trong ô dân
            if (currentPlayer.getAllGemInPlayerSquares() == 0) {
                currentPlayer.increaseBorrowGem(5); // Vay 5 gem từ đối phương
            }
        }

        int gemsPickedUp = currentPlayer.pickSquare(pickedSquareId); // Lấy số lượng gem từ square đã chọn
        if (gemsPickedUp == 0) {
            System.out.println("Ô đã chọn không có quân.");
            return; // Kết thúc lượt nếu ô không có quân
        }

        // Rải gem
        int finalSquareId = currentPlayer.spreadGem(pickedSquareId, gemsPickedUp, direction, board);
        
        // Kiểm tra xem có ăn được quân không
        checkForCapture(finalSquareId);
        
        // Tính điểm người chơi
        int playerScore = playerPointCalculate(currentPlayer); 
        currentPlayer.setScore(playerScore); // Cập nhật điểm mới
        endGameCheck(); // Kiểm tra kết thúc trò chơi
        switchTurn(); // Chuyển người chơi
        resetTurnTimer(); // Đặt lại bộ đếm thời gian cho lượt tiếp theo
    }

    private void checkForCapture(int finalSquareId) {
        Square finalSquare = board.getSquareById(finalSquareId);
        if (finalSquare != null && finalSquare.getGemQuantity() == 0) {
            // Nếu ô trống, kiểm tra ô tiếp theo
            int nextSquareId = (finalSquareId + 1) % board.getSquareList().size();
            Square nextSquare = board.getSquareById(nextSquareId);
            if (nextSquare != null && nextSquare.getGemQuantity() > 0) {
                // Nếu ô tiếp theo có quân, ăn quân
                int gemsCaptured = nextSquare.getGemQuantity();
                nextSquare.removeAllGems(); // Xóa quân
                getActivePlayer().addScoreGems(List.of(new SmallGem(gemsCaptured))); // Cộng điểm
                // Kiểm tra tiếp ô sau
                checkForCapture(nextSquareId);
            }
        }
    }

    public Player getActivePlayer() {
        return isTurn ? player1 : player2;
    }

    private void switchTurn() {
        isTurn = !isTurn;
    }

    public int playerPointCalculate(Player player) {
        return player.checkScoreGems(); // Tính tổng điểm từ gem
    }

    public boolean isGameOver() {
        return player1.getAllGemInPlayerSquares() == 0 && player2.getAllGemInPlayerSquares() == 0;
    }

    private boolean endGameCheck() {
        if (player1.getAllGemInPlayerSquares() == 0 && player2.getAllGemInPlayerSquares() == 0) {
            int winner = 0;
            if (player1.checkScoreGems() > player2.checkScoreGems()) {
                winner = 1;
            } else if (player1.checkScoreGems() < player2.checkScoreGems()) {
                winner = 2;
            }
            displayWinner(winner);
            return true; // Trò chơi kết thúc
        }
        return false; // Trò chơi vẫn tiếp tục
    }

    private void displayWinner(int winner) {
        String message = (winner == 0) ? "Hòa!" : "Người chiến thắng: Player " + winner;
        System.out.println(message);
    }

    // Bắt đầu bộ đếm thời gian cho lượt chơi
    private void startTurnTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hết thời gian! Chuyển lượt cho đối phương.");
                switchTurn();
                resetTurnTimer(); // Đặt lại bộ đếm thời gian cho lượt tiếp theo
            }
        }, TURN_TIME_LIMIT * 1000); // 30 giây
    }

    // Đặt lại bộ đếm thời gian
    private void resetTurnTimer() {
        timer.cancel(); // Hủy bộ đếm hiện tại
        startTurnTimer(); // Bắt đầu lại bộ đếm thời gian
    }
}
