package execute.serivce;

import execute.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExecuteServiceImpl implements ExecuteService{

    private RestTemplate restTemplate;

    @Override
    public TicketExecuteResult ticketExecute(TicketExecuteInfo info){
        //1.获取订单信息
        GetOrderByIdInfo getOrderByIdInfo = new GetOrderByIdInfo();
        getOrderByIdInfo.setOrderId(info.getOrderId());
        GetOrderResult resultFromOrder = getOrderByIdFromOrder(getOrderByIdInfo);
        TicketExecuteResult result = new TicketExecuteResult();
        Order order;
        if(resultFromOrder.isStatus() == true){
            order = resultFromOrder.getOrder();
            //2.检查订单是否可以进站
            if(order.getStatus() != OrderStatus.COLLECTED.getCode()){
                result.setStatus(false);
                result.setMessage("Order Status Wrong");
                return result;
            }
            //3.确认进站 请求修改订单信息
            ExecuteOrderInfo executeInfo = new ExecuteOrderInfo();
            executeInfo.setOrderId(info.getOrderId());
            ExecuteOrderResult resultExecute = executeOrder(executeInfo);
            if(resultExecute.isStatus() == true){
                result.setStatus(true);
                result.setMessage("Success.");
                return result;
            }else{
                result.setStatus(false);
                result.setMessage(resultExecute.getMessage());
                return result;
            }
        }else{
            resultFromOrder = getOrderByIdFromOrderOther(getOrderByIdInfo);
            if(resultFromOrder.isStatus() == true){
                order = resultFromOrder.getOrder();
                //2.检查订单是否可以进站
                if(order.getStatus() != OrderStatus.COLLECTED.getCode()){
                    result.setStatus(false);
                    result.setMessage("Order Status Wrong");
                    return result;
                }
                //3.确认进站 请求修改订单信息
                ExecuteOrderInfo executeInfo = new ExecuteOrderInfo();
                executeInfo.setOrderId(info.getOrderId());
                ExecuteOrderResult resultExecute = executeOrderOther(executeInfo);
                if(resultExecute.isStatus() == true){
                    result.setStatus(true);
                    result.setMessage("Success.");
                    return result;
                }else{
                    result.setStatus(false);
                    result.setMessage(resultExecute.getMessage());
                    return result;
                }
            }else{
                result.setStatus(false);
                result.setMessage("Order Not Found");
                return result;
            }
        }

    }

    private ExecuteOrderResult executeOrder(ExecuteOrderInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Execute Service][Execute Order] Executing....");
        ExecuteOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/execute"
                ,info,ExecuteOrderResult.class);
        return cor;
    }

    private ExecuteOrderResult executeOrderOther(ExecuteOrderInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Execute Service][Execute Order] Executing....");
        ExecuteOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/execute"
                ,info,ExecuteOrderResult.class);
        return cor;
    }

    private GetOrderResult getOrderByIdFromOrder(GetOrderByIdInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Execute Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

    private GetOrderResult getOrderByIdFromOrderOther(GetOrderByIdInfo info){
        restTemplate = new RestTemplate();
        System.out.println("[Execute Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

}
