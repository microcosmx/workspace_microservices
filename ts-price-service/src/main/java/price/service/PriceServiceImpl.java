package price.service;

import org.springframework.stereotype.Service;
import price.domain.QueryInfo;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/9.
 */
@Service
public class PriceServiceImpl implements PriceService{

    @Override
    public double query(QueryInfo info){
        return 0;
    }

    @Override
    public List<Double> queryAll(){
        return null;
    }

    @Override
    public String create(QueryInfo info){
        return null;
    }

    @Override
    public String delete(QueryInfo info){
        return null;
    }

    @Override
    public String update(QueryInfo info){
        return null;
    }
}
