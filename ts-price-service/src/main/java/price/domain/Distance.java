package price.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Created by Chenjie Xu on 2017/6/9.
 */
@Document(collection="distance")
public class Distance {
    //station id,not name
    @Valid
    @NotNull
    private String placeA;

    @Valid
    @NotNull
    private String placeB;

    @NotNull
    @Valid
    private double distance;


    public Distance(){}


    public Distance(String placeA, String placeB, double distance) {
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
