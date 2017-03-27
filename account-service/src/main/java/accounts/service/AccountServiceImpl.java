package accounts.service;

import accounts.domain.Account;
import accounts.domain.NewPasswordInfo;
import accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findById(long id){
        return accountRepository.findById(id);
    }

    @Override
    public Account create(Account account){
        accountRepository.save(account);
        //account.setPassword("");
        return account;
    }

    @Override
    public Account saveChanges(Account account){
        Account oldAccount = accountRepository.findById(account.getId());
        if(oldAccount == null){
            return null;
        }else{
            oldAccount.setName(account.getName());
            oldAccount.setGender(account.getGender());
            oldAccount.setPhoneNum(account.getPhoneNum());
            oldAccount.setDocumentType(account.getDocumentType());
            oldAccount.setDocumentNum(account.getDocumentNum());
            accountRepository.save(oldAccount);
            oldAccount.setPassword("");
            return oldAccount;
        }
    }

    @Override
    public Account changePassword(NewPasswordInfo npi){
        Account oldAccount = accountRepository.findById(npi.getId());
        if(oldAccount == null){
            return null;
        }else{
            if(npi!= null && npi.getOldPassword() != null &
                    npi.getNewPassword() != null &&
                    oldAccount.getPassword().equals(npi.getOldPassword())){
                oldAccount.setPassword(npi.getNewPassword());
                accountRepository.save(oldAccount);
                oldAccount.setPassword("");
                return oldAccount;
            }else{
                return null;
            }
        }
    }

}
