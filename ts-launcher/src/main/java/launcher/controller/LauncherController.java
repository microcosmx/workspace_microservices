package launcher.controller;

import launcher.service.LauncherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LauncherController {

    @Autowired
    private LauncherService launcherService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Ts Launcher ] !";
    }

    @RequestMapping(path = "/doErrorQueue",method = RequestMethod.GET)
    public String doErrorQueue(){
        launcherService.doErrorQueue();
        return "[Ts Launcher][Do Error Queue] Complete";
    }

}
