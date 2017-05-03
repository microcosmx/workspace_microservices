package sso.service;

import sso.domain.VerifyResult;

public interface AccountSsoService {

    String loginPutToken(String loginToken);

    String logoutDeleteToken(String logoutToken);

    VerifyResult verifyLoginToken(String verifyToken);

}
