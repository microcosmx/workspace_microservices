
/********************************************************************/
/********************Function For Contacts Service*******************/

$("#add_contacts_button").click(function() {
    var addContactsInfo = new Object();
    addContactsInfo.name = $("#add_contacts_name").val();
    addContactsInfo.documentType = $("#add_contacts_documentType").val();
    addContactsInfo.documentNumber = $("#add_contacts_documentNum").val();
    addContactsInfo.phoneNumber = $("#add_contacts_phoneNum").val();
    addContactsInfo.accountId = $("#user_login_id").html();
    addContactsInfo.loginToken = $("#user_login_token").html();
    var data = JSON.stringify(addContactsInfo);
    $.ajax({
        type: "post",
        url: "/contacts/create",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            $("#add_contacts_result_status").html(JSON.stringify(obj["status"]));
            $("#add_contacts_result_msg").html(obj["message"]);
            $("#add_contacts_result_contacts").html(JSON.stringify(obj["contacts"]));
        }
    });
});

$("#refresh_contacts_button").click(function () {
    refresh_contacts();
});

function refresh_contacts(){
    $.ajax({
        type: "get",
        url: "/contacts/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result["contacts"];
            $("#contacts_list_table").find("tbody").html("");
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#contacts_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i                                                    + "</td>" +
                    "<td class='all_contacts_id noshow_component'>" + obj[i]["id"]                + "</td>" +
                    "<td>" + obj[i]["accountId"]                                  + "</td>" +
                    "<td ><input class='all_contacts_name form-control' value='" + obj[i]["name"] + "'></td>" +
                    "<td>" + convertNumberToHtmlDocumentType(obj[i]["documentType"])  + "</td>" +
                    "<td ><input class='all_contacts_documentNum form-control' value='" + obj[i]["documentNumber"] + "'></td>" +
                    "<td ><input class='all_contacts_phoneNum form-control' value='" + obj[i]["phoneNumber"] + "'></td>" +
                    "<td>" +  "<button class='all_contacts_update btn btn-primary'>Update</button>" + "<button class='all_contacts_delete btn btn-primary'>Delete</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToAllContactsTable();
            alert("Success.");
        }
    });
}

function addListenerToAllContactsTable(){
    var allContactsUpdateBtnSet = $(".all_contacts_update");
    for(var i = 0;i < allContactsUpdateBtnSet.length;i++){
        allContactsUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.contactsId = $(this).parents("tr").find(".all_contacts_id").text();
            modifyInfo.documentNumber = $(this).parents("tr").find(".all_contacts_documentNum").val();
            modifyInfo.name = $(this).parents("tr").find(".all_contacts_name").val();
            modifyInfo.documentType = $(this).parents("tr").find(".all_contacts_documentType").val();;
            modifyInfo.phoneNumber = $(this).parents("tr").find(".all_contacts_phoneNum").val();
            modifyInfo.loginToken = "NotNeed";
            var data = JSON.stringify(modifyInfo);
            $.ajax({
                type: "post",
                url: "/contacts/modifyContacts",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_contacts();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
    var allContactsDeleteBtnSet = $(".all_contacts_delete");
    for(var i = 0;i < allContactsDeleteBtnSet.length;i++){
        allContactsDeleteBtnSet[i].onclick = function(){
            var deleteInfo = new Object();
            deleteInfo.contactsId = $(this).parents("tr").find(".all_contacts_id").text();
            deleteInfo.loginToken = "NotNeed";
            var data = JSON.stringify(deleteInfo);
            $.ajax({
                type: "post",
                url: "/contacts/deleteContacts",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_contacts();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

function convertNumberToHtmlDocumentType(number){
    var result = "";
    if(number == 1){
        result =
            "<select  class='all_contacts_documentType form-control' name='documentType'>" +
            "<option value='1' selected = 'selected'>ID Card</option>" +
            "<option value='2'>Passport</option>" +
            "<option value='3'>Other</option>" +
            "</select>";
    }else if(number == 2){
        result =
            "<select class='all_contacts_documentType form-control' name='documentType'>" +
            "<option value='1'>ID Card</option>" +
            "<option value='2' selected = 'selected'>Passport</option>" +
            "<option value='3'>Other</option>" +
            "</select>";
    }else{
        result =
            "<select  class='all_contacts_documentType form-control' name='documentType'>" +
            "<option value='1'>ID Card</option>" +
            "<option value='2'>Passport</option>" +
            "<option value='3' selected = 'selected'>Other</option>" +
            "</select>";
    }
    return result;
}
