package sso.service;

import sso.domain.LogoutInfo;
import sso.domain.LogoutResult;
import sso.domain.PutLoginResult;
import sso.domain.VerifyResult;

public interface AccountSsoService {

    PutLoginResult loginPutToken(String loginToken);

    LogoutResult logoutDeleteToken(LogoutInfo li);

    VerifyResult verifyLoginToken(String verifyToken);

}
