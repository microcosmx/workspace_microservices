package seat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seat.domain.SeatRequest;
import seat.service.SeatService;

@RestController
public class SeatController {

    @Autowired
    private SeatService seatService;

    //分配座位
    @CrossOrigin(origins = "*")
    @RequestMapping(value="/seat/getSeat", method= RequestMethod.POST)
    public String create(@RequestBody SeatRequest seatRequest){
        return seatService.distributeSeat(seatRequest);
    }
}
