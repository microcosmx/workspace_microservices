package route.domain;

public class GetRouteByIdInfo {

    private String routeId;

    public GetRouteByIdInfo() {
        //Empty Constructor
    }

    public GetRouteByIdInfo(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
