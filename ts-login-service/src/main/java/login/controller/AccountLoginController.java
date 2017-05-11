package login.controller;

import login.domain.LoginInfo;
import login.domain.LoginResult;
import login.domain.PutLoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import login.service.AccountLoginService;
import org.springframework.web.client.RestTemplate;

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
    public LoginResult login(@RequestBody LoginInfo li, @CookieValue String YsbCaptcha){

        System.out.println("[AccountLoginService][LoginWithPara] VerificationCode:" + li.getVerificationCode() +
                " VerifyCookie:" + YsbCaptcha);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","YsbCaptcha=" + YsbCaptcha);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("verificationCode", li.getVerificationCode());
        HttpEntity requestEntity = new HttpEntity(body,requestHeaders);
        restTemplate = new RestTemplate();
        ResponseEntity rssResponse = restTemplate.exchange(
                "http://ts-verification-code-service:15678/verification/verify",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        String verifyResult = (String)rssResponse.getBody();
        System.out.println("[AccountLoginService][LoginWithPara] Verification Result:" + verifyResult);

        if(!verifyResult.contains("true")){
            LoginResult verifyCodeLr = new LoginResult();
            verifyCodeLr.setAccount(null);
            verifyCodeLr.setToken(null);
            verifyCodeLr.setStatus(false);
            verifyCodeLr.setMessage("Verification Code Wrong.");
            return verifyCodeLr;
        }

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
    public LoginResult loginWithPara(@RequestParam String phoneNum, @RequestParam String password,
                                     @RequestParam String verificationCode, @CookieValue String YsbCaptcha){

        System.out.println("[AccountLoginService][LoginWithPara] VerificationCode:" + verificationCode +
                " VerifyCookie:" + YsbCaptcha);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","YsbCaptcha=" + YsbCaptcha);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("verificationCode", verificationCode);
        HttpEntity requestEntity = new HttpEntity(body,requestHeaders);
        restTemplate = new RestTemplate();
        ResponseEntity rssResponse = restTemplate.exchange(
                "http://ts-verification-code-service:15678/verification/verify",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        String verifyResult = (String)rssResponse.getBody();
        System.out.println("[AccountLoginService][LoginWithPara] Verification Result:" + verifyResult);

        if(!verifyResult.contains("true")){
            LoginResult verifyCodeLr = new LoginResult();
            verifyCodeLr.setAccount(null);
            verifyCodeLr.setToken(null);
            verifyCodeLr.setStatus(false);
            verifyCodeLr.setMessage("Verification Code Wrong.");
            return verifyCodeLr;
        }

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
