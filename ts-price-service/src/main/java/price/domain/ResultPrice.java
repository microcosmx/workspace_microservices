package price.domain;

/**
 * Created by Chenjie Xu on 2017/6/12.
 */
public class ResultPrice {

    private String placeA;
    private String placeB;
    private String trainTypeId;
    private String seatClass;
    private String price;

    public ResultPrice(){}

    public ResultPrice(String placeA, String placeB, String trainTypeId, String seatClass, String price) {
        this.placeA = placeA;
        this.placeB = placeB;
        this.trainTypeId = trainTypeId;
        this.seatClass = seatClass;
        this.price = price;
    }

    public String getPlaceA() {
        return placeA;
    }

    public void setPlaceA(String placeA) {
        this.placeA = placeA;
    }

    public String getPlaceB() {
        return placeB;
    }

    public void setPlaceB(String placeB) {
        this.placeB = placeB;
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
