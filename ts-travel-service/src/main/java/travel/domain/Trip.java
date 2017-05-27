package travel.domain;

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

    //中间停靠站，最开始的版本只设置一站，也就是说只有起始站、一个停靠站、终点站，在之后的版本中，停靠站扩展为若干站
    @Valid
    private String stations;

    @Valid
    @NotNull
    private String terminalStation;

    @Valid
    @NotNull
    private String startingTime;

    @Valid
    @NotNull
    private String endTime;

    public Trip(){

    }

    public Trip(TripId tripId, String trainTypeId, String startingStation, String stations, String terminalStation, String startingTime, String endTime) {
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.startingStation = startingStation;
        this.stations = stations;
        this.terminalStation = terminalStation;
        this.startingTime = startingTime;
        this.endTime = endTime;
    }

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

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
