package contacts.service;

import contacts.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import contacts.repository.ContactsRepository;
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

    @Override
    public AddContactsResult create(AddContactsInfo aci){
        Contacts contacts = new Contacts();
        contacts.setId(UUID.randomUUID());
        contacts.setName(aci.getName());
        contacts.setPhoneNumber(aci.getPhoneNumber());
        contacts.setDocumentNumber(aci.getDocumentNumber());
        contacts.setAccountId(aci.getAccountId());
        contacts.setDocumentType(aci.getDocumentType());

        ArrayList<Contacts> accountContacts = contactsRepository.findByAccountId(contacts.getAccountId());
        if(accountContacts.contains(contacts)){
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Fail.Contacts already exists");
            AddContactsResult acr = new AddContactsResult();
            acr.setStatus(false);
            acr.setMessage("Contacts Already Exists");
            acr.setContacts(null);
            return acr;
        }else{
            contactsRepository.save(contacts);
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Success.");
            AddContactsResult acr = new AddContactsResult();
            acr.setStatus(true);
            acr.setMessage("Success");
            acr.setContacts(contacts);
            return acr;
        }
    }

    @Override
    public DeleteContactsResult delete(UUID contactsId){
        contactsRepository.deleteById(contactsId);
        Contacts contacts = contactsRepository.findById(contactsId);
        if(contacts == null){
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Success.");
            DeleteContactsResult dcr = new DeleteContactsResult();
            dcr.setStatus(true);
            dcr.setMessage("Success");
            return dcr;
        }else{
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Fail.Reason not clear.");
            DeleteContactsResult dcr = new DeleteContactsResult();
            dcr.setStatus(false);
            dcr.setMessage("Reason Not clear");
            return dcr;
        }
    }

    @Override
    public ModifyContactsResult saveChanges(Contacts contacts){
        Contacts oldContacts = findContactsById(contacts.getId());
        if(oldContacts == null){
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Fail.Contacts not found.");
            ModifyContactsResult mcr = new ModifyContactsResult();
            mcr.setStatus(false);
            mcr.setMessage("Contacts not found");
            mcr.setContacts(null);
            return mcr;
        }else{
            oldContacts.setName(contacts.getName());
            oldContacts.setDocumentType(contacts.getDocumentType());
            oldContacts.setDocumentNumber(contacts.getDocumentNumber());
            oldContacts.setPhoneNumber(contacts.getPhoneNumber());
            contactsRepository.save(oldContacts);
            System.out.println("[Contacts-Modify-Service][ModifyContacts] Success.");
            ModifyContactsResult mcr = new ModifyContactsResult();
            mcr.setStatus(true);
            mcr.setMessage("Success");
            mcr.setContacts(contacts);
            return mcr;
        }
    }

}


