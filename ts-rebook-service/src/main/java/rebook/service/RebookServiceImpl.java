package rebook.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rebook.domain.*;
import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;

/**
 * Created by Administrator on 2017/6/26.
 */
@Service
public class RebookServiceImpl implements RebookService{

    private RestTemplate restTemplate = new RestTemplate();

    public RebookResult rebook(RebookInfo info){
        //1.黄牛检测
        CheckInfo checkInfo = new CheckInfo();
        CheckResult result = checkSecurity(checkInfo);
        if(result.isStatus() == false){
            otr.setStatus(false);
            otr.setMessage(result.getMessage());
            otr.setOrder(null);
            return otr;
        }
        return null;
    }

    private CheckResult checkSecurity(CheckInfo info){
        CheckResult result = restTemplate.postForObject("http://ts-security-service:11188/security/check",info,CheckResult.class);
        return result;
    }

    private VerifyResult verifySsoLogin(String loginToken){
        System.out.println("[Preserve Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

    private GetTripAllDetailResult getTripAllDetailInformation(GetTripAllDetailInfo gtdi){
        System.out.println("[Preserve Service][Get Trip All Detail Information] Getting....");
        GetTripAllDetailResult gtdr = restTemplate.postForObject(
                "http://ts-travel-service:12346/travel/getTripAllDetailInfo/"
                ,gtdi,GetTripAllDetailResult.class);
        return gtdr;
    }

    private GetContactsResult getContactsById(GetContactsInfo gci){
        System.out.println("[Preserve Service][Get Contacts By Id] Getting....");
        GetContactsResult gcr = restTemplate.postForObject(
                "http://ts-contacts-service:12347/contacts/getContactsById/"
                ,gci,GetContactsResult.class);
        return gcr;
    }

    private CreateOrderResult createOrder(CreateOrderInfo coi){
        System.out.println("[Preserve Service][Get Contacts By Id] Creating....");
        CreateOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/create/"
                ,coi,CreateOrderResult.class);
        return cor;
    }
}
