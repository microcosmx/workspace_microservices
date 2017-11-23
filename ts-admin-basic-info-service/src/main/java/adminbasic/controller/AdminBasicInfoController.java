package adminbasic.controller;

import adminbasic.domin.bean.Contacts;
import adminbasic.domin.info.DeleteContactsInfo;
import adminbasic.domin.info.ModifyContactsInfo;
import adminbasic.domin.reuslt.AddContactsResult;
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
    @RequestMapping(path = "/adminbasic/getAllContacts/{id}", method = RequestMethod.GET)
    public GetAllContactsResult getAllContacts(@PathVariable String id){
        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: " + id);
        return adminBasicInfoService.getAllContacts(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/deleteContacts", method = RequestMethod.POST)
    public DeleteContactsResult deleteContacts(@RequestBody DeleteContactsInfo dci){
        System.out.println("[Admin Basic Info Service][Delete Contacts by admin: " + dci.getLoginId());
        return adminBasicInfoService.deleteContact(dci.getLoginId(), dci);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/modifyContacts", method = RequestMethod.POST)
    public ModifyContactsResult modifyContacts(@RequestBody ModifyContactsInfo mci){
        System.out.println("[Admin Basic Info Service][Modify Contacts by admin: " + mci.getLoginId());
        return adminBasicInfoService.modifyContact(mci.getLoginId(), mci);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminbasic/addContacts", method = RequestMethod.POST)
    public AddContactsResult addContacts(@RequestBody Contacts c){
        System.out.println("[Admin Basic Info Service][Modify Contacts by admin: " + c.getLoginId());
        return adminBasicInfoService.addContact(c.getLoginId(), c);
    }



}
