package contacts.service;

import contacts.domain.AddContactsInfo;
import contacts.domain.Contacts;
import java.util.ArrayList;
import java.util.UUID;

public interface ContactsService {

    Contacts findContactsById(UUID id);

    ArrayList<Contacts> findContactsByAccountId(UUID accountId);

    Contacts create(AddContactsInfo aci);

    Contacts saveChanges(Contacts contacts);
}
