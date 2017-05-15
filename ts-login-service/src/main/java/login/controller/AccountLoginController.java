package login.controller;

import login.domain.*;
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

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Login Service ] !";
    }

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public LogoutResult logout(@RequestBody LogoutInfo li){
        System.out.println("[AccountLoginService][Logout] Logout ID:" + li.getId() + " Token:" + li.getToken());
        restTemplate = new RestTemplate();
        LogoutResult lr = restTemplate.postForObject("http://ts-sso-service:12349/logout",li,LogoutResult.class);
        if(lr.isStatus()){
            System.out.println("[AccountLoginService][Logout] Success");
        }else{
            System.out.println("[AccountLoginService][Logout] Fail.Reason:" + lr.getMessage());
        }
        return lr;
    }

}
