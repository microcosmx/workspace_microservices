package preserve.service;

import org.springframework.scheduling.annotation.Async;
import preserve.domain.OrderTicketsInfo;
import preserve.domain.OrderTicketsResult;

import java.util.concurrent.Future;

public interface PreserveService {

    @Async("myAsync")
    Future<OrderTicketsResult> preserve(OrderTicketsInfo oti, String accountId, String loginToken);

}
