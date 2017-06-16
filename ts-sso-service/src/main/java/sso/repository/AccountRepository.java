package sso.repository;

import sso.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    @Query("{ 'phoneNum': ?0 }")
    Account findByPhoneNum(String phoneNum);

    @Query("{ 'id': ?0 }")
    Account findById(UUID id);

    ArrayList<Account> findAll();

}
