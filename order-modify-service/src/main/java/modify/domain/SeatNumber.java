package modify.domain;

public class SeatNumber {

    char position;
    int lineNum;
    int seatNumber;
    boolean isNormalSeat;

    public SeatNumber(){
        isNormalSeat = false;
    }

    public char getPosition() {
        return position;
    }

    public void setPosition(char position) {
        this.position = position;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isNormalSeat() {
        return isNormalSeat;
    }

    public void setNormalSeat(boolean normalSeat) {
        isNormalSeat = normalSeat;
    }

    @Override
    public String toString(){
        if(isNormalSeat){
            return "" + seatNumber;
        }else{
            return "" + position + lineNum;
        }
    }
}
