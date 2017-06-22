package inside_payment.service;

import inside_payment.domain.*;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface InsidePaymentService {
    boolean pay(PaymentInfo info);
    boolean createAccount(CreateAccountInfo info);
    boolean addMoney(AddMoneyInfo info);
    List<Payment> queryPayment();
    List<Balance> queryAccount();
}
