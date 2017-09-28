package seat.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class SeatRequest {
    @Valid
    @NotNull
    private Date travelDate;

    @Valid
    @NotNull
    private String trainNumber;

    @Valid
    @NotNull
    private int seatType;

    public SeatRequest(){

    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }
}
