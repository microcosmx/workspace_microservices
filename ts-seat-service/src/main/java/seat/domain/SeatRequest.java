package seat.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SeatRequest {
    @Valid
    @NotNull
    private String trainNumber;

    @Valid
    @NotNull
    private int seatType;

    public SeatRequest(){

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
