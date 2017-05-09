package register.controller;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import register.service.StationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Chenjie Xu on 2017/5/8.
 */
@RestController
public class StationController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private StationService stationService;

    @RequestMapping(value="/station/create",method= RequestMethod.GET)
    public boolean create(HttpServletRequest request, HttpServletResponse response ){
        return stationService.create(request,response);
    }

    @RequestMapping(value="/station/exist",method= RequestMethod.POST)
    public boolean exist(HttpServletRequest request, HttpServletResponse response ){
        return stationService.exist(request,response);
    }

    @RequestMapping(value="/station/update",method= RequestMethod.GET)
    public boolean update(HttpServletRequest request, HttpServletResponse response ){
        return stationService.update(request,response);
    }

    @RequestMapping(value="/station/delete",method= RequestMethod.GET)
    public boolean delete(HttpServletRequest request, HttpServletResponse response ){
        return stationService.delete(request,response);
    }
}
