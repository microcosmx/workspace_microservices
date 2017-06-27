package rebook.domain;

/**
 * Created by Administrator on 2017/6/27.
 */
public class RebookResult {
    private boolean status;
    private String message;

    public RebookResult(){}

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
}
