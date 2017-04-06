package alter.service;

import alter.domain.Order;
import alter.domain.OrderAlterInfo;
import alter.domain.OrderStatus;
import alter.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class OrderAlterServiceImpl implements OrderAlterService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order alterOrder(OrderAlterInfo oai){
        UUID oldOrderId = oai.getPreviousOrderId();
        Order oldOrder = findOrderById(oldOrderId);
        oldOrder.setStatus(OrderStatus.CANCEL.getCode());
        saveChanges(oldOrder);
        create(oai.getNewOrderInfo());
        return oai.getNewOrderInfo();
    }

    public Order findOrderById(UUID id){
        return orderRepository.findById(id);
    }

    public Order create(Order order){
        ArrayList<Order> accountOrders = orderRepository.findByAccountId(order.getAccountId());
        if(accountOrders.contains(order)){
            return null;
        }else{
            orderRepository.save(order);
            return order;
        }
    }

    public Order saveChanges(Order order){
        Order oldOrder = findOrderById(order.getId());
        if(oldOrder == null){
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
            return oldOrder;
        }
    }
}

