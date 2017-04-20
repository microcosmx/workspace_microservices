package query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import query.domain.Contacts;
import query.service.ContactsService;
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

}
