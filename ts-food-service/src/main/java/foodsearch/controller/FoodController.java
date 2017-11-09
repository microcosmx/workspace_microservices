package foodsearch.controller;

import foodsearch.domain.GetAllFoodOfTripInfo;
import foodsearch.domain.GetAllFoodOfTripResult;
import foodsearch.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class FoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Food Service ] !";
    }

    @RequestMapping(path = "/food/getFood", method = RequestMethod.POST)
    public GetAllFoodOfTripResult getFood(@RequestBody GetAllFoodOfTripInfo gati){
        System.out.println("[Food Service]Get the Get Food Request!");
        return foodService.getAllFood(gati.getDate(), gati.getStartStation(), gati.getEndStation(), gati.getTripId());
    }

}
