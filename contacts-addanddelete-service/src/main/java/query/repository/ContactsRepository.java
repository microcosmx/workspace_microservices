package query.repository;

import query.domain.Contacts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface ContactsRepository extends MongoRepository<Contacts, String> {

    Contacts findById(UUID id);

    @Query("{ 'accountId' : ?0 }")
    ArrayList<Contacts> findByAccountId(UUID accountId);

}
