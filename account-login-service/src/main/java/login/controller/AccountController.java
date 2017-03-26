package login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import login.domain.Account;
import login.service.AccountService;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Login Service ] !";
    }

    @RequestMapping(path = "/findAccount/{accountId}", method = RequestMethod.GET)
    public Account findAccount(@PathVariable long accountId){
        return accountService.findById(accountId);
    }

}
