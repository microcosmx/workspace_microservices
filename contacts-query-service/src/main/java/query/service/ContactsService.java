package query.service;

import query.domain.Contacts;
import java.util.ArrayList;
import java.util.UUID;

public interface ContactsService {

    Contacts findContactsById(UUID id);

    ArrayList<Contacts> findContactsByAccountId(UUID accountId);

}
