package register.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import register.domain.CreateAccountInfo;
import register.domain.RegisterInfo;
import register.domain.RegisterResult;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    private RestTemplate restTemplate;

    @Override
    public RegisterResult create(RegisterInfo ri,String YsbCaptcha){
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
        System.out.println("[Register Service][Register] Verification Result:" + verifyResult);
        if(!verifyResult.contains("true")){
            RegisterResult verifyCodeLr = new RegisterResult();
            verifyCodeLr.setAccount(null);
            verifyCodeLr.setMessage("Verification Code Wrong");
            verifyCodeLr.setStatus(false);
            return verifyCodeLr;
        }
        restTemplate = new RestTemplate();
        RegisterResult rr = restTemplate.postForObject(
                "http://ts-sso-service:12349/account/register",
                ri,RegisterResult.class);

        CreateAccountInfo createAccountInfo = new CreateAccountInfo();
        createAccountInfo.setUserId(rr.getAccount().getId().toString());
        createAccountInfo.setBalance("10000");
        boolean  createAccountSuccess = restTemplate.postForObject(
                "http://ts-inside-payment-service:18673/inside_payment/createAccount",
                createAccountInfo,Boolean.class);
        return rr;
    }
}
