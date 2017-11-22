package adminbasic.service;

import adminbasic.domin.bean.Contacts;
import adminbasic.domin.info.DeleteContactsInfo;
import adminbasic.domin.info.ModifyContactsInfo;
import adminbasic.domin.reuslt.AddContactsResult;
import adminbasic.domin.reuslt.DeleteContactsResult;
import adminbasic.domin.reuslt.GetAllContactsResult;
import adminbasic.domin.reuslt.ModifyContactsResult;

public interface AdminBasicInfoService {

    GetAllContactsResult getAllContacts(String loginId);

    AddContactsResult addContact(String loginId, Contacts c);

    DeleteContactsResult deleteContact(String loginId, DeleteContactsInfo dci);

    ModifyContactsResult modifyContact(String loginId, ModifyContactsInfo mci);


//    Contacts login(String name, String password);

}
