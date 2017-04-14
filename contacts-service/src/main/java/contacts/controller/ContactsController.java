package contacts.controller;

import contacts.domain.AddContactsInfo;
import contacts.domain.Contacts;
import contacts.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Contacts Service ] !";
    }

    @RequestMapping(path = "/findContacts/{accountIdStr}", method = RequestMethod.GET)
    public ArrayList<Contacts> findContactsByAccountId(@PathVariable String accountIdStr){
        UUID accountId = UUID.fromString(accountIdStr);
        return contactsService.findContactsByAccountId(accountId);
    }

    @RequestMapping(path = "/createNewContacts", method = RequestMethod.POST)
    public Contacts createNewAccount(@RequestBody AddContactsInfo aci){
        return contactsService.create(aci);
    }

    @RequestMapping(path = "/saveContactsInfo", method = RequestMethod.PUT)
    public Contacts saveAccountInfo(@RequestBody Contacts contacts){
        return contactsService.saveChanges(contacts);
    }

}
