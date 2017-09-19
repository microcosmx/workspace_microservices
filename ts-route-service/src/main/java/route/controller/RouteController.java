package route.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import route.domain.*;
import route.service.RouteService;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Route Service ] !";
    }

    @RequestMapping(path = "/route/createAndModify", method = RequestMethod.POST)
    public CreateAndModifyRouteResult createAndModifyRoute(@RequestBody CreateAndModifyRouteInfo createAndModifyRouteInfo){
        return null;
    }

    @RequestMapping(path = "/route/delete", method = RequestMethod.POST)
    public DeleteRouteResult deleteRoute(@RequestBody DeleteRouteInfo deleteRouteInfo){
        return null;
    }

    @RequestMapping(path = "/route/queryById", method = RequestMethod.POST)
    public GetRouteByIdResult queryById(@RequestBody GetRouteByIdInfo getRouteByIdInfo){
        return null;
    }

    @RequestMapping(path = "/route/queryByStartAndTerminal", method = RequestMethod.POST)
    public GetRouteByStartAndTerminalResult queryByStartAndTerminal(GetRouteByStartAndTerminalInfo getRouteByStartAndTerminalInfo){
        return null;
    }

}