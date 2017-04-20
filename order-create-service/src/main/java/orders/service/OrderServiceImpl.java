package orders.service;

import orders.domain.Order;
import orders.repository.OrderRepository;
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

}

