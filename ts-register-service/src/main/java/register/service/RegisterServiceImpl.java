package register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import register.domain.AddContactsInfo;
import register.domain.CreateAccountInfo;
import register.domain.RegisterInfo;
import register.domain.RegisterResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public RegisterResult create(RegisterInfo ri,String YsbCaptcha){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","YsbCaptcha=" + YsbCaptcha);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("verificationCode", ri.getVerificationCode());
        HttpEntity requestEntity = new HttpEntity(body,requestHeaders);
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
        RegisterResult rr = restTemplate.postForObject(
                "http://ts-sso-service:12349/account/register",
                ri,RegisterResult.class);
        if(rr.isStatus() == true){
            System.out.println("[Register Service] Register Success.");
            System.out.println("[Register Service] Get Price Account.");
            CreateAccountInfo createAccountInfo = new CreateAccountInfo();
            createAccountInfo.setUserId(rr.getAccount().getId().toString());
            createAccountInfo.setMoney("2000");
            System.out.println("[Register Service] Get Price Account.");
            boolean  createAccountSuccess = restTemplate.postForObject(
                    "http://ts-inside-payment-service:18673/inside_payment/createAccount",
                    createAccountInfo,Boolean.class);

//            AddContactsInfo contacts_One = new AddContactsInfo();
//            contacts_One.setDocumentType(1);
//            contacts_One.setName("Contacts_One");
//            contacts_One.setDocumentNumber("DocumentNumber_One");
//            contacts_One.setPhoneNumber("ContactsPhoneNum_One");
        }else{

        }
        return rr;
    }

}
