package accounts.service;

import accounts.domain.Account;

public interface AccountService {

    Account findById(long id);

    Account create(Account account);

    Account saveChanges(Account account);

}
