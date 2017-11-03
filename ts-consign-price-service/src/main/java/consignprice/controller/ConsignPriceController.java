package consignprice.controller;

import consignprice.domain.GetPriceDomain;
import consignprice.service.ConsignPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsignPriceController {

    @Autowired
    ConsignPriceService service;

    @RequestMapping(value = "/consignPrice/getPrice", method= RequestMethod.POST)
    public double getPriceByWeightAndRegion(@RequestBody GetPriceDomain info){
        return service.getPriceByWeightAndRegion(info);
    }
}
