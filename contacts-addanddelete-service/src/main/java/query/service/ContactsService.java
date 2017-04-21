package query.service;

import query.domain.AddContactsInfo;
import query.domain.Contacts;
import java.util.UUID;

public interface ContactsService {

    Contacts create(AddContactsInfo aci);

    String delete(UUID contactsId);

}
