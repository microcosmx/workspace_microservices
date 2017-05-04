package sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sso.domain.VerifyResult;
import sso.service.AccountSsoService;

@RestController
public class AccountSsoController {

    @Autowired
    private AccountSsoService ssoService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts SSO Service ] !";
    }

    @RequestMapping(path = "/loginPutToken/{token}", method = RequestMethod.GET)
    public String loginPutToken(@PathVariable String token){
        return ssoService.loginPutToken(token);
    }

    @RequestMapping(path = "/logoutDeleteToken/{token}", method = RequestMethod.GET)
    public String logoutDeleteToken(@PathVariable String token){
        return ssoService.logoutDeleteToken(token);
    }

    @RequestMapping(path = "/verifyLoginToken/{token}", method = RequestMethod.GET)
    public VerifyResult verifyLoginToken(@PathVariable String token){
        return ssoService.verifyLoginToken(token);
    }

}
