package sso.repository;

import org.springframework.data.repository.CrudRepository;
import sso.domain.Account;

import java.util.ArrayList;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {

    Account findByEmail(String email);

    Account findById(UUID id);

    ArrayList<Account> findAll();

}
