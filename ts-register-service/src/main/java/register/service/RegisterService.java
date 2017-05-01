package register.service;

import register.domain.Account;
import register.domain.RegisterInfo;

public interface RegisterService {

    Account create(RegisterInfo ri);
}
