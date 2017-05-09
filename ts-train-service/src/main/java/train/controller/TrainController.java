package train.controller;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import train.domain.Information;
import train.service.TrainService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Chenjie Xu on 2017/5/8.
 */
@RestController
public class TrainController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TrainService trainService;

    @RequestMapping(value="/train/create",method= RequestMethod.POST)
    public boolean create(@RequestBody Information info){
        return trainService.create(info);
    }

    @RequestMapping(value="/train/retrieve",method= RequestMethod.POST)
    public String retrieve(@RequestBody Information info){
        return trainService.retrieve(info);
    }

    @RequestMapping(value="/train/update",method= RequestMethod.GET)
    public boolean update(@RequestBody Information info){
        return trainService.update(info);
    }

    @RequestMapping(value="/train/delete",method= RequestMethod.GET)
    public boolean delete(@RequestBody Information info){
        return trainService.delete(info);
    }
}
