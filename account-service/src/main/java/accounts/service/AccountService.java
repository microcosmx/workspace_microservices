package accounts.service;

import accounts.domain.Account;

public interface AccountService {

    Account findById(int id);

    Account create(Account account);

    Account saveChanges(Account account);

}
