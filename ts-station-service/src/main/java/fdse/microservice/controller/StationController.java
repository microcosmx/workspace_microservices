package fdse.microservice.controller;

import fdse.microservice.domain.Information;
import fdse.microservice.domain.Station;
import fdse.microservice.service.StationService;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Chenjie Xu on 2017/5/8.
 */
@RestController
public class StationController {

    //private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private StationService stationService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/station/create",method= RequestMethod.POST)
    public boolean create(@RequestBody Information info){
        return stationService.create(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/station/exist",method= RequestMethod.POST)
    public boolean exist(@RequestBody Information info){
        return stationService.exist(info);
    }

    /*
    @RequestMapping(value="/station/update",method= RequestMethod.POST)
    public boolean update(@RequestBody Information info){
        return stationService.update(info);
    }*/

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/station/delete",method= RequestMethod.POST)
    public boolean delete(@RequestBody Information info){
        return stationService.delete(info);
    }
}
