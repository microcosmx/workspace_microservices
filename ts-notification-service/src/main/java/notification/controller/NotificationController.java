package notification.controller;

import notification.domain.NotifyInfo;
import notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Wenyi on 2017/6/15.
 */
@RestController
public class NotificationController {
    @Autowired
    NotificationService service;

    @RequestMapping(value="/notification/notify", method = RequestMethod.POST)
    public boolean notify(@RequestBody NotifyInfo info){
        return service.notify(info);
    }
}
