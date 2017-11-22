package adminbasic.controller;

import adminbasic.domin.info.DeleteContactsInfo;
import adminbasic.domin.info.ModifyContactsInfo;
import adminbasic.domin.reuslt.DeleteContactsResult;
import adminbasic.domin.reuslt.GetAllContactsResult;
import adminbasic.domin.reuslt.ModifyContactsResult;
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

//    @CrossOrigin(origins = "*")
//    @RequestMapping(path = "/adminbasic/login", method = RequestMethod.POST)
//    public Contacts getAllContacts(@RequestBody AdminLoginInfo ali){
//        System.out.println("[Admin Basic Info Service][Login]");
//        return adminBasicInfoService.login(ali.getName(), ali.getPassword());
//    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/getAllContacts", method = RequestMethod.GET)
    public GetAllContactsResult getAllContacts(@CookieValue String loginId){
        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: " + loginId);
        return adminBasicInfoService.getAllContacts(loginId);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/deleteContacts", method = RequestMethod.POST)
    public DeleteContactsResult deleteContacts(@RequestBody DeleteContactsInfo dci, @CookieValue String loginId){
        System.out.println("[Admin Basic Info Service][Delete Contacts by admin: " + loginId);
        return adminBasicInfoService.deleteContact(loginId, dci);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/modifyContacts", method = RequestMethod.POST)
    public ModifyContactsResult modifyContacts(@RequestBody ModifyContactsInfo mci, @CookieValue String loginId){
        System.out.println("[Admin Basic Info Service][Modify Contacts by admin: " + loginId);
        return adminBasicInfoService.modifyContact(loginId, mci);
    }



}
