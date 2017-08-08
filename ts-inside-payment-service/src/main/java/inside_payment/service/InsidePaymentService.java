package inside_payment.service;

import inside_payment.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface InsidePaymentService {
    boolean pay(PaymentInfo info, HttpServletRequest request) throws Exception;
    boolean createAccount(CreateAccountInfo info);
    boolean addMoney(AddMoneyInfo info);
    List<Payment> queryPayment();
    List<Balance> queryAccount();
    boolean drawBack(DrawBackInfo info);
    boolean payDifference(PaymentDifferenceInfo info, HttpServletRequest request);
    List<AddMoney> queryAddMoney();
}
