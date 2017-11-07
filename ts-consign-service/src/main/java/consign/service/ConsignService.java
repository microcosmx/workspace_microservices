package consign.service;

import consign.domain.ConsignRecord;
import consign.domain.ConsignRequest;

import java.util.ArrayList;
import java.util.UUID;

public interface ConsignService {
    boolean insertConsignRecord(ConsignRequest consignRequest);
    boolean updateConsignRecord(ConsignRequest consignRequest);
    ArrayList<ConsignRecord> queryByAccountId(UUID accountId);
    ArrayList<ConsignRecord> queryByConsignee(String consignee);
}
