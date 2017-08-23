package preserveOther.service;

import preserveOther.domain.OrderTicketsInfo;
import preserveOther.domain.OrderTicketsInfoWithOrderId;
import preserveOther.domain.OrderTicketsResult;

public interface PreserveOtherService {

    OrderTicketsResult preserve(OrderTicketsInfoWithOrderId oti, String accountId, String loginToken);

}
