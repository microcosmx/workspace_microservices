package security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String home(){
        return "welcome to [Security Service]";
    }

    @RequestMapping(value="/checkPassOrNot", method = RequestMethod.POST)
    public CheckResult checkPassOrNot(CheckInfo ci){
        return null;
    }


}
