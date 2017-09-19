package assurance.domain;

public class ModifyAssuranceInfo {

    private String assuranceId;
    private String orderId;
    private  int typeIndex;

    public String getAssuranceId() {
        return assuranceId;
    }

    public void setAssuranceId(String assuranceId) {
        this.assuranceId = assuranceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

}
