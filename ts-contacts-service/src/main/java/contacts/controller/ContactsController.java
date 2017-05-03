package contacts.controller;

import contacts.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import contacts.service.ContactsService;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    private RestTemplate restTemplate;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Contacts Service ] !";
    }

    @RequestMapping(path = "/findContacts", method = RequestMethod.POST)
    public ArrayList<Contacts> findContactsByAccountId(@RequestBody QueryContactsInfo qci){
        String loginToken = qci.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            UUID accountId = UUID.fromString(qci.getAccountId());
            return contactsService.findContactsByAccountId(accountId);
        }else {
            return new ArrayList<Contacts>();
        }
    }

    @RequestMapping(path = "/createNewContacts", method = RequestMethod.POST)
    public AddContactsResult createNewContacts(@RequestBody AddContactsInfo aci){
        String loginToken = aci.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            return contactsService.create(aci);
        }else{
            AddContactsResult acr = new AddContactsResult();
            acr.setStatus(false);
            acr.setMessage("Not Login");
            acr.setContacts(null);
            return acr;
        }
    }

    @RequestMapping(path = "/deleteContacts", method = RequestMethod.POST)
    public DeleteContactsResult deleteContacts(@RequestBody DeleteContactsInfo dci){
        String loginToken = dci.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            UUID contactsId = UUID.fromString(dci.getContactsId());
            return contactsService.delete(contactsId);
        }else{
            DeleteContactsResult dcr = new DeleteContactsResult();
            dcr.setStatus(false);
            dcr.setMessage("Not Login");
            return dcr;
        }
    }

    @RequestMapping(path = "/saveContactsInfo", method = RequestMethod.PUT)
    public ModifyContactsResult saveContactsInfo(@RequestBody ModifyContactsInfo contactsInfo){
        String loginToken = contactsInfo.getLoginToken();
        restTemplate = new RestTemplate();
        VerifyResult tokenResult = restTemplate.getForObject("http://ts-sso-service:12349/verifyLoginToken/" + loginToken,VerifyResult.class);
        if(tokenResult.isStatus() == true){
            return contactsService.saveChanges(contactsInfo.getContacts());
        }else{
            ModifyContactsResult mcr = new ModifyContactsResult();
            mcr.setStatus(false);
            mcr.setMessage("Not Login");
            mcr.setContacts(null);
            return mcr;
        }
    }

}
