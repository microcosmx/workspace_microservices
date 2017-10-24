package travelplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import travelplan.domain.TransferTravelSearchInfo;
import travelplan.domain.TransferTravelSearchResult;
import travelplan.service.TravelPlanService;

@RestController
public class TravelPlanController {

    @Autowired
    TravelPlanService travelPlanService;

    @RequestMapping(value="/travelPlan/getTransferResult", method= RequestMethod.POST)
    public TransferTravelSearchResult getTransferResult(@RequestBody TransferTravelSearchInfo info){
        return travelPlanService.getTransferSearch(info);
    }

}
