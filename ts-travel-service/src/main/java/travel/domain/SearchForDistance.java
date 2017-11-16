package travel.domain;

public class SearchForDistance {

    private String tripId;

    private String fromStaionName;

    private String toStationName;

    public SearchForDistance() {
        //Default Constructor
    }

    public SearchForDistance(String tripId, String fromStaionName, String toStationName) {
        this.tripId = tripId;
        this.fromStaionName = fromStaionName;
        this.toStationName = toStationName;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getFromStaionName() {
        return fromStaionName;
    }

    public void setFromStaionName(String fromStaionName) {
        this.fromStaionName = fromStaionName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }
}
