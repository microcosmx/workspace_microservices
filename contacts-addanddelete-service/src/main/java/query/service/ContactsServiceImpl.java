package query.service;

import query.domain.AddContactsInfo;
import query.domain.Contacts;
import query.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Contacts create(AddContactsInfo aci){

        Contacts contacts = new Contacts();
        contacts.setId(UUID.randomUUID());
        contacts.setName(aci.getName());
        contacts.setPhoneNumber(aci.getPhoneNumber());
        contacts.setDocumentNumber(aci.getDocumentNumber());
        contacts.setAccountId(aci.getAccountId());
        contacts.setDocumentType(aci.getDocumentType());

        ArrayList<Contacts> accountContacts = contactsRepository.findByAccountId(contacts.getAccountId());
        if(accountContacts.contains(contacts)){
            return null;
        }else{
            contactsRepository.save(contacts);
            return contacts;
        }
    }

}


