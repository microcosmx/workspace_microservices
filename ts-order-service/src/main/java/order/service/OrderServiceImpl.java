package order.service;

import order.domain.*;
import order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findOrderById(UUID id){
        return orderRepository.findById(id);
    }

    @Override
    public Order create(Order order){
        ArrayList<Order> accountOrders = orderRepository.findByAccountId(order.getAccountId());
        if(accountOrders.contains(order)){
            System.out.println("[Order-Create-Service][OrderCreate] Fail.Order already exists.");
            return null;
        }else{
            order.setId(UUID.randomUUID());
            orderRepository.save(order);
            System.out.println("[Order-Create-Service][OrderCreate] Success.");
            return order;
        }
    }

    @Override
    public Order alterOrder(OrderAlterInfo oai){
        UUID oldOrderId = oai.getPreviousOrderId();
        Order oldOrder = findOrderById(oldOrderId);
        if(oldOrder == null){
            System.out.println("[Order-Alter-Service][AlterOrder] Fail.Order do not exist.");
            return null;
        }
        oldOrder.setStatus(OrderStatus.CANCEL.getCode());
        saveChanges(oldOrder);
        create(oai.getNewOrderInfo());
        System.out.println("[Order-Alter-Service][AlterOrder] Success.");
        return oai.getNewOrderInfo();
    }

    @Override
    public ArrayList<Order> queryOrders(QueryInfo qi){
        //1.Get all orders of the user
        ArrayList<Order> list = orderRepository.findByAccountId(qi.getAccountId());
        //2.Check is these orders fit the requirement/
        if(qi.isEnableStateQuery() || qi.isEnableBoughtDateQuery() || qi.isEnableTravelDateQuery()){
            ArrayList<Order> finalList = new ArrayList<>();
            for(Order tempOrder : list){
                boolean statePassFlag = false;
                boolean boughtDatePassFlag = false;
                boolean travelDatePassFlag = false;
                //3.Check order state requirement.
                if(qi.isEnableStateQuery()){
                    if(tempOrder.getStatus() != qi.getState()){
                        statePassFlag = false;
                    }else{
                        statePassFlag = true;
                    }
                }else{
                    statePassFlag = true;
                }
                //4.Check order travel date requirement.
                if(qi.isEnableTravelDateQuery()){
                    if(tempOrder.getTravelDate().before(qi.getTravelDateEnd()) &&
                            tempOrder.getTravelDate().after(qi.getBoughtDateStart())){
                        travelDatePassFlag = true;
                    }else{
                        travelDatePassFlag = false;
                    }
                }else{
                    travelDatePassFlag = true;
                }
                //5.Check order bought date requirement.
                if(qi.isEnableBoughtDateQuery()){
                    if(tempOrder.getBoughtDate().before(qi.getBoughtDateEnd()) &&
                            tempOrder.getBoughtDate().after(qi.getBoughtDateStart())){
                        boughtDatePassFlag = true;
                    }else{
                        boughtDatePassFlag = false;
                    }
                }else{
                    boughtDatePassFlag = true;
                }
                //6.check if all requirement fits.
                if(statePassFlag && boughtDatePassFlag && travelDatePassFlag){
                    finalList.add(tempOrder);
                }
            }
            System.out.println("[Order-Query-Service][QueryOrder] Get order num:" + finalList.size());
            return finalList;
        }else{
            System.out.println("[Order-Query-Service][QueryOrder] Get order num:" + list.size());
            return list;
        }
    }

    @Override
    public Order saveChanges(Order order){
        Order oldOrder = findOrderById(order.getId());
        if(oldOrder == null){
            System.out.println("[Order-Modify-Service][ModifyOrder] Fail.Order not found.");
            return null;
        }else{
            oldOrder.setAccountId(order.getAccountId());
            oldOrder.setBoughtDate(order.getBoughtDate());
            oldOrder.setTravelDate(order.getTravelDate());
            oldOrder.setCoachNumber(order.getCoachNumber());
            oldOrder.setSeatClass(order.getSeatClass());
            oldOrder.setSeatNumber(order.getSeatNumber());
            oldOrder.setFrom(order.getFrom());
            oldOrder.setTo(order.getTo());
            oldOrder.setStatus(order.getStatus());
            oldOrder.setTrainNumber(order.getTrainNumber());
            orderRepository.save(oldOrder);
            System.out.println("[Order-Modify-Service] Success.");
            return oldOrder;
        }
    }

    @Override
    public Order cancelOrder(CancelOrderInfo coi){
        UUID orderId = coi.getOrderId();
        Order oldOrder = orderRepository.findById(orderId);
        if(oldOrder == null){
            System.out.println("[Cancel-Order-Service][CancelOrder] Fail.Order not found.");
            return null;
        }else{
            oldOrder.setStatus(OrderStatus.CANCEL.getCode());
            orderRepository.save(oldOrder);
            System.out.println("[Cancel-Order-Service][CancelOrder] Success.");
            return oldOrder;
        }
    }




}

