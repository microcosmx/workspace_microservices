package adminbasic.service;

import adminbasic.domin.info.DeleteContactsInfo;
import adminbasic.domin.info.ModifyContactsInfo;
import adminbasic.domin.reuslt.DeleteContactsResult;
import adminbasic.domin.reuslt.GetAllContactsResult;
import adminbasic.domin.reuslt.ModifyContactsResult;

public interface AdminBasicInfoService {

    GetAllContactsResult getAllContacts(String loginId);

//    AddContactsResult addContact(String loginId, AddContactsInfo aci);

    DeleteContactsResult deleteContact(String loginId, DeleteContactsInfo dci);

    ModifyContactsResult modifyContact(String loginId, ModifyContactsInfo mci);


//    Contacts login(String name, String password);

}
