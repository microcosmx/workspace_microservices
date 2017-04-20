package query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import query.domain.Contacts;
import query.repository.ContactsRepository;
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
        ArrayList<Contacts> arr = new ArrayList<>();
        arr = contactsRepository.findByAccountId(accountId);
        System.out.println("[Contacts-Query-Service][Query-Contacts] Result Size:" + arr.size());
        return arr;
    }

}


