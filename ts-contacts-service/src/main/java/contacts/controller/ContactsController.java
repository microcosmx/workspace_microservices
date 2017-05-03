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
        VerifyResult tokenResult = verifySsoLogin(qci.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[ContactsService][VerifyLogin] Success");
            return contactsService.findContactsByAccountId(UUID.fromString(qci.getAccountId()));
        }else {
            System.out.println("[ContactsService][VerifyLogin] Fail");
            return new ArrayList<Contacts>();
        }
    }

    @RequestMapping(path = "/createNewContacts", method = RequestMethod.POST)
    public AddContactsResult createNewContacts(@RequestBody AddContactsInfo aci){
        VerifyResult tokenResult = verifySsoLogin(aci.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[ContactsService][VerifyLogin] Success");
            return contactsService.create(aci);
        }else{
            System.out.println("[ContactsService][VerifyLogin] Fail");
            AddContactsResult acr = new AddContactsResult();
            acr.setStatus(false);
            acr.setMessage("Not Login");
            acr.setContacts(null);
            return acr;
        }
    }

    @RequestMapping(path = "/deleteContacts", method = RequestMethod.POST)
    public DeleteContactsResult deleteContacts(@RequestBody DeleteContactsInfo dci){
        VerifyResult tokenResult = verifySsoLogin(dci.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[ContactsService][VerifyLogin] Success");
            return contactsService.delete(UUID.fromString(dci.getContactsId()));
        }else{
            System.out.println("[ContactsService][VerifyLogin] Fail");
            DeleteContactsResult dcr = new DeleteContactsResult();
            dcr.setMessage("Not Login");
            dcr.setStatus(false);
            return dcr;
        }
    }

    @RequestMapping(path = "/saveContactsInfo", method = RequestMethod.PUT)
    public ModifyContactsResult saveContactsInfo(@RequestBody ModifyContactsInfo contactsInfo){
        VerifyResult tokenResult = verifySsoLogin(contactsInfo.getLoginToken());
        if(tokenResult.isStatus() == true){
            System.out.println("[ContactsService][VerifyLogin] Success");
            return contactsService.saveChanges(contactsInfo.getContacts());
        }else{
            System.out.println("[ContactsService][VerifyLogin] Fail");
            ModifyContactsResult mcr = new ModifyContactsResult();
            mcr.setStatus(false);
            mcr.setMessage("Not Login");
            mcr.setContacts(null);
            return mcr;
        }
    }

    private VerifyResult verifySsoLogin(String loginToken){
        restTemplate = new RestTemplate();
        System.out.println("[ContactsService][VerifyLogin] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                     VerifyResult.class);
        return tokenResult;
    }

}
