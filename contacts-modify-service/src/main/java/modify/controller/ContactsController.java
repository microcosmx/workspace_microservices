package modify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import modify.domain.Contacts;
import modify.service.ContactsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Contacts Service ] !";
    }

    @RequestMapping(path = "/saveContactsInfo", method = RequestMethod.PUT)
    public Contacts saveAccountInfo(@RequestBody Contacts contacts){
        return contactsService.saveChanges(contacts);
    }

}
