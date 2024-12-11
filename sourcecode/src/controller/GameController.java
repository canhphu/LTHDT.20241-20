package controller;
import entity.*;

public class GameController {
    private Player player1;
    private Player player2;
    private Board board;
    private boolean isTurn;

    public GameController(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.isTurn = true; // Lượt chơi ban đầu của player1
    }

    public void gameMethod(int direction, int pickedSquareId) {
        Player currentPlayer = getActivePlayer();
        int gemsPickedUp = currentPlayer.pickSquare(pickedSquareId);
        currentPlayer.spreadGem(direction, gemsPickedUp);
        int playerScore = calculatePlayerScore(currentPlayer);
        currentPlayer.setScore(playerScore);
        checkEndGame();
        setIsTurn(!getIsTurn());
    }

    public boolean stopSpreadGem(int currentSquareId) {
        Square currentSquare = board.getSquare(currentSquareId);
        if (currentSquare.getGemQuantity() > 0) {
            Gem lastGem = currentSquare.getGem(currentSquare.getGemQuantity() - 1);
            if (lastGem instanceof BigGem) {
                return true;
            } else if (lastGem instanceof SmallGem) {
                currentSquare.removeGem(lastGem);
                return false;
            }
        }
        return false;
    }

    public void autoAddGem(Player player) {
        player.refillPlayerSquares();
    }

    public Player getActivePlayer() {
        return getIsTurn() ? player1 : player2;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    private int calculatePlayerScore(Player player) {
        return player.checkScoreGems();
    }

    private void checkEndGame() {
        if (player1.getAllGemInPlayerSquares() == 0 && player2.getAllGemInPlayerSquares() == 0) {
            int winner = 0;
            if (player1.checkScoreGems() > player2.checkScoreGems()) {
                winner = 1;
            } else if (player1.checkScoreGems() < player2.checkScoreGems()) {
                winner = 2;
            }
            // Hiển thị thông tin người chiến thắng
        }
    }

    public boolean playerSquareEmptyCheck(Player player) {
        for (Square square : player.getPlayerSquares()) {
            if (square.getGemQuantity() == 0) {
                return true;
            }
        }
        return false;
    }
}
