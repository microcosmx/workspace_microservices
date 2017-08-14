package cancel.service;

import cancel.async.AsyncTask;
import cancel.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.Future;

@Service
public class CancelServiceImpl implements CancelService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncTask asyncTask;

    @Override
    public CancelOrderResult cancelOrder(CancelOrderInfo info,String loginToken,String loginId) throws Exception{

        boolean wrongStatus = false;

        GetOrderByIdInfo getFromOrderInfo = new GetOrderByIdInfo();
        getFromOrderInfo.setOrderId(info.getOrderId());
        GetOrderResult orderResult = getOrderByIdFromOrder(getFromOrderInfo);
        if(orderResult.isStatus() == true){
            System.out.println("[Cancel Order Service][Cancel Order] Order found G|H");
            Order order = orderResult.getOrder();
            if(order.getStatus() == OrderStatus.NOTPAID.getCode()
                    || order.getStatus() == OrderStatus.PAID.getCode() || order.getStatus() == OrderStatus.CHANGE.getCode()){

                order.setStatus(OrderStatus.CANCEL.getCode());
                ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
                changeOrderInfo.setLoginToken(loginToken);
                changeOrderInfo.setOrder(order);
                ChangeOrderResult changeOrderResult = cancelFromOrder(changeOrderInfo);
                if(changeOrderResult.isStatus() == true){
                    CancelOrderResult finalResult = new CancelOrderResult();
                    finalResult.setStatus(true);
                    finalResult.setMessage("Success.");
                    System.out.println("[Cancel Order Service][Cancel Order] Success.");
                    //Draw back money
                    String money = calculateRefund(order);
                    boolean status = drawbackMoney(money,loginId);
                    if(status == true){
                        System.out.println("[Cancel Order Service][Draw Back Money] Success.");
                    }else{
                        System.out.println("[Cancel Order Service][Draw Back Money] Fail.");
                    }
                    checkStatus(wrongStatus);
                    return finalResult;
                }else{
                    CancelOrderResult finalResult = new CancelOrderResult();
                    finalResult.setStatus(false);
                    finalResult.setMessage(changeOrderResult.getMessage());
                    System.out.println("[Cancel Order Service][Cancel Order] Fail.Reason:" + changeOrderResult.getMessage());
                    checkStatus(wrongStatus);
                    return finalResult;
                }

            }else{
                CancelOrderResult result = new CancelOrderResult();
                result.setStatus(false);
                result.setMessage("Order Status Cancel Not Permitted");
                System.out.println("[Cancel Order Service][Cancel Order] Order Status Not Permitted.");
                checkStatus(wrongStatus);
                return result;
            }
        }else{
            GetOrderByIdInfo getFromOtherOrderInfo = new GetOrderByIdInfo();
            getFromOtherOrderInfo.setOrderId(info.getOrderId());
            GetOrderResult orderOtherResult = getOrderByIdFromOrderOther(getFromOtherOrderInfo);
            if(orderOtherResult.isStatus() == true){
                System.out.println("[Cancel Order Service][Cancel Order] Order found Z|K|Other");

                Order order = orderOtherResult.getOrder();
                if(order.getStatus() == OrderStatus.NOTPAID.getCode()
                        || order.getStatus() == OrderStatus.PAID.getCode() || order.getStatus() == OrderStatus.CHANGE.getCode()){

                    System.out.println("[Cancel Order Service][Cancel Order] Order status ok");

                    order.setStatus(OrderStatus.CANCEL.getCode());
                    ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
                    changeOrderInfo.setLoginToken(loginToken);
                    changeOrderInfo.setOrder(order);
//                    ChangeOrderResult changeOrderResult = cancelFromOtherOrder(changeOrderInfo);


                    /***********************Fault Reproduce - Error Process Seq*************************/
                    /**
                     * 订单取消首先退款，然后重置订单状态为已取消，二使用异步操作进行
                     * 退款流程前期使用时间太长导致，在检查订单状态的时候发现订单状态已经处于不能退款的状态，导致退款失败
                     * 由于先发生的退款操作异步时间太长，使得修改订单状态的操作抢先完成，使得退款不能正常进行
                     */

                    Future<ChangeOrderResult> cancellingTask = asyncTask.cancelling(loginId,order.getId().toString(),loginToken);

                    String money = calculateRefund(order);
                    Future<Boolean> drawBackMoneyTask = asyncTask.drawBackMoney(money,loginId,order.getId().toString(),loginToken);

//                    //2.然后修改订单的状态至【已取消】（将订单状态改为-已退款）
//                    Future<ChangeOrderResult> taskCancelOrder = asyncTask.updateOtherOrderStatusToCancel(changeOrderInfo);

                    ChangeOrderResult changeOrderResult = cancellingTask.get();
                    boolean drawBackMoneyStatus = drawBackMoneyTask.get();

                    List<Order> list = queryOtherOrder(loginId,loginToken);
                    Iterator<Order> iterator = list.iterator();
                    Order o;

                    while(iterator.hasNext()){
                        o = iterator.next();
                        if(o.getStatus() == OrderStatus.Canceling.getCode()){
                            wrongStatus = true;
                        }

                    }

                    /********************************************************************************/
                    if(drawBackMoneyStatus == true){

                        CancelOrderResult finalResult = new CancelOrderResult();
                        finalResult.setStatus(true);
                        finalResult.setMessage("Success.");
                        System.out.println("[Cancel Order Service][Cancel Order] Success.");
                        System.out.println("[Cancel Order Service][Draw Back Money] Success.");
                        checkStatus(wrongStatus);
                        return finalResult;
                    }else{
                        CancelOrderResult finalResult = new CancelOrderResult();
                        finalResult.setStatus(false);
                        finalResult.setMessage("Fail.");
                        System.out.println("[Cancel Order Service][Cancel Order] Success.");
                        System.out.println("[Cancel Order Service][Draw Back Money] Fail.");
                        checkStatus(wrongStatus);
                        return finalResult;
                    }
//
//                    if(changeOrderResult.isStatus() == true){
//                        CancelOrderResult finalResult = new CancelOrderResult();
//                        finalResult.setStatus(true);
//                        finalResult.setMessage("Success.");
//                        System.out.println("[Cancel Order Service][Cancel Order] Success.");
//                        //Draw back money
//                        String money = calculateRefund(order);
//                        boolean status = drawbackMoney(money,loginId);
//                        if(status == true){
//                            System.out.println("[Cancel Order Service][Draw Back Money] Success.");
//                        }else{
//                            System.out.println("[Cancel Order Service][Draw Back Money] Fail.");
//                        }
//                        return finalResult;
//                    }else{
//                        CancelOrderResult finalResult = new CancelOrderResult();
//                        finalResult.setStatus(false);
//                        finalResult.setMessage(changeOrderResult.getMessage());
//                        System.out.println("[Cancel Order Service][Cancel Order] Fail.Reason:" + changeOrderResult.getMessage());
//                        return finalResult;
//                    }
                }else{
                    CancelOrderResult result = new CancelOrderResult();
                    result.setStatus(false);
                    result.setMessage("Order Status Cancel Not Permitted");
                    System.out.println("[Cancel Order Service][Cancel Order] Order Status Not Permitted.");
                    checkStatus(wrongStatus);
                    return result;
                }
            }else{
                CancelOrderResult result = new CancelOrderResult();
                result.setStatus(false);
                result.setMessage("Order Not Found");
                System.out.println("[Cancel Order Service][Cancel Order] Order Not Found.");
                checkStatus(wrongStatus);
                return result;
            }
        }
    }


    public CalculateRefundResult calculateRefund(CancelOrderInfo info){
        GetOrderByIdInfo getFromOrderInfo = new GetOrderByIdInfo();
        getFromOrderInfo.setOrderId(info.getOrderId());
        GetOrderResult orderResult = getOrderByIdFromOrder(getFromOrderInfo);
        if(orderResult.isStatus() == true){
            Order order = orderResult.getOrder();
            if(order.getStatus() == OrderStatus.NOTPAID.getCode()
                    || order.getStatus() == OrderStatus.PAID.getCode()){
                if(order.getStatus() == OrderStatus.NOTPAID.getCode()){
                    CalculateRefundResult result = new CalculateRefundResult();
                    result.setStatus(true);
                    result.setMessage("Success");
                    result.setRefund("0");
                    System.out.println("[Cancel Order][Refund Price] From Order Service.Not Paid.");
                    return result;
                }else{
                    CalculateRefundResult result = new CalculateRefundResult();
                    result.setStatus(true);
                    result.setMessage("Success");
                    result.setRefund(calculateRefund(order));
                    System.out.println("[Cancel Order][Refund Price] From Order Service.Paid.");
                    return result;
                }
            }else{
                CalculateRefundResult result = new CalculateRefundResult();
                result.setStatus(false);
                result.setMessage("Order Status Cancel Not Permitted");
                result.setRefund("error");
                System.out.println("[Cancel Order][Refund Price] Order. Cancel Not Permitted.");

                return result;
            }
        }else{
            GetOrderByIdInfo getFromOtherOrderInfo = new GetOrderByIdInfo();
            getFromOtherOrderInfo.setOrderId(info.getOrderId());
            GetOrderResult orderOtherResult = getOrderByIdFromOrderOther(getFromOtherOrderInfo);
            if(orderOtherResult.isStatus() == true){
                Order order = orderOtherResult.getOrder();
                if(order.getStatus() == OrderStatus.NOTPAID.getCode()
                        || order.getStatus() == OrderStatus.PAID.getCode()){
                    if(order.getStatus() == OrderStatus.NOTPAID.getCode()){
                        CalculateRefundResult result = new CalculateRefundResult();
                        result.setStatus(true);
                        result.setMessage("Success");
                        result.setRefund("0");
                        System.out.println("[Cancel Order][Refund Price] From Order Other Service.Not Paid.");
                        return result;
                    }else{
                        CalculateRefundResult result = new CalculateRefundResult();
                        result.setStatus(true);
                        result.setMessage("Success");
                        result.setRefund(calculateRefund(order));
                        System.out.println("[Cancel Order][Refund Price] From Order Other Service.Paid.");
                        return result;
                    }
                }else{
                    CalculateRefundResult result = new CalculateRefundResult();
                    result.setStatus(false);
                    result.setMessage("Order Status Cancel Not Permitted");
                    result.setRefund("error");
                    System.out.println("[Cancel Order][Refund Price] Order Other. Cancel Not Permitted.");
                    return result;
                }
            }else{
                CalculateRefundResult result = new CalculateRefundResult();
                result.setStatus(false);
                result.setMessage("Order Not Found");
                result.setRefund("error");
                System.out.println("[Cancel Order][Refund Price] Order not found.");
                return result;
            }
        }
    }

    private String calculateRefund(Order order){
        if(order.getStatus() == OrderStatus.NOTPAID.getCode()){
            return "0.00";
        }
        System.out.println("[Cancel Order] Order Travel Date:" + order.getTravelDate().toString());
        Date nowDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(order.getTravelDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(order.getTravelTime());
        int hour = cal2.get(Calendar.HOUR);
        int minute = cal2.get(Calendar.MINUTE);
        int second = cal2.get(Calendar.SECOND);
        Date startTime = new Date(year,
                                  month,
                                  day,
                                  hour,
                                  minute,
                                  second);
        System.out.println("[Cancel Order] nowDate  :" + nowDate.toString());
        System.out.println("[Cancel Order] startTime:" + startTime.toString());
        if(nowDate.after(startTime)){
            System.out.println("[Cancel Order] Ticket expire refund 0");
            return "0";
        }else{
            double totalPrice = Double.parseDouble(order.getPrice());
            double price = totalPrice * 0.8;
            if(Math.random()<0.5){
                price = totalPrice * 0.7;
            }
            DecimalFormat priceFormat = new java.text.DecimalFormat("0.00");
            String str = priceFormat.format(price);
            System.out.println("[Cancel Order]calculate refund - " + str);
            return str;
        }
    }


    private ChangeOrderResult cancelFromOrder(ChangeOrderInfo info){
        System.out.println("[Cancel Order Service][Change Order Status] Changing....");
        ChangeOrderResult result = restTemplate.postForObject("http://ts-order-service:12031/order/update",info,ChangeOrderResult.class);
        return result;
    }

    private ChangeOrderResult cancelFromOtherOrder(ChangeOrderInfo info){
        System.out.println("[Cancel Order Service][Change Order Status] Changing....");
        ChangeOrderResult result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",info,ChangeOrderResult.class);
        return result;
    }

    public boolean drawbackMoney(String money,String userId){
        System.out.println("[Cancel Order Service][Draw Back Money] Draw back money...");
        DrawBackInfo info = new DrawBackInfo();
        info.setMoney(money);
        info.setUserId(userId);
        String result = restTemplate.postForObject("http://ts-inside-payment-service:18673/inside_payment/drawBack",info,String.class);
        if(result.equals("true")){
            return true;
        }else{
            return false;
        }
    }


    private GetOrderResult getOrderByIdFromOrder(GetOrderByIdInfo info){
        System.out.println("[Cancel Order Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

    private GetOrderResult getOrderByIdFromOrderOther(GetOrderByIdInfo info){
        System.out.println("[Cancel Order Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

    private List<Order> queryOtherOrder(String loginId, String loginToken){

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","loginId=" + loginId);
        requestHeaders.add("Cookie","loginToken=" + loginToken);
        QueryInfo info = new QueryInfo();
        HttpEntity<QueryInfo> requestEntity = new HttpEntity(info, requestHeaders);

        ResponseEntity rssResponse = restTemplate.exchange("http://ts-order-other-service:12032/orderOther/query", HttpMethod.POST, requestEntity, List.class);
        List<Order> list = (List)rssResponse.getBody();

        return list;
    }

    private void checkStatus(boolean wrongStatus) throws Exception{
        if(wrongStatus){
            throw new Exception("status error!!");
        }
    }

}
