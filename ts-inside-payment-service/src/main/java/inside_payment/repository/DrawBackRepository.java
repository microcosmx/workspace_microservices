package inside_payment.repository;

import inside_payment.domain.DrawBack;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
public interface DrawBackRepository extends CrudRepository<DrawBack,String> {
    List<DrawBack> findAll();
    DrawBack findByOrderId(String orderId);
}
