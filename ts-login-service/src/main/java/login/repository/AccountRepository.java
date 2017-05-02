package login.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import login.domain.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    @Query("{ 'phoneNum': ?0 }")
    Account findByPhoneNum(String phoneNum);

}
