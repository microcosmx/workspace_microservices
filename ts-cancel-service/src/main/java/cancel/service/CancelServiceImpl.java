package cancel.service;

import cancel.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@Service
public class CancelServiceImpl implements CancelService{

    private RestTemplate restTemplate;

    @Override
    public CancelOrderResult cancelOrder(CancelOrderInfo info,String loginToken){
        GetOrderByIdInfo getFromOrderInfo = new GetOrderByIdInfo();
        getFromOrderInfo.setOrderId(info.getOrderId());
        GetOrderResult orderResult = getOrderByIdFromOrder(getFromOrderInfo);
        if(orderResult.isStatus() == true){
            Order order = orderResult.getOrder();
            if(order.getStatus() == OrderStatus.NOTPAID.getCode()
                    || order.getStatus() == OrderStatus.PAID.getCode()){

                order.setStatus(OrderStatus.CANCEL.getCode());
                ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
                changeOrderInfo.setLoginToken(loginToken);
                changeOrderInfo.setOrder(order);
                ChangeOrderResult changeOrderResult = cancelFromOrder(changeOrderInfo);
                if(changeOrderResult.isStatus() == true){
                    CancelOrderResult finalResult = new CancelOrderResult();
                    finalResult.setStatus(true);
                    finalResult.setMessage("Success.");
                    return finalResult;
                }else{
                    CancelOrderResult finalResult = new CancelOrderResult();
                    finalResult.setStatus(false);
                    finalResult.setMessage(changeOrderResult.getMessage());
                    return finalResult;
                }

            }else{
                CancelOrderResult result = new CancelOrderResult();
                result.setStatus(false);
                result.setMessage("Order Status Cancel Not Permitted");
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

                    order.setStatus(OrderStatus.CANCEL.getCode());
                    ChangeOrderInfo changeOrderInfo = new ChangeOrderInfo();
                    changeOrderInfo.setLoginToken(loginToken);
                    changeOrderInfo.setOrder(order);
                    ChangeOrderResult changeOrderResult = cancelFromOtherOrder(changeOrderInfo);
                    if(changeOrderResult.isStatus() == true){
                        CancelOrderResult finalResult = new CancelOrderResult();
                        finalResult.setStatus(true);
                        finalResult.setMessage("Success.");
                        return finalResult;
                    }else{
                        CancelOrderResult finalResult = new CancelOrderResult();
                        finalResult.setStatus(false);
                        finalResult.setMessage(changeOrderResult.getMessage());
                        return finalResult;
                    }
                }else{
                    CancelOrderResult result = new CancelOrderResult();
                    result.setStatus(false);
                    result.setMessage("Order Status Cancel Not Permitted");
                    return result;
                }
            }else{
                CancelOrderResult result = new CancelOrderResult();
                result.setStatus(false);
                result.setMessage("Order Not Found");
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
                    return result;
                }else{
                    CalculateRefundResult result = new CalculateRefundResult();
                    result.setStatus(true);
                    result.setMessage("Success");
                    result.setRefund(calculateRefund(order));
                    return result;
                }
            }else{
                CalculateRefundResult result = new CalculateRefundResult();
                result.setStatus(false);
                result.setMessage("Order Status Cancel Not Permitted");
                result.setRefund("0");
                return result;
            }
        }else{
            GetOrderByIdInfo getFromOtherOrderInfo = new GetOrderByIdInfo();
            getFromOtherOrderInfo.setOrderId(info.getOrderId());
            GetOrderResult orderOtherResult = getOrderByIdFromOrderOther(getFromOtherOrderInfo);
            if(orderOtherResult.isStatus() == true){
                Order order = orderResult.getOrder();
                if(order.getStatus() == OrderStatus.NOTPAID.getCode()
                        || order.getStatus() == OrderStatus.PAID.getCode()){
                    if(order.getStatus() == OrderStatus.NOTPAID.getCode()){
                        CalculateRefundResult result = new CalculateRefundResult();
                        result.setStatus(true);
                        result.setMessage("Success");
                        result.setRefund("0");
                        return result;
                    }else{
                        CalculateRefundResult result = new CalculateRefundResult();
                        result.setStatus(true);
                        result.setMessage("Success");
                        result.setRefund(calculateRefund(order));
                        return result;
                    }
                }else{
                    CalculateRefundResult result = new CalculateRefundResult();
                    result.setStatus(false);
                    result.setMessage("Order Status Cancel Not Permitted");
                    result.setRefund("0");
                    return result;
                }
            }else{
                CalculateRefundResult result = new CalculateRefundResult();
                result.setStatus(false);
                result.setMessage("Order Not Found");
                result.setRefund("0");
                return result;
            }
        }
    }

    private String calculateRefund(Order order){
        Date nowDate = new Date();
        Date startTime = new Date(order.getTravelDate().getYear(),
                                  order.getTravelDate().getMonth(),
                                  order.getTravelDate().getDay(),
                                  order.getTravelTime().getHours(),
                                  order.getTravelTime().getMinutes());
        if(nowDate.after(startTime)){
            return "0";
        }else{
            double totalPrice = Double.parseDouble(order.getPrice());
            double price = totalPrice * 0.8;
            DecimalFormat priceFormat = new java.text.DecimalFormat("0.00");
            String str = priceFormat.format(price);
            System.out.println("[Cancel Order]calculate refund - " + str);
            return str;
        }
    }

    private ChangeOrderResult cancelFromOrder(ChangeOrderInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Cancel Order Service][Get Contacts By Id] Getting....");
        ChangeOrderResult result = restTemplate.postForObject("http://ts-order-service:12031/order/update",info,ChangeOrderResult.class);
        return result;
    }

    private ChangeOrderResult cancelFromOtherOrder(ChangeOrderInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Cancel Order Service][Get Contacts By Id] Getting....");
        ChangeOrderResult result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",info,ChangeOrderResult.class);
        return result;
    }


    private GetOrderResult getOrderByIdFromOrder(GetOrderByIdInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Cancel Order Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

    private GetOrderResult getOrderByIdFromOrderOther(GetOrderByIdInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Cancel Order Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

}
