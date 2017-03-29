package alter.service;

import alter.domain.Order;
import alter.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderAlterServiceImpl implements OrderAlterService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findOrderById(long id){
        return orderRepository.findById(id);
    }

    @Override
    public ArrayList<Order> findOrdersByAccountId(long accountId){
        return orderRepository.findByAccountId(accountId);
    }

    @Override
    public Order create(Order order){
        ArrayList<Order> accountOrders = orderRepository.findByAccountId(order.getAccountId());
        if(accountOrders.contains(order)){
            return null;
        }else{
            orderRepository.save(order);
            return order;
        }
    }

    @Override
    public Order saveChanges(Order order){
        Order oldOrder = findOrderById(order.getId());
        if(oldOrder == null){
            return null;
        }else{
            oldOrder.setAccountId(order.getAccountId());
            oldOrder.setBoughtDate(order.getBoughtDate());
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

