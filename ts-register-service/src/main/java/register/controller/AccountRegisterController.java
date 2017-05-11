package register.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import register.domain.RegisterInfo;
import register.domain.RegisterResult;
import register.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountRegisterController {

    @Autowired
    private RegisterService accountService;

    RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Register Service ] !";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public RegisterResult createNewAccount(@RequestBody RegisterInfo ri,@CookieValue String YsbCaptcha){


        System.out.println("[AccountRegisterService][RegisterWithPara] VerificationCode:" + ri.getVerificationCode() +
                " VerifyCookie:" + YsbCaptcha);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","YsbCaptcha=" + YsbCaptcha);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("verificationCode", ri.getVerificationCode());
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
            RegisterResult verifyCodeLr = new RegisterResult();
            verifyCodeLr.setAccount(null);
            verifyCodeLr.setMessage("Verification Code Wrong");
            verifyCodeLr.setStatus(false);
            return verifyCodeLr;
        }

        return accountService.create(ri);
    }

    @RequestMapping(path = "/registerWithPara", method = RequestMethod.POST)
    public RegisterResult createNewAccountWithPara(@RequestParam String password,@RequestParam String phoneNum,
                                                   @RequestParam int documentType, @RequestParam String documentNum,
                                                   @RequestParam String name, @RequestParam int gender,
                                                   @RequestParam String verificationCode, @CookieValue String YsbCaptcha){

        System.out.println("[AccountRegisterService][RegisterWithPara] VerificationCode:" + verificationCode +
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
        System.out.println("[AccountRegisterService][RegisterWithPara] Verification Result:" + verifyResult);

        if(!verifyResult.contains("true")){
            RegisterResult verifyCodeLr = new RegisterResult();
            verifyCodeLr.setAccount(null);
            verifyCodeLr.setMessage("Verification Code Wrong");
            verifyCodeLr.setStatus(false);
            return verifyCodeLr;
        }

        RegisterInfo ri = new RegisterInfo();
        ri.setPassword(password);
        ri.setPhoneNum(phoneNum);
        ri.setDocumentType(documentType);
        ri.setDocumentNum(documentNum);
        ri.setName(name);
        ri.setGender(gender);
        return accountService.create(ri);
    }


}
