package cancel.repository;

import cancel.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findById(long id);

    @Query("{ 'accountId' : ?0 }")
    ArrayList<Order> findByAccountId(long accountId);
}
