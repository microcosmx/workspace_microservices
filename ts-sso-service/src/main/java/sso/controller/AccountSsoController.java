package sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sso.service.AccountSsoService;

public class AccountSsoController {

    @Autowired
    private AccountSsoService accountService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts SSO Service ] !";
    }

    @RequestMapping(path = "/loginPutToken", method = RequestMethod.POST)
    public String loginPutToken(){
        return null;
    }

    @RequestMapping(path = "/logoutDeleteToken", method = RequestMethod.POST)
    public String logoutDeleteToken(){
        return null;
    }

    @RequestMapping(path = "/verifyLoginToken", method = RequestMethod.POST)
    public String verifyLoginToken(){
        return null;
    }

}
