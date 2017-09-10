package example.controller;

import example.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @Autowired
    ExampleService exampleService;

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String home() {
        return exampleService.test();
    }

}
