package query.service;

import query.domain.Order;
import query.domain.QueryInfo;
import query.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ArrayList<Order> queryOrders(QueryInfo qi){
        return null;
    }

}

