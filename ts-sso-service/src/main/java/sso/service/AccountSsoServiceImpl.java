package sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sso.domain.PutLoginResult;
import sso.domain.VerifyResult;
import sso.repository.AccountRepository;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AccountSsoServiceImpl implements AccountSsoService{


    @Autowired
    private AccountRepository accountRepository;

    private static HashMap<String,String > loginUserList = new HashMap<>();

    @Override
    public PutLoginResult loginPutToken(String loginId){
        PutLoginResult plr = new PutLoginResult();
        if(loginUserList.keySet().contains(loginId)){
            System.out.println("[Account-SSO-Service][Login] Already Login, Token:" + loginId);
            plr.setStatus(false);
            plr.setLoginId(loginId);
            plr.setMsg("Already Login");
            plr.setToken(null);

        }else{
            String token = UUID.randomUUID().toString();
            loginUserList.put(loginId,token);
            System.out.println("[Account-SSO-Service][Login] Login Success. Id:" + loginId + " Token:" + token);
            plr.setStatus(true);
            plr.setLoginId(loginId);
            plr.setMsg("Success");
            plr.setToken(token);
        }
        return plr;
    }

    @Override
    public String logoutDeleteToken(String logoutId){
        if(!loginUserList.keySet().contains(logoutId)){
            System.out.println("[Account-SSO-Service][Logout] Already Logout. LogoutId:" + logoutId);
            return "status=AlreadyDelete";
        }else{
            loginUserList.remove(logoutId);
            System.out.println("[Account-SSO-Service][Logout] Logout Success. Logout:" + logoutId);
            return "status=DeleteSuccess";
        }
    }

    @Override
    public VerifyResult verifyLoginToken(String verifyToken){
        System.out.println("[Account-SSO-Service][Verify] Verify token:" + verifyToken);
        VerifyResult vr = new VerifyResult();
        if(loginUserList.values().contains(verifyToken)){
            vr.setStatus(true);
            vr.setMessage("Verify Success.");
            System.out.println("[Account-SSO-Service][Verify] Success.Token:" + verifyToken);
        }else{
            vr.setStatus(false);
            vr.setMessage("Verify Fail.");
            System.out.println("[Account-SSO-Service][Verify] Fail.Token:" + verifyToken);
        }
        return vr;
    }


}
