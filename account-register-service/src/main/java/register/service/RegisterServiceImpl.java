package register.service;

import register.domain.Account;
import register.domain.RegisterInfo;
import register.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account create(RegisterInfo ri){
        Account oldAcc = accountRepository.findByPhoneNum(ri.getPhoneNum());
        if(oldAcc != null){
            return null;
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
        return account;
    }

}
