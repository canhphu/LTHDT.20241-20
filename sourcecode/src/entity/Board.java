package entity;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Square> squareList = new ArrayList<>();

    public Board() {
        for (int i = 0; i < 12; i++) {
            squareList.add(new Square(i));
        }
    }

    public Square getSquareById(int squareId) {
        for (Square square : squareList) {
            if (square.getSquareId() == squareId) {
                return square;
            }
        }
        return null;
    }
    public List<Square> getSquareList() {
        return squareList;
    }
}
