package register.repository;

import org.springframework.data.mongodb.repository.Query;
import register.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    @Query("{ 'phoneNum': ?0 }")
    Account findByPhoneNum(String phoneNum);
}
