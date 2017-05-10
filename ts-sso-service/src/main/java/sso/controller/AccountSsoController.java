package sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sso.domain.PutLoginResult;
import sso.domain.VerifyResult;
import sso.service.AccountSsoService;

@RestController
public class AccountSsoController {

    @Autowired
    private AccountSsoService ssoService;

    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts SSO Service ] !";
    }

    @RequestMapping(path = "/logoutDeleteToken/{loginId}", method = RequestMethod.GET)
    public String logoutDeleteToken(@PathVariable String loginId){
        return ssoService.logoutDeleteToken(loginId);
    }

    @RequestMapping(path = "/verifyLoginToken/{token}", method = RequestMethod.GET)
    public VerifyResult verifyLoginToken(@PathVariable String token){
        return ssoService.verifyLoginToken(token);
    }

    @RequestMapping(path = "/loginPutToken/{loginId}", method = RequestMethod.GET)
    public PutLoginResult loginPutToken(@PathVariable String loginId){
        return ssoService.loginPutToken(loginId);
    }

}
