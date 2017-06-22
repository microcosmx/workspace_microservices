package inside_payment.service;

import inside_payment.domain.*;
import inside_payment.repository.BalanceRepository;
import inside_payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */
@Service
public class InsidePaymentServiceImpl implements InsidePaymentService{

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    PaymentRepository paymentRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean pay(PaymentInfo info){
        QueryOrderResult result;
        if(info.getTripId().startsWith("G") || info.getTripId().startsWith("D")){
             result = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }else{
             result = restTemplate.postForObject(
                    "http://ts-order-service:12032/orderOther/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }

        if(result.isStatus()){
            if(paymentRepository.findByOrderNumber(info.getOrderId()) == null){
                Payment payment = new Payment();
                payment.setOrderId(info.getOrderId());
                payment.setPrice(result.getPrice());

                //判断一下账户余额够不够，不够要去站外支付

                paymentRepository.save(payment);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public boolean createAccount(CreateAccountInfo info){
        if(balanceRepository.findById(info.getUserId()) == null){
            Balance balance = new Balance();
            balance.setBalance(info.getBalance());
            balance.setUserId(info.getUserId());
            balanceRepository.save(balance);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean addMoney(AddMoneyInfo info){
        if(balanceRepository.findById(info.getUserId()) != null){
            Balance balance = balanceRepository.findById(info.getUserId());
            BigDecimal remainingMoney = new BigDecimal(balance.getBalance());
            String money = remainingMoney.add(new BigDecimal(info.getMoney())).toString();
            balance.setBalance(money);
            balanceRepository.save(balance);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Balance> queryAccount(){
        return balanceRepository.findAll();
    }

    @Override
    public List<Payment> queryPayment(){
        return paymentRepository.findAll();
    }
}
