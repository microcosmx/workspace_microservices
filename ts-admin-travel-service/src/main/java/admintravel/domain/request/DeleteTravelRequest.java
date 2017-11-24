package admintravel.domain.request;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;

/**
 * Created by Chenjie Xu on 2017/5/15.
 */
public class DeleteTravelRequest {
    @Valid
    @Id
    private String tripId;

    public DeleteTravelRequest(){
        //Default Constructor
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
