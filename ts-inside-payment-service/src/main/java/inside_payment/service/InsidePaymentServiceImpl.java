package inside_payment.service;

import inside_payment.async.AsyncTask;
import inside_payment.domain.*;
import inside_payment.repository.AddMoneyRepository;
import inside_payment.repository.DrawBackRepository;
import inside_payment.repository.PaymentRepository;
import inside_payment.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/6/20.
 */
@Service
public class InsidePaymentServiceImpl implements InsidePaymentService{

    @Autowired
    public AddMoneyRepository addMoneyRepository;

    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public DrawBackRepository drawBackRepository;

    @Autowired
    AsyncTask asyncTask;

    public final AtomicLong equal = new AtomicLong();

    @Override
    public boolean pay(PaymentInfo info, HttpServletRequest request){
//        QueryOrderResult result;
        String userId = CookieUtil.getCookieByName(request,"loginId").getValue();

        GetOrderByIdInfo getOrderByIdInfo = new GetOrderByIdInfo();
        getOrderByIdInfo.setOrderId(info.getOrderId());
        GetOrderResult result;

        if(info.getTripId().startsWith("G") || info.getTripId().startsWith("D")){
            result = restTemplate.postForObject("http://ts-order-service:12031/order/getById",getOrderByIdInfo,GetOrderResult.class);
             //result = restTemplate.postForObject(
             //       "http://ts-order-service:12031/order/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }else{
            result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/getById",getOrderByIdInfo,GetOrderResult.class);
            //result = restTemplate.postForObject(
            //      "http://ts-order-other-service:12032/orderOther/price", new QueryOrder(info.getOrderId()),QueryOrderResult.class);
        }

        if(result.isStatus()){

            if(result.getOrder().getStatus() != OrderStatus.NOTPAID.getCode()){
                System.out.println("[Inside Payment Service][Pay] Error. Order status Not allowed to Pay.");
                return false;
            }

            Payment payment = new Payment();
            payment.setOrderId(info.getOrderId());
            payment.setPrice(result.getOrder().getPrice());
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
            totalExpand.add(new BigDecimal(result.getOrder().getPrice()));

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
                outsidePaymentInfo.setPrice(result.getOrder().getPrice());
                boolean outsidePaySuccess = restTemplate.postForObject(
                        "http://ts-payment-service:19001/payment/pay", outsidePaymentInfo,Boolean.class);
                if(outsidePaySuccess){
                    payment.setType(PaymentType.O);
                    paymentRepository.save(payment);
                    setOrderStatus(info.getTripId(),info.getOrderId());
                    return true;
                }else{
                    return false;
                }
            }else{
                setOrderStatus(info.getTripId(),info.getOrderId());
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
    public boolean drawBack(DrawBackInfo info)  {
        equal.set(0);
        //设置订单状态为已退款
        GetOrderByIdInfo getOrderInfo = new GetOrderByIdInfo();
        System.out.println();
        System.out.println("info.getOrderId()"+info.getOrderId());
        System.out.println();
        getOrderInfo.setOrderId(info.getOrderId());
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/getById/"
                ,getOrderInfo,GetOrderResult.class);
        Order order = cor.getOrder();

        order.setStatus(OrderStatus.CANCEL.getCode());
        ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
        changeOrderInfo.setOrder(order);
        changeOrderInfo.setLoginToken(info.getLoginToken());
        ChangeOrderResult changeOrderResult = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",changeOrderInfo,ChangeOrderResult.class);

        //退款记录
        DrawBack drawBack = new DrawBack();
        drawBack.setLoginToken(info.getLoginToken());
        drawBack.setMoney(info.getMoney());
        drawBack.setOrderId(info.getOrderId());
        drawBack.setUserId(info.getUserId());

        drawBackRepository.save(drawBack);

//        CallInsidePaymentInfo callInsidePaymentInfo = new CallInsidePaymentInfo();
//        callInsidePaymentInfo.setTime(Math.random()*8);
//        try{
//            Thread.sleep((long)callInsidePaymentInfo.getTime());
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//
//        restTemplate.postForObject("http://ts-security-service:11188/security/callInsidePayment",
//                callInsidePaymentInfo , Boolean.class);


        if(addMoneyRepository.findByUserId(info.getUserId()) != null){
            AddMoney addMoney = new AddMoney();
            addMoney.setUserId(info.getUserId());
            addMoney.setMoney(info.getMoney());
            addMoney.setType(AddMoneyType.D);
            addMoneyRepository.save(addMoney);

            try {
                Future<ChangeOrderResult> changeOrderResultFuture = asyncTask.reCalculateRefundMoney(info.getOrderId(), info.getMoney(), info.getLoginToken());
                ChangeOrderResult changeOrderResult1 = changeOrderResultFuture.get();
//                reCalculateRefundMoney(info.getOrderId(), info.getMoney(), info.getLoginToken());
                System.out.println("reCalculateRefundMoney:Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e){
                e.printStackTrace();
            }


            return true;
        }else{
            try {
//                Future<ChangeOrderResult> changeOrderResultFuture = asyncTask.reCalculateRefundMoney(info.getOrderId(), info.getMoney(), info.getLoginToken());
////                ChangeOrderResult changeOrderResult1 = changeOrderResultFuture.get();
//                while(!changeOrderResultFuture.isDone()){
//
//                }
                reCalculateRefundMoney(info.getOrderId(), info.getMoney(), info.getLoginToken());
                System.out.println("reCalculateRefundMoney:Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    private ModifyOrderStatusResult setOrderStatus(String tripId,String orderId){
        ModifyOrderStatusInfo info = new ModifyOrderStatusInfo();
        info.setOrderId(orderId);
        info.setStatus(1);   //order paid and not collected

        ModifyOrderStatusResult result;
        if(tripId.startsWith("G") || tripId.startsWith("D")){
            result = restTemplate.postForObject(
                    "http://ts-order-service:12031/order/modifyOrderStatus", info, ModifyOrderStatusResult.class);
        }else{
            result = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/modifyOrderStatus", info, ModifyOrderStatusResult.class);
        }
        return result;
    }

    @Override
    public boolean check(){

        List<DrawBack> drawBacks = drawBackRepository.findAll();
        drawBacks.forEach(n -> {
            GetOrderByIdInfo getOrderInfo = new GetOrderByIdInfo();
            getOrderInfo.setOrderId(n.getOrderId());
            GetOrderResult cor = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/getById/"
                    ,getOrderInfo,GetOrderResult.class);
            Order order = cor.getOrder();

            order.setStatus(OrderStatus.CANCEL.getCode());
            ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
            changeOrderInfo.setOrder(order);
            changeOrderInfo.setLoginToken(n.getLoginToken());
            ChangeOrderResult changeOrderResult = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",
                    changeOrderInfo,ChangeOrderResult.class);
        });
        return true;
    }


    private ChangeOrderResult reCalculateRefundMoney(String orderId, String money, String loginToken) throws InterruptedException{
        ChangeOrderResult changeOrderResult = null;
        CancelOrderInfo info = new CancelOrderInfo();
        info.setOrderId(orderId);
        String result = restTemplate.postForObject("http://ts-cancel-service:18885/cancelCalculateRefund2",info,String.class);

        System.out.println();
        System.out.println("money:"+money);
        System.out.println("result.getRefund():"+result);
        System.out.println();

        if(!result.equals(money)){
            equal.set(2);
            Thread.sleep(20000);
            DrawBack drawBack = drawBackRepository.findByOrderId(orderId);

            addMoneyRepository.deleteByUserIdAndMoney(drawBack.getUserId(),money);

            AddMoney addMoney = new AddMoney();
            addMoney.setUserId(drawBack.getUserId());
            addMoney.setMoney(result);
            addMoney.setType(AddMoneyType.D);
            addMoneyRepository.save(addMoney);

            //设置订单状态为已退款
            GetOrderByIdInfo getOrderInfo = new GetOrderByIdInfo();
            getOrderInfo.setOrderId(orderId);
            GetOrderResult cor = restTemplate.postForObject(
                    "http://ts-order-other-service:12032/orderOther/getById"
                    ,getOrderInfo,GetOrderResult.class);
            Order order = cor.getOrder();


            order.setStatus(OrderStatus.CANCEL.getCode());
            ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
            changeOrderInfo.setOrder(order);
            changeOrderInfo.setLoginToken(loginToken);
            System.out.println();
            System.out.println("http://ts-order-other-service:12032/orderOther/update before");
            System.out.println();
            changeOrderResult = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",changeOrderInfo,ChangeOrderResult.class);
            System.out.println();
            System.out.println("http://ts-order-other-service:12032/orderOther/update after");
            System.out.println();
        }else{
            equal.set(1);
            changeOrderResult = new ChangeOrderResult();
            changeOrderResult.setStatus(false);
        }
        return changeOrderResult;
    }

    @Override
    public boolean equal(){
        if(equal.get() == 1){
            equal.set(2);
        }else if(equal.get() == 2){
            equal.set(1);
        }
        return true;
    }

//    private boolean sendOrderCreateEmail(){
//        result = restTemplate.postForObject(
//                "http://ts-notification-service:12031/order/modifyOrderStatus", info, ModifyOrderStatusResult.class);
//        return true;
//    }
}
