package sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sso.service.AccountSsoService;

public class AccountSsoController {

    @Autowired
    private AccountSsoService ssoService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts SSO Service ] !";
    }

    @RequestMapping(path = "/loginPutToken", method = RequestMethod.POST)
    public String loginPutToken(@RequestParam(value="token", required = true) String token){
        return ssoService.loginPutToken(token);
    }

    @RequestMapping(path = "/logoutDeleteToken", method = RequestMethod.POST)
    public String logoutDeleteToken(@RequestParam(value="token", required = true) String token){
        return ssoService.logoutDeleteToken(token);
    }

    @RequestMapping(path = "/verifyLoginToken", method = RequestMethod.POST)
    public String verifyLoginToken(@RequestParam(value="token", required = true) String token){
        return ssoService.verifyLoginToken(token);
    }

}
