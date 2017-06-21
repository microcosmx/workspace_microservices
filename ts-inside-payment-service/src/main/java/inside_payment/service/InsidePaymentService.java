package inside_payment.service;

import inside_payment.domain.AddMoneyInfo;
import inside_payment.domain.CreateAccountInfo;
import inside_payment.domain.PaymentInfo;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface InsidePaymentService {
    boolean pay(PaymentInfo info);
    boolean createAccount(CreateAccountInfo info);
    boolean addMoney(AddMoneyInfo info);
}
