package contacts.init;

import contacts.domain.AddContactsInfo;
import contacts.domain.Contacts;
import contacts.domain.DocumentType;
import contacts.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InitData implements CommandLineRunner{

    @Autowired
    private ContactsService service;

    public void run(String... args)throws Exception{
        Contacts contacts_One = new Contacts();
        contacts_One.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        contacts_One.setDocumentType(DocumentType.ID_CARD.getCode());
        contacts_One.setName("Contacts_One");
        contacts_One.setDocumentNumber("DocumentNumber_One");
        contacts_One.setPhoneNumber("ContactsPhoneNum_One");
        contacts_One.setId(UUID.randomUUID());

        Contacts contacts_Two = new Contacts();
        contacts_Two.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        contacts_Two.setDocumentType(DocumentType.ID_CARD.getCode());
        contacts_Two.setName("Contacts_Two");
        contacts_Two.setDocumentNumber("DocumentNumber_Two");
        contacts_Two.setPhoneNumber("ContactsPhoneNum_Two");
        contacts_Two.setId(UUID.randomUUID());

        service.createContacts(contacts_One);
        service.createContacts(contacts_Two);

        Contacts contacts_user2_One = new Contacts();
        contacts_user2_One.setAccountId(UUID.fromString("03830807-a1ac-4942-aa10-dbe6ed7c7bdf"));
        contacts_user2_One.setDocumentType(DocumentType.ID_CARD.getCode());
        contacts_user2_One.setName("Contacts_One");
        contacts_user2_One.setDocumentNumber("DocumentNumber_One");
        contacts_user2_One.setPhoneNumber("ContactsPhoneNum_One");
        contacts_user2_One.setId(UUID.randomUUID());

        Contacts contacts_user2_Two = new Contacts();
        contacts_user2_Two.setAccountId(UUID.fromString("03830807-a1ac-4942-aa10-dbe6ed7c7bdf"));
        contacts_user2_Two.setDocumentType(DocumentType.ID_CARD.getCode());
        contacts_user2_Two.setName("Contacts_Two");
        contacts_user2_Two.setDocumentNumber("DocumentNumber_Two");
        contacts_user2_Two.setPhoneNumber("ContactsPhoneNum_Two");
        contacts_user2_Two.setId(UUID.randomUUID());

        service.createContacts(contacts_user2_One);
        service.createContacts(contacts_user2_Two);
    }
}
