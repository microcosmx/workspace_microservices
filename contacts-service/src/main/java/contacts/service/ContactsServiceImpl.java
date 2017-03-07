package contacts.service;

import contacts.domain.Contacts;
import contacts.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Contacts findContactsById(long id){
        return contactsRepository.findById(id);
    }

    @Override
    public ArrayList<Contacts> findContactsByAccountId(long accountId){
        return contactsRepository.findByAccountId(accountId);
    }

    @Override
    public Contacts create(Contacts contacts){
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


