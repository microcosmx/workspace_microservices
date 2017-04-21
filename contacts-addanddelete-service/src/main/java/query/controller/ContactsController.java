package query.controller;

import query.domain.AddContactsInfo;
import query.domain.Contacts;
import query.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Contacts Add & Delete Service ] !";
    }

    @RequestMapping(path = "/createNewContacts", method = RequestMethod.POST)
    public Contacts createNewContacts(@RequestBody AddContactsInfo aci){
        return contactsService.create(aci);
    }

    @RequestMapping(path = "/deleteContacts", method = RequestMethod.POST)
    public String deleteContacts(@RequestParam(value="contactsId", required = true) String id){
        System.out.println("ContactsId:" + id);
        UUID contactsId = UUID.fromString(id);
        return contactsService.delete(contactsId);
    }

}
