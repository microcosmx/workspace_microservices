package travel2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import travel2.domain.*;
import travel2.service.Travel2Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenjie Xu on 2017/6/7.
 */
@RestController
public class Travel2Controller {

    @Autowired
    private Travel2Service service;

    @RequestMapping(value="/travel2/create", method= RequestMethod.POST)
    public String create(@RequestBody Information info){
        return service.create(info);
    }

    //只返回Trip，不会返回票数信息
    @RequestMapping(value="/travel2/retrieve", method= RequestMethod.POST)
    public Trip retrieve(@RequestBody Information2 info){
        return service.retrieve(info);
    }

    @RequestMapping(value="/travel2/update", method= RequestMethod.POST)
    public String update(@RequestBody Information info){
        return service.update(info);
    }

    @RequestMapping(value="/travel2/delete", method= RequestMethod.POST)
    public String delete(@RequestBody Information2 info){
        return service.delete(info);
    }

    //返回Trip以及剩余票数
    @RequestMapping(value="/travel2/query", method= RequestMethod.POST)
    public List<TripResponse> query(@RequestBody QueryInfo info){
        if(info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
                info.getDepartureTime() == null){
            System.out.println("[Travel Other Service][Travel Query] Fail.Something null.");
            List<TripResponse> errorList = new ArrayList<>();
            return errorList;
        }
        System.out.println("[Travel2 Service] Query TripResponse");
        return service.query(info);
    }

    //返回Trip以及剩余票数
    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel2/getTripAllDetailInfo", method= RequestMethod.POST)
    public GetTripAllDetailResult getTripAllDetailInfo(@RequestBody GetTripAllDetailInfo gtdi){
        return service.getTripAllDetailInfo(gtdi);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel2/queryAll", method= RequestMethod.GET)
    public List<Trip> queryAll(){
        return service.queryAll();
    }
}
