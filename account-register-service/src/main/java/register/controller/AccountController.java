package register.controller;

import register.domain.Account;
import register.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Register Service ] !";
    }

    @RequestMapping(path = "/createNewAccount", method = RequestMethod.POST)
    public Account createNewAccount(@RequestBody Account newAccount){
        return accountService.create(newAccount);
    }

}
