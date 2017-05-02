package sso.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class AccountSsoServiceImpl implements AccountSsoService{

    private static ArrayList<String> loginUserList = new ArrayList<>();

    @Override
    public String loginPutToken(String loginToken){

    }

    @Override
    public String logoutDeleteToken(String logoutToken){
        return null;
    }

    @Override
    public boolean verifyLoginToken(String verifyToken){
        System.out.println("[Account-SSO-Service][Verify] Verify token:" + verifyToken);
        return loginUserList.contains(verifyToken);
    }
}
