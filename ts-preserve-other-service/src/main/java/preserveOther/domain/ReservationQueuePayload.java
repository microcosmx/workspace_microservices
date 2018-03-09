package preserveOther.domain;

public class ReservationQueuePayload {

    private OrderTicketsInfo oti;

    private String userId;

    private String loginToken;

    private String requestId;

    public ReservationQueuePayload() {
    }

    public ReservationQueuePayload(OrderTicketsInfo oti, String userId, String loginToken, String requestId) {
        this.oti = oti;
        this.userId = userId;
        this.loginToken = loginToken;
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public OrderTicketsInfo getOti() {
        return oti;
    }

    public void setOti(OrderTicketsInfo oti) {
        this.oti = oti;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
