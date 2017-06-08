package login.service;

import login.domain.LoginInfo;
import login.domain.LoginResult;
import login.domain.LogoutInfo;
import login.domain.LogoutResult;

public interface AccountLoginService {

    LoginResult login(LoginInfo li,String YsbCaptcha);

    LogoutResult logout(LogoutInfo li);

}
