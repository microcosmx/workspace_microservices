package login.service;

import login.domain.LoginInfo;
import login.domain.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import login.domain.Account;
import login.repository.AccountRepository;

@Service
public class AccountLoginServiceImpl implements AccountLoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public LoginResult login(LoginInfo li){
        if(li == null){
            System.out.println("[Account-Login-Service][Login] Fail.Account not found.");
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
            System.out.println("[Account-Login-Service][Login] Success.");
            LoginResult lr = new LoginResult();
            lr.setStatus(true);
            lr.setMessage("Success");
            lr.setAccount(result);
            return lr;
        }else{
            System.out.println("[Account-Login-Service][Login] Fail.Wrong Password.");
            LoginResult lr = new LoginResult();
            lr.setStatus(false);
            lr.setMessage("Password Wrong");
            lr.setAccount(null);
            return lr;
        }
    }

}
