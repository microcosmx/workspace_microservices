package contacts.service;

import contacts.domain.Contacts;
import java.util.ArrayList;

public interface ContactsService {

    Contacts findContactsById(long id);

    ArrayList<Contacts> findContactsByAccountId(long accountId);

    Contacts create(Contacts contacts);

    Contacts saveChanges(Contacts contacts);
}
