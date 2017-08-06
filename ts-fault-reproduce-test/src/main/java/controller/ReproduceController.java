package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.ReproduceService;

@RestController
public class ReproduceController {

    @Autowired
    private ReproduceService reproduceService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        System.out.println("home");
        return "Welcome to [ Error Normal Reproduce Test ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/doErrorNormal", method = RequestMethod.GET)
    public String doErrorNormal(){
        if(reproduceService.testErrorNormal() == true){
            return "This is a NORMAL Success";
        }else{
            return "This is a ERROR-NORMAL process";
        }
    }

}
