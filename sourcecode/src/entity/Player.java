package entity;

import java.util.List;

public class Player {
    private int playerId;
    private String playerName;
    private List<Square> playerSquares;
    private int score;
    private List<Gem> scoreGems;
    private int borrowGemCount; 

    public Player(int playerId, String playerName, List<Square> playerSquares) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerSquares = playerSquares;
        this.score = 0;
        this.borrowGemCount = 0;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Square> getPlayerSquares() {
        return playerSquares;
    }

    public void setPlayerSquares(List<Square> playerSquares) {
        this.playerSquares = playerSquares;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Gem> getScoreGems() {
        return scoreGems;
    }

    public void setScoreGems(List<Gem> scoreGems) {
        this.scoreGems = scoreGems;
    }

    public int getBorrowGemCount() {
        return borrowGemCount;
    }

    public void setBorrowGemCount(int borrowGemCount) {
        this.borrowGemCount = borrowGemCount;
    }

    public int pickSquare(int squareId) {
        for (Square square : playerSquares) {
            if (square.getSquareId() == squareId) {
                int gemsInSquare = square.getGemQuantity();
                square.removeAllGems();
                return gemsInSquare;
            }
        }
        return 0; 
    }

    public int getAllGemInPlayerSquares() {
        int totalGems = 0;
        for (Square square : playerSquares) {
            totalGems += square.getGemQuantity();
        }
        return totalGems;
    }

    public void increaseBorrowGem(int value) {
        this.borrowGemCount += value;
    }

    public void addScoreGems(List<Gem> gems) {
        if (scoreGems != null) {
            scoreGems.addAll(gems);
        } else {
            scoreGems = gems;
        }
        for (Gem gem : gems) {
            this.score += gem.getValue();
        }
    }

    public int checkScoreGems() {
        int gemScore = 0;
        if (scoreGems != null) {
            for (Gem gem : scoreGems) {
                gemScore += gem.getValue();
            }
        }
        return gemScore;
    }

    public void calculateScore(int points) {
        this.score += points;
    }

    public void refillPlayerSquares() {
        for (Square square : playerSquares) {
            square.addGem(new Gem(1));
        }
    }

    public int spreadGem(int pickedSquareId, int gemsPickedUp, int direction, Board board) {
        Square currentSquare = board.getSquareById(pickedSquareId);
        int currentSquareId = pickedSquareId;

        while (gemsPickedUp > 0) {
            currentSquare.addGem(new SmallGem(1)); // Rải 1 gem vào ô hiện tại
            gemsPickedUp--;

            // Tính ô tiếp theo dựa vào hướng
            currentSquareId = (currentSquareId + direction + board.getSquareList().size()) % board.getSquareList().size();
            currentSquare = board.getSquareById(currentSquareId);

            // Kiểm tra ô tiếp theo
            if (currentSquare.getGemQuantity() > 0) {
                continue; // Nếu ô tiếp theo có gem, tiếp tục rải
            } else {
                // Nếu ô tiếp theo trống
                break; // Kết thúc việc rải gem
            }
        }
        return currentSquareId; // Trả về ô cuối cùng đã rải
    }
}
