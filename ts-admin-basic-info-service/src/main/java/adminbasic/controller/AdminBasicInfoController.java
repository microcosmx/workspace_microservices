package adminbasic.controller;

import adminbasic.domin.bean.Contacts;
import adminbasic.domin.info.AdminLoginInfo;
import adminbasic.domin.reuslt.GetAllContactsResult;
import adminbasic.service.AdminBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminBasicInfoController {

    @Autowired
    AdminBasicInfoService adminBasicInfoService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ AdminBasicInfo Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/login", method = RequestMethod.POST)
    public Contacts getAllContacts(@RequestBody AdminLoginInfo ali){
        System.out.println("[Admin Basic Info Service][Login]");
        return adminBasicInfoService.login(ali.getName(), ali.getPassword());
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/getAllContacts", method = RequestMethod.GET)
    public GetAllContactsResult getAllContacts(@CookieValue String loginId){
        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: " + loginId);
        return adminBasicInfoService.getAllContacts(loginId);
    }



}
