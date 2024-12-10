package entity.board;

import java.util.ArrayList;
import java.util.List;

public class Broad {
    private List<Square> squareList = new ArrayList<>();

    public Broad()
    {
        for (int i = 0; i < 12; i++) {
            squareList.add(new Square(i));
        }
    }

    public List<Square> getSquareList() {
        return squareList;
    }

}