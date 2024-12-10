package entity.board;

import entity.gem.Gem;
import entity.gem.SmallGem;
import entity.gem.BigGem;

import java.util.ArrayList;
import java.util.List;

public class Square {
    private int squareId;
    private List<Gem> gemsInSquare= new ArrayList<>();

    public Square(int squareId) {
        this.squareId = squareId;
        if(squareId == 0 || squareId == 6)
        {
            this.gemsInSquare.add(new BigGem(squareId));
        }
        else
        {
            this.gemsInSquare.add(new SmallGem(squareId));
            this.gemsInSquare.add(new SmallGem(squareId));
            this.gemsInSquare.add(new SmallGem(squareId));
            this.gemsInSquare.add(new SmallGem(squareId));
            this.gemsInSquare.add(new SmallGem(squareId));
        }
    }

    public int getSquareId() {
        return squareId;
    }

    public void setSquareId(int squareId) {
        this.squareId = squareId;
    }

    public List<Gem> getGemsInSquare() {
        return gemsInSquare;
    }

    public void setGemsInSquare(List<Gem> gemsInSquare) {
        this.gemsInSquare = gemsInSquare;
    }

    public void gemDrop(Gem gem)
    {
        gem.setLocate(squareId);
        gemsInSquare.add(gem);
    }

}