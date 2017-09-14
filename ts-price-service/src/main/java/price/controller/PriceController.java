package price.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import price.domain.CreateInfo;
import price.domain.DeleteInfo;
import price.domain.QueryInfo;
import price.domain.ResultPrice;
import price.service.PriceService;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/9.
 */
@RestController
public class PriceController {
    @Autowired
    PriceService service;

    @RequestMapping(value="/price/query", method= RequestMethod.POST)
    public String query(@RequestBody QueryInfo info){
        return service.query(info);
    }

    @RequestMapping(value="/price/queryAll", method= RequestMethod.GET)
    public List<ResultPrice> queryAll(){
        return service.queryAll();
    }

    @RequestMapping(value="/price/create", method= RequestMethod.POST)
    public String create(@RequestBody CreateInfo info){
        return service.create(info);
    }

    @RequestMapping(value="/price/delete", method= RequestMethod.POST)
    public boolean delete(@RequestBody DeleteInfo info){
        return service.delete(info);
    }

    @RequestMapping(value="/price/update", method= RequestMethod.POST)
    public String update(@RequestBody CreateInfo info){
        return service.update(info);
    }
}
