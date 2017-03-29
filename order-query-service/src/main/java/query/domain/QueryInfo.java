package query.domain;

import java.util.Date;

public class QueryInfo {
    private long accountId;
    private Date start;
    private Date end;
    private int state;

    private boolean enableDateQuery;
    private boolean enableStateQuery;

    public QueryInfo(long accountId){
        this.accountId = accountId;
        disableDateQuery();
        disableStateQuery();
    }

    public void enableDateQuery(Date startTime,Date endTime){
        enableDateQuery = true;
        start = startTime;
        end = endTime;
    }

    public void disableDateQuery(){
        enableDateQuery = false;
        start = null;
        end = null;
    }

    public void enableStateQuery(int targetStatus){
        enableStateQuery = true;
        state = targetStatus;
    }

    public void disableStateQuery(){
        enableDateQuery = false;
        state = -1;
    }
}
