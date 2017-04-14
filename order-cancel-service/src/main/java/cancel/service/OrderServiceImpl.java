package cancel.service;

import cancel.domain.CancelOrderInfo;
import cancel.domain.Order;
import cancel.domain.OrderStatus;
import cancel.repository.OrderRepository;
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
    public Order cancelOrder(CancelOrderInfo coi){
        UUID orderId = coi.getOrderId();
        Order oldOrder = orderRepository.findById(orderId);
        if(oldOrder == null){
            return null;
        }else{
            oldOrder.setStatus(OrderStatus.CANCEL.getCode());
            orderRepository.save(oldOrder);
            return oldOrder;
        }
    }

}

