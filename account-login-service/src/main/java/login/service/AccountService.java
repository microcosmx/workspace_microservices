package login.service;

import login.domain.Account;
import login.domain.LoginInfo;

public interface AccountService {

    Account login(LoginInfo li);

}
