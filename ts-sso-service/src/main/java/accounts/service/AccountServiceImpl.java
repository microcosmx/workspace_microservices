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
    public Account findByPhoneNum(String phoneNum){
        Account account = accountRepository.findByPhoneNum(phoneNum);
        if(account == null){
            System.out.println("[Account-Manage-Service][FindAccountByPhoneNum] Fail.Can not found account.");
        }else{
            System.out.println("[Account-Manage-Service][FindAccountByPhoneNum] Success.");
        }
        return account;
    }

    @Override
    public Account saveChanges(Account account){
        Account oldAccount = accountRepository.findById(account.getId());
        if(oldAccount == null){
            System.out.println("[Account-Manage-Service][ModifyInfo] Fail.Can not found account.");
            return null;
        }else{
            oldAccount.setName(account.getName());
            oldAccount.setGender(account.getGender());
            oldAccount.setPhoneNum(account.getPhoneNum());
            oldAccount.setDocumentType(account.getDocumentType());
            oldAccount.setDocumentNum(account.getDocumentNum());
            accountRepository.save(oldAccount);
            oldAccount.setPassword("");
            System.out.println("[Account-Manage-Service][ModifyInfo] Success.");
            return oldAccount;
        }
    }

    @Override
    public Account changePassword(NewPasswordInfo npi){
        System.out.println("[Change Password]");
        Account oldAccount = accountRepository.findById(npi.getId());
        if(oldAccount == null){
            System.out.println("[Account-Manage-Service][ChangePassword] Fail.Can not found account.");
            return null;
        }else{
            if(npi != null && npi.getOldPassword() != null &
                    npi.getNewPassword() != null &&
                    oldAccount.getPassword().equals(npi.getOldPassword())){
                oldAccount.setPassword(npi.getNewPassword());
                accountRepository.save(oldAccount);
                oldAccount.setPassword("");
                System.out.println("[Account-Manage-Service][ChangePassword] Success.");
                return oldAccount;
            }else{
                System.out.println("[Account-Manage-Service][ChangePassword] Fail.Wrong Password.");
                return null;
            }
        }
    }

}
