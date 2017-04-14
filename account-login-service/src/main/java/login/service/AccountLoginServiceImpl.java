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
            return null;
        }
        Account result = accountRepository.findByPhoneNum(li.getPhoneNum());
        if(result != null &&
                result.getPassword() != null && li.getPassword() != null
                && result.getPassword().equals(li.getPassword())){
            result.setPassword("");
            return result;
        }else{
            return null;
        }
    }

}
