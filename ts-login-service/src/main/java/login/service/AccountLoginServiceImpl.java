package login.service;

import login.domain.LoginInfo;
import login.domain.LoginResult;
import login.domain.LogoutInfo;
import login.domain.LogoutResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountLoginServiceImpl implements AccountLoginService {

    private RestTemplate restTemplate;

    @Override
    public LoginResult login(LoginInfo li,String YsbCaptcha){
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
        System.out.println("[Login Service][Login] Verification Result:" + verifyResult);
        if(!verifyResult.contains("true")){
            LoginResult verifyCodeLr = new LoginResult();
            verifyCodeLr.setAccount(null);
            verifyCodeLr.setToken(null);
            verifyCodeLr.setStatus(false);
            verifyCodeLr.setMessage("Verification Code Wrong.");
            return verifyCodeLr;
        }
        restTemplate = new RestTemplate();
        LoginResult lr = restTemplate.postForObject(
                "http://ts-sso-service:12349/account/login",
                li,LoginResult.class);
        return lr;
    }

    @Override
    public LogoutResult logout(LogoutInfo li){
        restTemplate = new RestTemplate();
        LogoutResult lr = restTemplate.postForObject("http://ts-sso-service:12349/logout",li,LogoutResult.class);
        if(lr.isStatus()){
            System.out.println("[Login Service][Logout] Success");
        }else{
            System.out.println("[Login Service][Logout] Fail.Reason:" + lr.getMessage());
        }
        return lr;
    }
}
