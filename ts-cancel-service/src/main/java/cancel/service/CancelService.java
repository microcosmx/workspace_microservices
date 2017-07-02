package cancel.service;

import cancel.domain.CancelOrderInfo;
import cancel.domain.CancelOrderResult;

public interface CancelService {

    CancelOrderResult cancelOrder(CancelOrderInfo infom,String loginToken);

}
