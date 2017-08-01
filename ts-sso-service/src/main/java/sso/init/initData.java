package sso.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sso.domain.Account;
import sso.domain.DocumentType;
import sso.domain.Gender;
import sso.service.AccountSsoService;
import java.util.UUID;

@Component
public class initData implements CommandLineRunner {

    @Autowired
    private AccountSsoService ssoService;

    @Override
    public void run(String... args) throws Exception {
        Account acc = new Account();
        acc.setDocumentType(DocumentType.ID_CARD.getCode());
        acc.setDocumentNum("DefaultDocumentNumber");
        acc.setEmail("fdse_microservices@163.com");
        acc.setPassword("DefaultPassword");
        acc.setName("Default User");
        acc.setGender(Gender.MALE.getCode());
        acc.setId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        ssoService.createAccount(acc);

        Account acc2 = new Account();
        acc2.setDocumentType(DocumentType.ID_CARD.getCode());
        acc2.setDocumentNum("DefaultDocumentNumber");
        acc2.setEmail("chaojifudan@outlook.com");
        acc2.setPassword("DefaultPassword");
        acc2.setName("Default User2");
        acc2.setGender(Gender.MALE.getCode());
        acc2.setId(UUID.fromString("03830807-a1ac-4942-aa10-dbe6ed7c7bdf"));
        ssoService.createAccount(acc2);

    }

}
