package route.domain;

public class CreateAndModifyRouteResult {

    private boolean status;

    private String message;

    private Route route;

    public CreateAndModifyRouteResult() {
        //Default Constructor
    }

    public CreateAndModifyRouteResult(boolean status, String message, Route route) {
        this.status = status;
        this.message = message;
        this.route = route;
    }


}
