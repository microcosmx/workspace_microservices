package contacts.repository;

import contacts.domain.Contacts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface ContactsRepository extends MongoRepository<Contacts, String> {

    Contacts findById(long id);

    @Query("{ 'accountId' : ?0 }")
    ArrayList<Contacts> findByAccountId(long accountId);
}
