package sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sso.domain.*;
import sso.service.AccountSsoService;

@RestController
public class AccountSsoController {

    @Autowired
    private AccountSsoService ssoService;


    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts SSO Service ] !";
    }

    /***************For super admin(Single Service Test*******************/
    @RequestMapping(path = "/account/findAll", method = RequestMethod.GET)
    public FindAllAccountResult findAllAccount(){
        return ssoService.findAllAccount();
    }

    @RequestMapping(path = "/account/findAllLogin", method = RequestMethod.GET)
    public GetLoginAccountList findAllLoginAccount(){
        return ssoService.findAllLoginAccount();
    }

    @RequestMapping(path = "/account/modify", method = RequestMethod.POST)
    public ModifyAccountResult modifyAccount(@RequestBody ModifyAccountInfo modifyAccountInfo){
        return ssoService.saveChanges(modifyAccountInfo);
    }

    /***************************For Normal Use***************************/
    @RequestMapping(path = "/account/register", method = RequestMethod.POST)
    public RegisterResult createNewAccount(@RequestBody RegisterInfo ri){
        return ssoService.create(ri);
    }

    @RequestMapping(path = "/account/login", method = RequestMethod.POST)
    public LoginResult login(@RequestBody LoginInfo li) {
        LoginResult lr = ssoService.login(li);
        if(lr.getStatus() == false){
            System.out.println("[SSO Service][Login] Login Fail. No token generate.");
            return lr;
        }else{
            //Post token to the sso
            System.out.println("[SSO Service][Login] Password Right. Put token to sso.");
            PutLoginResult tokenResult = loginPutToken(lr.getAccount().getId().toString());
            System.out.println("[SSO Service] PutLoginResult Status: " + tokenResult.isStatus());
            if(tokenResult.isStatus() == true){
                System.out.println("[SSO Service][Login] Post to sso:" + tokenResult.getToken());
                lr.setToken(tokenResult.getToken());
                lr.setMessage(tokenResult.getMsg());
            }else{
                System.out.println("[SSO Service][Login] Token Result Fail.");
                lr.setToken(null);
                lr.setStatus(false);
                lr.setMessage(tokenResult.getMsg());
                lr.setAccount(null);
            }
            return lr;
        }
    }


    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public LogoutResult logoutDeleteToken(@RequestBody LogoutInfo li){
        System.out.println("[SSO Service][Logout Delete Token] ID:" + li.getId() + "Token:" + li.getToken());
        return ssoService.logoutDeleteToken(li);
    }

    @RequestMapping(path = "/account/findById", method = RequestMethod.POST)
    public GetAccountByIdResult getAccountById(@RequestBody GetAccountByIdInfo info){
        System.out.println("[SSO Service][Find Account By Id] Account Id:" + info.getAccountId());
        return ssoService.getAccountById(info);
    }

    @RequestMapping(path = "/verifyLoginToken/{token}", method = RequestMethod.GET)
    public VerifyResult verifyLoginToken(@PathVariable String token){
        return ssoService.verifyLoginToken(token);
    }

    public PutLoginResult loginPutToken(String loginId){
        return ssoService.loginPutToken(loginId);
    }

}
