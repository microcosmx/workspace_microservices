package login.service;

import login.domain.Account;
import login.domain.LoginInfo;

public interface AccountLoginService {

    Account login(LoginInfo li);

}
