package login.service;

import login.domain.LoginInfo;
import login.domain.LoginResult;

public interface AccountLoginService {

    LoginResult login(LoginInfo li);

}
