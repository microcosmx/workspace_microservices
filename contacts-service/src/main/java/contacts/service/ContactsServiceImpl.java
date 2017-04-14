package contacts.service;

import contacts.domain.AddContactsInfo;
import contacts.domain.Contacts;
import contacts.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Contacts findContactsById(UUID id){
        return contactsRepository.findById(id);
    }

    @Override
    public ArrayList<Contacts> findContactsByAccountId(UUID accountId){
        return contactsRepository.findByAccountId(accountId);
    }

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

    @Override
    public Contacts saveChanges(Contacts contacts){
        Contacts oldContacts = findContactsById(contacts.getId());
        if(oldContacts == null){
            return null;
        }else{
            oldContacts.setName(contacts.getName());
            oldContacts.setDocumentType(contacts.getDocumentType());
            oldContacts.setDocumentNumber(contacts.getDocumentNumber());
            oldContacts.setPhoneNumber(contacts.getPhoneNumber());
            contactsRepository.save(oldContacts);
            return oldContacts;
        }
    }

}


