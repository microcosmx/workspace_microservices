package modify.service;

import modify.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import modify.domain.Contacts;
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


