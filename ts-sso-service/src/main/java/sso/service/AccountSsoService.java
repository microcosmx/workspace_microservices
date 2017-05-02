package sso.service;

public interface AccountSsoService {

    String loginPutToken(String loginToken);

    String logoutDeleteToken(String logoutToken);

    boolean verifyLoginToken(String verifyToken);

}
