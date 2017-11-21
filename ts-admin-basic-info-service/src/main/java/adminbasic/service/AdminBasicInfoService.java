package adminbasic.service;

import adminbasic.domin.bean.Contacts;
import adminbasic.domin.reuslt.GetAllContactsResult;

public interface AdminBasicInfoService {

    GetAllContactsResult getAllContacts(String loginId);

    Contacts login(String name, String password);

}
