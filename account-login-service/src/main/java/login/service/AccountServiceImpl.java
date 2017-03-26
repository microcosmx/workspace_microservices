package login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import login.domain.Account;
import login.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Account findById(long id){
        return accountRepository.findById(id);
    }

}
