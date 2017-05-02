package sso.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class AccountSsoServiceImpl implements AccountSsoService{

    private static ArrayList<String> loginUserList = new ArrayList<>();

    @Override
    public String loginPutToken(String loginToken){
        if(loginUserList.contains(loginToken)){
            System.out.println("[Account-SSO-Service][Login] Already Login, Token:" + loginToken);
            return "status=AlreadyLogin";
        }else{
            loginUserList.add(loginToken);
            System.out.println("[Account-SSO-Service][Login] Login Success. Token:" + loginToken);
            return "status=LoginSuccess";
        }
    }

    @Override
    public String logoutDeleteToken(String logoutToken){
        if(!loginUserList.contains(logoutToken)){
            System.out.println("[Account-SSO-Service][Logout] Already Logout. Token:" + logoutToken);
            return "status=AlreadyLogout";
        }else{
            for(int i = 0;i < loginUserList.size();i++){
                if(loginUserList.get(i).toString().equals(logoutToken)){
                    loginUserList.remove(i);
                    break;
                }
            }
            System.out.println("[Account-SSO-Service][Logout] Logout Success. Token:" + logoutToken);
            return "status=LogoutSuccess";
        }
    }

    @Override
    public String verifyLoginToken(String verifyToken){
        System.out.println("[Account-SSO-Service][Verify] Verify token:" + verifyToken);
        if(loginUserList.contains(verifyToken)){
            return "status=VerifySuccess";
        }else{
            return "status=VerifyFail";
        }
    }
}
