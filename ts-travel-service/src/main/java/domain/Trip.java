package domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
@Document(collection="trip")
public class Trip {
    @Valid
    @Id
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeId;

    @Valid
    @NotNull
    private String startingStation;

    @Valid
    private String stations;

    @Valid
    @NotNull
    private String terminalStation;

    public TripId getTripId() {
        return tripId;
    }

    public void setTripId(TripId tripId) {
        this.tripId = tripId;
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations;
    }

    public String getTerminalStation() {
        return terminalStation;
    }

    public void setTerminalStation(String terminalStation) {
        this.terminalStation = terminalStation;
    }
}
