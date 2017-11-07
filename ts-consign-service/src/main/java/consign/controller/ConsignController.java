package consign.controller;

import consign.domain.ConsignRecord;
import consign.domain.ConsignRequest;
import consign.service.ConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ConsignController {
    @Autowired
    ConsignService service;

    @RequestMapping(value = "/consign/insertConsign", method= RequestMethod.POST)
    public boolean insertConsign(@RequestBody ConsignRequest request){
        return service.insertConsignRecord(request);
    }

    @RequestMapping(value = "/consign/updateConsign", method= RequestMethod.POST)
    public boolean updateConsign(@RequestBody ConsignRequest request){
        return service.updateConsignRecord(request);
    }

    @RequestMapping(value = "/consign/findByAccountId", method= RequestMethod.POST)
    public List<ConsignRecord> findByAccountId(@RequestParam(value = "accountid", required = true) UUID id){
        return service.queryByAccountId(id);
    }

    @RequestMapping(value = "/consign/findByConsignee", method= RequestMethod.POST)
    public List<ConsignRecord> findByConsignee(@RequestParam(value = "consignee", required = true) String consignee){
        return service.queryByConsignee(consignee);
    }
}
