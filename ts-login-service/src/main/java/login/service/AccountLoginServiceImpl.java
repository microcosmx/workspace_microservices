package login.service;

import login.domain.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import login.domain.Account;
import login.repository.AccountRepository;

@Service
public class AccountLoginServiceImpl implements AccountLoginService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account login(LoginInfo li){
        if(li == null){
            System.out.println("[Account-Login-Service][Login] Fail.Account not found.");
            return null;
        }
        Account result = accountRepository.findByPhoneNum(li.getPhoneNum());
        if(result != null &&
                result.getPassword() != null && li.getPassword() != null
                && result.getPassword().equals(li.getPassword())){
            result.setPassword("");
            System.out.println("[Account-Login-Service][Login] Success.");
            return result;
        }else{
            System.out.println("[Account-Login-Service][Login] Fail.Wrong Password.");
            return null;
        }
    }

}
