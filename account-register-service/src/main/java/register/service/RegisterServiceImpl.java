package register.service;

import register.domain.Account;
import register.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account create(Account account){
        accountRepository.save(account);
        account.setPassword("");
        return account;
    }

}
