package preserveOther.domain;

public class ReserveQueueInformation {

    private String requestId;

    private String userId;

    private OrderTicketsResult result;

    public ReserveQueueInformation() {
    }

    public ReserveQueueInformation(String requestId, String userId) {
        this.requestId = requestId;
        this.userId = userId;
    }

    public OrderTicketsResult getResult() {
        return result;
    }

    public void setResult(OrderTicketsResult result) {
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
