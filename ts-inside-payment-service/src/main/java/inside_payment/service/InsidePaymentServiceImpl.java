package inside_payment.service;

import inside_payment.domain.*;
import inside_payment.repository.AddMoneyRepository;
import inside_payment.repository.PaymentRepository;
import inside_payment.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/6/20.
 */
@Service
public class InsidePaymentServiceImpl implements InsidePaymentService{

    @Autowired
    AddMoneyRepository addMoneyRepository;

    @Autowired
    PaymentRepository paymentRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean pay(PaymentInfo info, HttpServletRequest request){
        QueryOrderResult result;
        String userId = CookieUtil.getCookieByName(request,"loginId").getValue();
        if(info.getTripId().startsWith("G") || info.getTripId().startsWith("D")){
             result = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }else{
             result = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }

        if(result.isStatus()){
            Payment payment = new Payment();
            payment.setOrderId(info.getOrderId());
            payment.setPrice(result.getPrice());
            payment.setUserId(userId);

            //判断一下账户余额够不够，不够要去站外支付
            List<Payment> payments = paymentRepository.findByUserId(userId);
            List<AddMoney> addMonies = addMoneyRepository.findByUserId(userId);
            Iterator<Payment> paymentsIterator = payments.iterator();
            Iterator<AddMoney> addMoniesIterator = addMonies.iterator();

            BigDecimal totalExpand = new BigDecimal("0");
            while(paymentsIterator.hasNext()){
                Payment p = paymentsIterator.next();
                totalExpand.add(new BigDecimal(p.getPrice()));
            }
            totalExpand.add(new BigDecimal(result.getPrice()));

            BigDecimal money = new BigDecimal("0");
            while(addMoniesIterator.hasNext()){
                AddMoney addMoney = addMoniesIterator.next();
                money.add(new BigDecimal(addMoney.getMoney()));
            }

            if(totalExpand.compareTo(money) > 0){
                //站外支付
                OutsidePaymentInfo outsidePaymentInfo = new OutsidePaymentInfo();
                outsidePaymentInfo.setOrderId(info.getOrderId());
                outsidePaymentInfo.setUserId(userId);
                outsidePaymentInfo.setPrice(result.getPrice());
                boolean outsidePaySuccess = restTemplate.postForObject(
                        "http://ts-payment-service:19001/payment/pay", outsidePaymentInfo,Boolean.class);
                if(outsidePaySuccess){
                    payment.setType(PaymentType.O);
                    paymentRepository.save(payment);
                    return true;
                }else{
                    return false;
                }
            }else{
                payment.setType(PaymentType.P);
                paymentRepository.save(payment);
            }

                return true;

        }else{
            return false;
        }
    }

