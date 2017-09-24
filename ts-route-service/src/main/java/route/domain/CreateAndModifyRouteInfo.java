package route.domain;

public class CreateAndModifyRouteInfo {

    private Route route;

    public CreateAndModifyRouteInfo() {
        //Default Constructor
    }

    public CreateAndModifyRouteInfo(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
