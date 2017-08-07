package reproduction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reproduction.domain.Information;
import reproduction.domain.OrderTicketsInfo;
import reproduction.domain.OrderTicketsResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/8/7.
 */
@Service
public class ReproductionServiceImpl implements ReproductionService{

    @Autowired
    RestTemplate restTemplate;

    @Override
    public OrderTicketsResult reproduct(OrderTicketsInfo info, String loginId, String loginToken) throws InterruptedException, ExecutionException, TimeoutException {
        Future<OrderTicketsResult> preserve = preserve(info);
        Future<OrderTicketsResult> refreshOrder = refreshOrder(info);
        refreshOrder.get(2000, TimeUnit.MILLISECONDS);
        return preserve.get();
    }

    @Async("mySimpleAsync")
    private Future<OrderTicketsResult> preserve(OrderTicketsInfo info){
        OrderTicketsResult result = restTemplate.postForObject("http://ts-preserve-service:14568/preserve"
                ,info,OrderTicketsResult.class);
        return new AsyncResult<>(result);
    }

    @Async("mySimpleAsync")
    private Future<OrderTicketsResult> refreshOrder(OrderTicketsInfo info){
        OrderTicketsResult result = restTemplate.postForObject("http://ts-order-service:12031/order/query"
                ,info,OrderTicketsResult.class);
        return new AsyncResult<>(result);
    }
}
