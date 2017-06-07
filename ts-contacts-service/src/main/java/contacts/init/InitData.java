package contacts.init;

import contacts.domain.AddContactsInfo;
import contacts.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Chenjie Xu on 2017/6/5.
 */
@Component
public class InitData implements CommandLineRunner{

    @Autowired
    ContactsService service;

    public void run(String... args)throws Exception{
        AddContactsInfo info = new AddContactsInfo();

//        info.setAccountId();
//        info.setDocumentNumber();
//        info.setDocumentType();
//        info.setLoginToken();
//        info.setName();
//        info.setPhoneNumber();
//
//        service.create(info);
    }
}
