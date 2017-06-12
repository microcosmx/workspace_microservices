package price.service;

import price.domain.QueryInfo;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/9.
 */
public interface PriceService {
    double query(QueryInfo info);
    List<Double> queryAll();
    String create(QueryInfo info);
    String delete(QueryInfo info);
    String update(QueryInfo info);
}
