package register.controller;

import register.domain.RegisterInfo;
import register.domain.RegisterResult;
import register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountRegisterController {

    @Autowired
    private RegisterService accountService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Register Service ] !";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public RegisterResult createNewAccount(@RequestBody RegisterInfo ri){
        return accountService.create(ri);
    }

    @RequestMapping(path = "/registerWithPara", method = RequestMethod.POST)
    public RegisterResult createNewAccountWithPara(@RequestParam String password,@RequestParam String phoneNum,
                                                   @RequestParam int documentType, @RequestParam String documentNum,
                                                   @RequestParam String name, @RequestParam int gender){
        RegisterInfo ri = new RegisterInfo();
        ri.setPassword(password);
        ri.setPhoneNum(phoneNum);
        ri.setDocumentType(documentType);
        ri.setDocumentNum(documentNum);
        ri.setName(name);
        ri.setGender(gender);
        return accountService.create(ri);
    }


}
