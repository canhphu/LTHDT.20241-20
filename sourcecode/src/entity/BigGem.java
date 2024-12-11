package entity;

public class BigGem extends Gem{

    public BigGem(int value) {
        super(value);
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int value){
        this.value = value;
    }
}