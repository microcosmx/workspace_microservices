package preserveOther.domain;

import java.util.Date;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public class Trip {

    private TripId tripId;

    private String trainTypeId;

    private String startingStation;

    //中间停靠站，最开始的版本只设置一站，也就是说只有起始站、一个停靠站、终点站，在之后的版本中，停靠站扩展为若干站
    private String stations;

    private String terminalStation;

    private Date startingTime;

    private Date endTime;

    public Trip(){
        //Default Constructor
    }

    public Trip(TripId tripId, String trainTypeId, String startingStation, String stations, String terminalStation, Date startingTime, Date endTime) {
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

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
