package orders.domain;

public class TrainNumber {

    private int type;
    private int number;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return TrainType.getNameByCode(type) + number;
    }

}
