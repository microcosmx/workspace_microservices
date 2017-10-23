package plan.domain;

import java.util.Date;

public class GetRoutePlanInfo {

    private String formStationId;

    private String toStationId;

    private Date travelDate;

    private int num;

    public GetRoutePlanInfo() {
        //Empty Constructor
    }

    public GetRoutePlanInfo(String formStationId, String toStationId, Date travelDate, int num) {
        this.formStationId = formStationId;
        this.toStationId = toStationId;
        this.travelDate = travelDate;
        this.num = num;
    }

    public String getFormStationId() {
        return formStationId;
    }

    public void setFormStationId(String formStationId) {
        this.formStationId = formStationId;
    }

    public String getToStationId() {
        return toStationId;
    }

    public void setToStationId(String toStationId) {
        this.toStationId = toStationId;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
