package assurance.domain;

import java.util.ArrayList;
import java.util.List;

public class GetAllAssuranceTypeResult {

    private boolean status;

    private String message;

    private List<AssuranceType> assuranceTypes;

    public GetAllAssuranceTypeResult() {
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

    public List<AssuranceType> getAssuranceTypes() {
        return assuranceTypes;
    }

    public void setAssuranceTypes(List<AssuranceType> assuranceTypes) {
        this.assuranceTypes = assuranceTypes;
    }

}
