package price.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import price.domain.QueryInfo;
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
    public double query(@RequestBody QueryInfo info){
        return service.query(info);
    }

    @RequestMapping(value="/price/queryAll", method= RequestMethod.GET)
    public List<Double> queryAll(){
        return service.queryAll();
    }

    @RequestMapping(value="/price/create", method= RequestMethod.POST)
    public String create(@RequestBody QueryInfo info){
        return service.create(info);
    }

    @RequestMapping(value="/price/delete", method= RequestMethod.POST)
    public String delete(@RequestBody QueryInfo info){
        return service.delete(info);
    }

    @RequestMapping(value="/price/update", method= RequestMethod.POST)
    public String update(@RequestBody QueryInfo info){
        return service.update(info);
    }
}
