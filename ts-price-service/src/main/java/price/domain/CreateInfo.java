package price.domain;

/**
 * Created by Chenjie Xu on 2017/6/12.
 */
public class CreateInfo {
    public CreateInfo(){}

    private String placeA;
    private String placeB;
    private double distance;

    public CreateInfo(String placeA, String placeB, double distance) {

        this.placeA = placeA;
        this.placeB = placeB;
        this.distance = distance;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
