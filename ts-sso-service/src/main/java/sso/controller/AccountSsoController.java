package sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sso.domain.LoginInfo;
import sso.domain.LoginResult;
import sso.domain.VerifyResult;
import sso.service.AccountSsoService;

import java.util.UUID;

@RestController
public class AccountSsoController {

    @Autowired
    private AccountSsoService ssoService;
    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts SSO Service ] !";
    }

    @RequestMapping(path = "/logoutDeleteToken/{token}", method = RequestMethod.GET)
    public String logoutDeleteToken(@PathVariable String token){
        return ssoService.logoutDeleteToken(token);
    }

    @RequestMapping(path = "/verifyLoginToken/{token}", method = RequestMethod.GET)
    public VerifyResult verifyLoginToken(@PathVariable String token){
        return ssoService.verifyLoginToken(token);
    }

    @RequestMapping(path = "/loginPutToken/{token}", method = RequestMethod.GET)
    public String loginPutToken(String token){
        return ssoService.loginPutToken(token);
    }

}
