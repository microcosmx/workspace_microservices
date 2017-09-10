package example.controller;

import example.service.ExampleSecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleSecondController {

    @Autowired
    ExampleSecondService exampleSecondService;

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String home() {
        return exampleSecondService.test();
    }
}
