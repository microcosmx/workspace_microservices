package adminbasic.service;

import adminbasic.domin.info.DeleteContactsInfo;
import adminbasic.domin.info.ModifyContactsInfo;
import adminbasic.domin.reuslt.DeleteContactsResult;
import adminbasic.domin.reuslt.GetAllContactsResult;
import adminbasic.domin.reuslt.ModifyContactsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminBasicInfoServiceImpl implements AdminBasicInfoService{

    @Autowired
    private RestTemplate restTemplate;

    private String adminID="1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f";


    @Override
    public  GetAllContactsResult getAllContacts(String loginId) {
        GetAllContactsResult result ;
        if(adminID.equals(loginId)){
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
    public DeleteContactsResult deleteContact(String loginId, DeleteContactsInfo dci) {
        DeleteContactsResult result;
        if(adminID.equals(loginId)){
            result = restTemplate.postForObject(
                    "http://ts-contacts-service:12347/contacts/deleteContacts",dci,
                    DeleteContactsResult.class);
        } else {
            result = new DeleteContactsResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + loginId);
        }
        return result;
    }

    @Override
    public ModifyContactsResult modifyContact(String loginId, ModifyContactsInfo mci) {
        ModifyContactsResult result;
        if(adminID.equals(loginId)){
            result = restTemplate.postForObject(
                    "http://ts-contacts-service:12347/contacts/modifyContacts",mci,
                    ModifyContactsResult.class);
        } else {
            result = new ModifyContactsResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + loginId);
        }
        return result;
    }

//    @Override
//    public AddContactsResult addContact(String loginId, AddContactsInfo aci) {
//        AddContactsResult result;
//        if (adminID.equals(loginId)) {
//            HttpHeaders headers = new HttpHeaders();
//            List<String> cookies = new ArrayList<>();
//            cookies.add("loginId=" + Strings.nullToEmpty("2"));
//            cookies.add("loginToken=" + Strings.nullToEmpty("1"));
//            headers.put(HttpHeaders.COOKIE,cookies);
//            HttpEntity request = new HttpEntity(aci, headers);
//            result = restTemplate.postForObject(
//                    "http://ts-contacts-service:12347/contacts/create",request,
//                    AddContactsResult.class);
//
//        } else {
//            result = new AddContactsResult();
//            result.setStatus(false);
//            result.setMessage("The Contact add operation failed.");
//        }
//        return null;
//    }



//    @Override
//    public Contacts login(String name, String password) {
//        Contacts c = null;
//        if("adminroot".equals(name) && "adminroot".equals(password)){
//            c = new Contacts();
//            c.setId(UUID.fromString("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f"));
//            c.setName("adminroot");
//        }
//        return c;
//    }
}
