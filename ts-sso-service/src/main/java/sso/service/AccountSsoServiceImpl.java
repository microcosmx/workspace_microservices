package sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sso.domain.*;
import sso.repository.AccountRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountSsoServiceImpl implements AccountSsoService{

    @Autowired
    private AccountRepository accountRepository;

    private static HashMap<String,String > loginUserList = new HashMap<>();

    @Override
    public Account createAccount(Account account){
        System.out.println("[SSO Service][Create Account] Before:" + account.getId());
        Account resultAcc = accountRepository.save(account);
        Account oldAcc = accountRepository.findByPhoneNum(account.getPhoneNum());
        System.out.println("[SSO Service][Create Account] After:" + oldAcc.getId());
        return resultAcc;
    }

    @Override
    public RegisterResult create(RegisterInfo ri){
        Account oldAcc = accountRepository.findByPhoneNum(ri.getPhoneNum());
        if(oldAcc != null){
            RegisterResult rr = new RegisterResult();
            rr.setStatus(false);
            rr.setMessage("Account Already Exists");
            rr.setAccount(null);
            System.out.println("[SSO Service][Register] Fail.Account already exists.");
            return rr;
        }
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setPhoneNum(ri.getPhoneNum());
        account.setPassword(ri.getPassword());
        account.setName(ri.getName());
        account.setDocumentNum(ri.getDocumentNum());
        account.setDocumentType(ri.getDocumentType());
        account.setGender(ri.getGender());
        Account resultAcc = accountRepository.save(account);
        resultAcc.setPassword("");
        System.out.println("[SSO Service][Register] Success.");
        RegisterResult rr = new RegisterResult();
        rr.setStatus(true);
        rr.setMessage("Success");
        rr.setAccount(account);
        return rr;
    }

    @Override
    public LoginResult login(LoginInfo li){
        if(li == null){
            System.out.println("[SSO Service][Login] Fail.Account not found.");
            LoginResult lr = new LoginResult();
            lr.setStatus(false);
            lr.setMessage("Account Not Found");
            lr.setAccount(null);
            return lr;
        }
        Account result = accountRepository.findByPhoneNum(li.getPhoneNum());
        if(result != null &&
                result.getPassword() != null && li.getPassword() != null
                && result.getPassword().equals(li.getPassword())){
            result.setPassword("");
            System.out.println("[SSO Service][Login] Success.");
            LoginResult lr = new LoginResult();
            lr.setStatus(true);
            lr.setMessage("Success");
            lr.setAccount(result);
            return lr;
        }else{
            LoginResult lr = new LoginResult();
            lr.setStatus(false);
            lr.setAccount(null);
            if(result == null){
                lr.setMessage("Account Not Exist");
                System.out.println("[SSO Service][Login] Fail.Account Not Exist.");
            }else{
                lr.setMessage("Password Wrong");
                System.out.println("[SSO Service][Login] Fail.Wrong Password.");
            }
            return lr;
        }
    }

    @Override
    public PutLoginResult loginPutToken(String loginId){
        PutLoginResult plr = new PutLoginResult();
        if(loginUserList.keySet().contains(loginId)){
            System.out.println("[SSO Service][Login] Already Login, Token:" + loginId);
            plr.setStatus(false);
            plr.setLoginId(loginId);
            plr.setMsg("Already Login");
            plr.setToken(null);

        }else{
            String token = UUID.randomUUID().toString();
            loginUserList.put(loginId,token);
            System.out.println("[SSO Service][Login] Login Success. Id:" + loginId + " Token:" + token);
            plr.setStatus(true);
            plr.setLoginId(loginId);
            plr.setMsg("Success");
            plr.setToken(token);
        }
        return plr;
    }

    @Override
    public LogoutResult logoutDeleteToken(LogoutInfo li){
        LogoutResult lr = new LogoutResult();
        if(!loginUserList.keySet().contains(li.getId())){
            System.out.println("[SSO Service][Logout] Already Logout. LogoutId:" + li.getId());
           lr.setStatus(false);
           lr.setMessage("Not Login");
        }else{
            String savedToken = loginUserList.get(li.getId());
            if(savedToken.equals(li.getToken())){
                loginUserList.remove(li.getId());
                lr.setStatus(true);
                lr.setMessage("Success");
            }else{
                lr.setStatus(false);
                lr.setMessage("Token Wrong");
            }
        }
        return lr;
    }

    @Override
    public VerifyResult verifyLoginToken(String verifyToken){
        System.out.println("[SSO Service][Verify] Verify token:" + verifyToken);
        VerifyResult vr = new VerifyResult();
        if(loginUserList.values().contains(verifyToken)){
            vr.setStatus(true);
            vr.setMessage("Verify Success.");
            System.out.println("[SSO Service][Verify] Success.Token:" + verifyToken);
        }else{
            vr.setStatus(false);
            vr.setMessage("Verify Fail.");
            System.out.println("[SSO Service][Verify] Fail.Token:" + verifyToken);
        }
        return vr;
    }

    @Override
    public FindAllAccountResult findAllAccount(){
        FindAllAccountResult findAllAccountResult = new FindAllAccountResult();
        ArrayList<Account> accounts = accountRepository.findAll();
        for(int i = 0;i < accounts.size();i++){
            System.out.println("[SSO Service][Find All Account]" + accounts.get(i).getId());
        }
        findAllAccountResult.setStatus(true);
        findAllAccountResult.setMessage("Success.");
        findAllAccountResult.setAccountArrayList(accounts);
        return findAllAccountResult;
    }

    @Override
    public GetLoginAccountList findAllLoginAccount(){
        ArrayList<LoginAccountValue> values = new ArrayList<>();
        for(Map.Entry<String,String> entry : loginUserList.entrySet()){
            LoginAccountValue value = new LoginAccountValue(entry.getKey(),entry.getValue());
            values.add(value);
        }
        GetLoginAccountList getLoginAccountList = new GetLoginAccountList();
        getLoginAccountList.setStatus(true);
        getLoginAccountList.setMessage("Success");
        getLoginAccountList.setLoginAccountList(values);
        return getLoginAccountList;
    }

    @Override
    public ModifyAccountResult saveChanges(ModifyAccountInfo modifyAccountInfo){
        Account existAccount = accountRepository.findByPhoneNum(modifyAccountInfo.getNewEmail());
        ModifyAccountResult result = new ModifyAccountResult();
        if(existAccount != null){
            System.out.println("[SSO Service][Modify Info] Email exists.");
            result.setStatus(false);
            result.setMessage("Email Has Been Occupied.");
            return result;
        }

        System.out.println("[SSO Service][Modify Info] Account Id:" + modifyAccountInfo.getAccountId());
        Account oldAccount = accountRepository.findById(UUID.fromString(modifyAccountInfo.getAccountId()));

        if(oldAccount == null){
            System.out.println("[SSO Service][Modify Info] Fail.Can not found account.");
            result.setStatus(false);
            result.setMessage("Account Not Found.");
        }else{
            oldAccount.setPhoneNum(modifyAccountInfo.getNewEmail());
            accountRepository.save(oldAccount);
            oldAccount.setPassword("");
            System.out.println("[SSO Service][ModifyInfo] Success.");
            result.setStatus(true);
            result.setMessage("Success.");
        }
        return result;
    }
}
