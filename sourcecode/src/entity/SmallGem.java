package entity;

public class SmallGem extends Gem{
    
    public SmallGem(int value) {
        super(value);
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int value){
        this.value = value;
    }
}