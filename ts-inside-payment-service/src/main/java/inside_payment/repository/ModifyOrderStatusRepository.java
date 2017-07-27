package inside_payment.repository;

import inside_payment.domain.ModifyOrderStatus;
import inside_payment.domain.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by hh on 2017-07-27.
 */
public interface ModifyOrderStatusRepository extends CrudRepository<ModifyOrderStatus,String> {

     List<ModifyOrderStatus> findByAccountId(String accountId);
}
