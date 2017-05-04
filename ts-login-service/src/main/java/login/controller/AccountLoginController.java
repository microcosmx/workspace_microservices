package login.controller;

import login.domain.LoginInfo;
import login.domain.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import login.service.AccountLoginService;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@RestController
public class AccountLoginController {

    @Autowired
    private AccountLoginService accountService;

    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Login Service ] !";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginResult login(@RequestBody LoginInfo li){
        LoginResult lr = accountService.login(li);
        if(lr.getStatus() == false){
            System.out.println("[AccountLoginService][Login] Login Fail. No token generate.");
            return lr;
        }else{
            //Post token to the sso
            System.out.println("[AccountLoginService][Login] LoginSuccess. Put token to sso.");
            UUID token = UUID.randomUUID();
            lr.setToken(token.toString());
            restTemplate = new RestTemplate();
            String tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/loginPutToken/" + token.toString(),String.class);
            System.out.println("[AccountLoginService][Login] Post to sso:" + tokenResult);
            return lr;
        }
    }

}
