package query.service;

import query.domain.AddContactsInfo;
import query.domain.Contacts;

public interface ContactsService {

    Contacts create(AddContactsInfo aci);

}
