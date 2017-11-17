package adminbasic.service;

import adminbasic.domin.bean.Contacts;
import adminbasic.domin.reuslt.GetAllContactsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class AdminBasicInfoServiceImpl implements AdminBasicInfoService{

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public  GetAllContactsResult getAllContacts(String loginId) {
        GetAllContactsResult result ;
        if("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f".equals(loginId)){
           result = restTemplate.getForObject(
                    "http://ts-contacts-service:12347/contacts/findAll",
                    GetAllContactsResult.class);
        } else {
            result = new GetAllContactsResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + loginId);
        }

        return result;
    }

    @Override
    public Contacts login(String name, String password) {
        Contacts c = null;
        if("adminroot".equals(name) && "adminroot".equals(password)){
            c = new Contacts();
            c.setId(UUID.fromString("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f"));
            c.setName("adminroot");
        }
        return c;
    }
}
