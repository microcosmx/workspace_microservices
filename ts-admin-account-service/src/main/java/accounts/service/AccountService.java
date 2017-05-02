package accounts.service;

import accounts.domain.Account;
import accounts.domain.NewPasswordInfo;

public interface AccountService {

    Account findByPhoneNum(String phoneNum);

    Account saveChanges(Account account);

    Account changePassword(NewPasswordInfo npi);

}
