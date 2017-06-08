package contacts.service;

import contacts.domain.*;
import java.util.ArrayList;
import java.util.UUID;

public interface ContactsService {

    Contacts createContacts(Contacts contacts);

    Contacts findContactsById(UUID id);

    ArrayList<Contacts> findContactsByAccountId(UUID accountId);

    AddContactsResult create(AddContactsInfo aci);

    DeleteContactsResult delete(UUID contactsId);

    ModifyContactsResult saveChanges(Contacts contacts);

}
