package accounts.controller;

import accounts.domain.Account;
import accounts.domain.NewPasswordInfo;
import accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Service ] !";
    }

    @RequestMapping(path = "/findAccount/{phoneNum}", method = RequestMethod.GET)
    public Account findAccount(@PathVariable String phoneNum){
        return accountService.findByPhoneNum(phoneNum);
    }

    @RequestMapping(path = "/saveAccountInfo", method = RequestMethod.PUT)
    public Account saveAccountInfo(@RequestBody Account account){
        return accountService.saveChanges(account);
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.PUT)
    public Account changePassword(@RequestBody NewPasswordInfo npi){
        return accountService.changePassword(npi);
    }

}
