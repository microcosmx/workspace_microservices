package controller;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TravelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
@RestController
public class TravelController {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    @Autowired
    private TravelService travelService;

    @RequestMapping(value="/travel", method= RequestMethod.GET)
    public String query(HttpServletRequest request, HttpServletResponse response){
        return "";
    }

}
