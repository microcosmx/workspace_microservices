package travel.domain;

import java.io.Serializable;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public class TripId implements Serializable{
    private Type type;
    private String number;

//    public TripId(Type type, String number){
//        this.type = type;
//        this.number = number;
//    }

    public TripId(){}

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return this.type + number;
    }
}
