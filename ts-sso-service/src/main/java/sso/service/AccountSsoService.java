package sso.service;

import sso.domain.PutLoginResult;
import sso.domain.VerifyResult;

public interface AccountSsoService {

    PutLoginResult loginPutToken(String loginToken);

    String logoutDeleteToken(String logoutToken);

    VerifyResult verifyLoginToken(String verifyToken);

}
