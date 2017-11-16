package travel.domain;

public class SearchForDistanceResult {

    private boolean status;

    private String message;

    private Route route;

    private int distance;

    public SearchForDistanceResult() {
        //Default Constructor
    }

    public SearchForDistanceResult(boolean status, String message, Route route, int distance) {
        this.status = status;
        this.message = message;
        this.route = route;
        this.distance = distance;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
