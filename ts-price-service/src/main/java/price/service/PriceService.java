package price.service;

import price.domain.CreateInfo;
import price.domain.DeleteInfo;
import price.domain.QueryInfo;
import price.domain.ResultPrice;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/9.
 */
public interface PriceService {
    String query(QueryInfo info);
    List<ResultPrice> queryAll();
    String create(CreateInfo info);
    boolean delete(DeleteInfo info);
    String update(CreateInfo info);
}
