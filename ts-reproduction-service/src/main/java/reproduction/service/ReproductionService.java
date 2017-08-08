package reproduction.service;

import reproduction.domain.Information;
import reproduction.domain.OrderTicketsInfo;
import reproduction.domain.OrderTicketsResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/8/7.
 */
public interface ReproductionService {
    OrderTicketsResult reproduct(OrderTicketsInfo info, String loginId, String loginToken) throws InterruptedException, ExecutionException, TimeoutException;
    OrderTicketsResult reproductOther(OrderTicketsInfo info, String loginId, String loginToken) throws InterruptedException, ExecutionException, TimeoutException;
    OrderTicketsResult reproductCorrect(OrderTicketsInfo info, String loginId, String loginToken) throws InterruptedException, ExecutionException, TimeoutException;
    OrderTicketsResult reproductOtherCorrect(OrderTicketsInfo info, String loginId, String loginToken) throws InterruptedException, ExecutionException, TimeoutException;

}