    @Override
    public boolean createAccount(CreateAccountInfo info){
        List<AddMoney> list = addMoneyRepository.findByUserId(info.getUserId());
        if(list.size() == 0){
            AddMoney addMoney = new AddMoney();
            addMoney.setMoney(info.getMoney());
            addMoney.setUserId(info.getUserId());
            addMoney.setType(AddMoneyType.A);
            addMoneyRepository.save(addMoney);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean addMoney(AddMoneyInfo info){
        if(addMoneyRepository.findByUserId(info.getUserId()) != null){
            AddMoney addMoney = new AddMoney();
            addMoney.setUserId(info.getUserId());
            addMoney.setMoney(info.getMoney());
            addMoney.setType(AddMoneyType.A);
            addMoneyRepository.save(addMoney);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Balance> queryAccount(){
        List<Balance> result = new ArrayList<Balance>();
        List<AddMoney> list = addMoneyRepository.findAll();
        Iterator<AddMoney> ite = list.iterator();
        HashMap<String,String> map = new HashMap<String,String>();
        while(ite.hasNext()){
            AddMoney addMoney = ite.next();
            if(map.containsKey(addMoney.getUserId())){
                BigDecimal money = new BigDecimal(map.get(addMoney.getUserId()));
                map.put(addMoney.getUserId(),money.add(new BigDecimal(addMoney.getMoney())).toString());
            }else{
                map.put(addMoney.getUserId(),addMoney.getMoney());
            }
        }

        Iterator ite1 = map.entrySet().iterator();
        while(ite1.hasNext()){
            Map.Entry entry = (Map.Entry) ite1.next();
            String userId = (String)entry.getKey();
            String money = (String)entry.getValue();

            List<Payment> payments = paymentRepository.findByUserId(userId);
            Iterator<Payment> iterator = payments.iterator();
            String totalExpand = "0";
            while(iterator.hasNext()){
                Payment p = iterator.next();
                BigDecimal expand = new BigDecimal(totalExpand);
                totalExpand = expand.add(new BigDecimal(p.getPrice())).toString();
            }
            String balanceMoney = new BigDecimal(money).subtract(new BigDecimal(totalExpand)).toString();
            Balance balance = new Balance();
            balance.setUserId(userId);
            balance.setBalance(balanceMoney);
            result.add(balance);
        }

        return result;
    }

    public String queryAccount(String userId){
        List<Payment> payments = paymentRepository.findByUserId(userId);
        List<AddMoney> addMonies = addMoneyRepository.findByUserId(userId);
        Iterator<Payment> paymentsIterator = payments.iterator();
        Iterator<AddMoney> addMoniesIterator = addMonies.iterator();

        BigDecimal totalExpand = new BigDecimal("0");
        while(paymentsIterator.hasNext()){
            Payment p = paymentsIterator.next();
            totalExpand.add(new BigDecimal(p.getPrice()));
        }

        BigDecimal money = new BigDecimal("0");
        while(addMoniesIterator.hasNext()){
            AddMoney addMoney = addMoniesIterator.next();
            money.add(new BigDecimal(addMoney.getMoney()));
        }

        String result = money.subtract(totalExpand).toString();
        return result;
    }

    @Override
    public List<Payment> queryPayment(){
        return paymentRepository.findAll();
    }

    @Override
    public boolean drawBack(DrawBackInfo info){
        if(addMoneyRepository.findByUserId(info.getUserId()) != null){
            AddMoney addMoney = new AddMoney();
            addMoney.setUserId(info.getUserId());
            addMoney.setMoney(info.getMoney());
            addMoney.setType(AddMoneyType.D);
            addMoneyRepository.save(addMoney);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean payDifference(PaymentDifferenceInfo info, HttpServletRequest request){
        QueryOrderResult result;
        String userId = info.getUserId();

        Payment payment = new Payment();
        payment.setOrderId(info.getOrderId());
        payment.setPrice(info.getPrice());
        payment.setUserId(info.getUserId());

        //判断一下账户余额够不够，不够要去站外支付
        List<Payment> payments = paymentRepository.findByUserId(userId);
        List<AddMoney> addMonies = addMoneyRepository.findByUserId(userId);
        Iterator<Payment> paymentsIterator = payments.iterator();
        Iterator<AddMoney> addMoniesIterator = addMonies.iterator();

        BigDecimal totalExpand = new BigDecimal("0");
        while(paymentsIterator.hasNext()){
            Payment p = paymentsIterator.next();
            totalExpand.add(new BigDecimal(p.getPrice()));
        }
        totalExpand.add(new BigDecimal(info.getPrice()));

        BigDecimal money = new BigDecimal("0");
        while(addMoniesIterator.hasNext()){
            AddMoney addMoney = addMoniesIterator.next();
            money.add(new BigDecimal(addMoney.getMoney()));
        }

        if(totalExpand.compareTo(money) > 0){
            //站外支付
            OutsidePaymentInfo outsidePaymentInfo = new OutsidePaymentInfo();
            outsidePaymentInfo.setOrderId(info.getOrderId());
            outsidePaymentInfo.setUserId(userId);
            outsidePaymentInfo.setPrice(info.getPrice());
            boolean outsidePaySuccess = restTemplate.postForObject(
                    "http://ts-payment-service:19001/payment/pay", outsidePaymentInfo,Boolean.class);
            if(outsidePaySuccess){
                payment.setType(PaymentType.E);
                paymentRepository.save(payment);
                return true;
            }else{
                return false;
            }
        }else{
            payment.setType(PaymentType.E);
            paymentRepository.save(payment);
        }

        return true;


    }

    @Override
    public List<AddMoney> queryAddMoney(){
        return addMoneyRepository.findAll();
    }
}
