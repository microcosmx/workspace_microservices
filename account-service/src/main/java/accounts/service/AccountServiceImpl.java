package accounts.service;
import accounts.domain.Account;
import accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findById(int id){
        return accountRepository.findById(id);
    }

    @Override
    public Account create(Account account){
        accountRepository.save(account);
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
            return oldAccount;
        }
    }

}
