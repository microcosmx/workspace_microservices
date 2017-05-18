package travel.domain;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Chenjie Xu on 2017/5/15.
 * 查询车票的返回信息，模仿12306，有车次、出发站、到达站、出发时间、到达时间、座位等级及相应的余票
 */
public class TripResponse {
    @Valid
    @Id
    private TripId tripId;

    @Valid
    @NotNull
    private String startingStation;

    @Valid
    @NotNull
    private String terminalStation;

    @Valid
    @NotNull
    private String startingTime;

    @Valid
    @NotNull
    private String endTime;

    @Valid
    @NotNull
    private int economyClass;   //普通座的座位数量

    @Valid
    @NotNull
    private int confortClass;   //商务座的座位数量

    public TripId getTripId() {
        return tripId;
    }

    public void setTripId(TripId tripId) {
        this.tripId = tripId;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
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

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getConfortClass() {
        return confortClass;
    }

    public void setConfortClass(int confortClass) {
        this.confortClass = confortClass;
    }
}
