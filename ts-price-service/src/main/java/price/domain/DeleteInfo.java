package price.domain;

/**
 * Created by Chenjie Xu on 2017/6/12.
 */
public class DeleteInfo {
    public DeleteInfo(){}



    private String placeA;
    private String placeB;

    public DeleteInfo(String placeA, String placeB) {
        this.placeA = placeA;
        this.placeB = placeB;
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
}
