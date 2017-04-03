package query.service;

import query.domain.Order;
import query.domain.QueryInfo;
import java.util.ArrayList;

public interface OrderQueryService {

    ArrayList<Order> queryOrders(QueryInfo qi);

}
