package query.controller;

import query.domain.AddContactsInfo;
import query.domain.Contacts;
import query.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Contacts Add & Delete Service ] !";
    }

    @RequestMapping(path = "/createNewContacts", method = RequestMethod.POST)
    public Contacts createNewAccount(@RequestBody AddContactsInfo aci){
        return contactsService.create(aci);
    }

}
