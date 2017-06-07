package sso.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sso.domain.DocumentType;
import sso.domain.Gender;
import sso.domain.RegisterInfo;
import sso.service.AccountSsoService;

@Component
public class initData implements CommandLineRunner {

    @Autowired
    private AccountSsoService ssoService;

    @Override
    public void run(String... args) throws Exception {
        RegisterInfo ri = new RegisterInfo();
        ri.setDocumentNum("DefaultDocumentNumber");
        ri.setDocumentType(DocumentType.ID_CARD.getCode());
        ri.setGender(Gender.MALE.getCode());
        ri.setName("Default User");
        ri.setPassword("DefaultPassword");
        ri.setPhoneNum("DefaultPhoneNum");
        ri.setVerificationCode("NoUse");
        ssoService.create(ri);
        System.out.println("[SSO Service] Create Default User complete.");
    }

}
