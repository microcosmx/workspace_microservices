package train.controller;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import train.domain.TrainType;
import train.service.TrainService;


/**
 * Created by Chenjie Xu on 2017/5/8.
 */
@RestController
public class TrainController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TrainService trainService;

    @RequestMapping(value="/train/create",method= RequestMethod.POST)
    public boolean create(@RequestParam String id, @RequestParam int economyClass, @RequestParam int confortClass){
        return trainService.create(id,economyClass,confortClass);
    }

    @RequestMapping(value="/train/retrieve",method= RequestMethod.POST)
    public TrainType retrieve(@RequestParam String id){
        return trainService.retrieve(id);
    }

    @RequestMapping(value="/train/update",method= RequestMethod.POST)
    public boolean update(@RequestParam String id, @RequestParam int economyClass, @RequestParam int confortClass){
        return trainService.update(id,economyClass,confortClass);
    }

    @RequestMapping(value="/train/delete",method= RequestMethod.POST)
    public boolean delete(@RequestParam String id){
        return trainService.delete(id);
    }
}
