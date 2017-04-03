package accounts.service;

import accounts.domain.Account;
import accounts.domain.NewPasswordInfo;

public interface AccountService {

    Account findById(long id);

    Account create(Account account);

    Account saveChanges(Account account);

    Account changePassword(NewPasswordInfo npi);

}
