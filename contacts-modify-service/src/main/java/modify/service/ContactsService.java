package modify.service;

import modify.domain.Contacts;
import java.util.UUID;

public interface ContactsService {

    Contacts findContactsById(UUID id);

    Contacts saveChanges(Contacts contacts);

}
