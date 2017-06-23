package inside_payment.service;

import inside_payment.domain.*;
import inside_payment.repository.BalanceRepository;
import inside_payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Iterator;
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
                    "http://ts-order-other-service:12032/orderOther/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }

        if(result.isStatus()){
            if(paymentRepository.findByOrderId(info.getOrderId()) == null){
                Payment payment = new Payment();
                payment.setOrderId(info.getOrderId());
                payment.setPrice(result.getPrice());

                //判断一下账户余额够不够，不够要去站外支付
                Balance balance = balanceRepository.findByUserId(info.getUserId());
                List<Payment> payments = paymentRepository.findByUserId(info.getUserId());
                Iterator<Payment> iterator = payments.iterator();
                BigDecimal totalExpand = new BigDecimal("0");
                while(iterator.hasNext()){
                    Payment p = iterator.next();
                    totalExpand.add(new BigDecimal(p.getPrice()));
                }
                totalExpand.add(new BigDecimal(result.getPrice()));
                if(totalExpand.compareTo(new BigDecimal(balance.getBalance())) > 0){
                    //站外支付
                }else{
                    paymentRepository.save(payment);
                }

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
        if(balanceRepository.findByUserId(info.getUserId()) == null){
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
        if(balanceRepository.findByUserId(info.getUserId()) != null){
            Balance balance = balanceRepository.findByUserId(info.getUserId());
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
        List<Balance> list = balanceRepository.findAll();
        Iterator<Balance> ite = list.iterator();
        while(ite.hasNext()){
            Balance balance = ite.next();
            List<Payment> payments = paymentRepository.findByUserId(balance.getUserId());
            Iterator<Payment> iterator = payments.iterator();
            BigDecimal totalExpand = new BigDecimal("0");
            while(iterator.hasNext()){
                Payment p = iterator.next();
                totalExpand.add(new BigDecimal(p.getPrice()));
            }
            BigDecimal balanceMoney = new BigDecimal(balance.getBalance());
            balance.setBalance(balanceMoney.subtract(totalExpand).toString());
        }

        return list;
    }

    @Override
    public List<Payment> queryPayment(){
        return paymentRepository.findAll();
    }
}
