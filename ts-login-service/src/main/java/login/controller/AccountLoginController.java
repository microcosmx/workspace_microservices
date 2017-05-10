package login.controller;

import login.domain.LoginInfo;
import login.domain.LoginResult;
import login.domain.PutLoginResult;
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
            System.out.println("[AccountLoginService][Login] Password Right. Put token to sso.");
            restTemplate = new RestTemplate();
            PutLoginResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/loginPutToken/"
                    + lr.getAccount().getId().toString(),PutLoginResult.class);
            System.out.println("[AccountLoginService][Login] Post to sso:" + tokenResult.getToken());
            if(tokenResult.isStatus() == true){
                lr.setToken(tokenResult.getToken());
            }else{
                lr.setToken(null);
                lr.setStatus(false);
                lr.setMessage(tokenResult.getMsg());
                lr.setAccount(null);
            }
            return lr;
        }
    }

    @RequestMapping(path = "/loginWithPara", method = RequestMethod.POST)
    public LoginResult loginWithPara(@RequestParam String phoneNum,@RequestParam String password){
        LoginInfo li = new LoginInfo();
        li.setPhoneNum(phoneNum);
        li.setPassword(password);
        LoginResult lr = accountService.login(li);
        if(lr.getStatus() == false){
            System.out.println("[AccountLoginService][Login With Para] Login Fail. No token generate.");
            return lr;
        }else{
            //Post token to the sso
            System.out.println("[AccountLoginService][Login With Para] Password Right. Put token to sso.");
            restTemplate = new RestTemplate();
            PutLoginResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/loginPutToken/"
                    + lr.getAccount().getId().toString(),PutLoginResult.class);
            System.out.println("[AccountLoginService][Login With Para] Post to sso:" + tokenResult.getToken());
            if(tokenResult.isStatus() == true){
                lr.setToken(tokenResult.getToken());
            }else{
                lr.setToken(null);
                lr.setStatus(false);
                lr.setMessage(tokenResult.getMsg());
                lr.setAccount(null);
            }
            return lr;
        }
    }

}
