package plan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import plan.service.RoutePlanService;

@RestController
public class RoutePlanController {

    @Autowired
    private RoutePlanService routePlanService;
}
