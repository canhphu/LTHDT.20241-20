package entity;

import java.util.ArrayList;
import java.util.List;

public class Square {
    private int squareId;
    private List<Gem> gemsInSquare = new ArrayList<>();

    public Square(int squareId) {
        this.squareId = squareId;
    }

    public int getSquareId() {
        return squareId;
    }

    public void addGem(Gem gem) {
        gemsInSquare.add(gem);
    }

    public int getGemQuantity() {
        return gemsInSquare.size();
    }

    public void removeAllGems() {
        gemsInSquare.clear();
    }

    public List<Gem> getGemInSquare() {
        return gemsInSquare;
    }
}