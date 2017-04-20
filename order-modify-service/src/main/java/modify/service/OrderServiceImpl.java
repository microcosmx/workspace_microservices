package modify.service;

import modify.domain.Order;
import modify.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}

