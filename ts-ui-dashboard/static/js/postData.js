//----------------Switch from many tags
//For toggle pages
$("#microservice_page").click(function(){
    $("#microservices").css('display','block');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','none');
});

$("#flow_one_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','block');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','none');
});

$("#flow_two_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','block');
    $("#flow_three").css('display','none');
});

$("#flow_three_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','block');
});

/********************************************************************/
/********************Function For SSO Service************************/

$("#refresh_account_button").click(function() {
    refresh_account();
});

function refresh_account() {
    $.ajax({
        type: "get",
        url: "/account/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#account_list_table").find("tbody").html("");
            var obj = result["accountArrayList"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='sso_account_id'>" + obj[i]["id"] + "</td>" +
                    "<td ><input class='sso_account_phoneNum form-control' value='" + obj[i]["phoneNum"] + "'></td>" +
                    "<td>" + "<button class='account_update_btn btn btn-primary'>Update</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToSsoAccountTable();
            alert("Success.");
        }
    });
}

function addListenerToSsoAccountTable(){
    var accountUpdateBtnSet = $(".account_update_btn");
    for(var i = 0;i < accountUpdateBtnSet .length;i++){
        accountUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.accountId = $(this).parents("tr").find(".sso_account_id").text();
            modifyInfo.newEmail = $(this).parents("tr").find(".sso_account_phoneNum").val();
            var data = JSON.stringify(modifyInfo);
            $.ajax({
                type: "post",
                url: "/account/modify",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_account();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

$("#refresh_login_account_button").click(function() {
    refresh_login_account();
});

function refresh_login_account() {
    $.ajax({
        type: "get",
        url: "/account/findAllLogin",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#login_account_list_table").find("tbody").html("");
            var obj = result["loginAccountList"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#login_account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='account_kick_id'>" + obj[i]["accountId"] + "</td>" +
                    "<td class='account_kick_token'>" + obj[i]["loginToken"] + "</td>" +
                    "<td>" +  "<button class='account_kick_off_btn btn btn-primary'>Kick Off</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToSsoLoginAccountTable();
            alert("Success.");
        }
    });
}

function addListenerToSsoLoginAccountTable(){
    var accountKickOffBtnSet = $(".account_kick_off_btn");
    for(var i = 0;i < accountKickOffBtnSet.length;i++){
        accountKickOffBtnSet[i].onclick = function(){
            var logoutInfo = new Object();
            logoutInfo.id = $(this).parents("tr").find(".account_kick_id").text();
            logoutInfo.token = $(this).parents("tr").find(".account_kick_token").text();
            var data = JSON.stringify(logoutInfo);
            $.ajax({
                type: "post",
                url: "/logout",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_login_account();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

$("#logout_button").click(function() {
    var logoutInfo = new Object();
    logoutInfo.id = $("#user_login_id").html();
    logoutInfo.token = $("#user_login_token").html();
    var data = JSON.stringify(logoutInfo);
    $.ajax({
        type: "post",
        url: "/logout",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result["status"] == true){
                $("#user_login_id").html("Not Login");
                $("#user_login_token").html("Please Login");
            }else if(result["message"] == "Not Login"){
                $("#user_login_id").html("Not Login");
                $("#user_login_token").html("Please Login");
            }
        }
    });
});

/********************************************************************/
/********************Function For Security Service**********************/
$("#refresh_security_config_button").click(function() {
    refresh_security_config();
});

function refresh_security_config() {
    $.ajax({
        type: "get",
        url: "/securityConfig/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#security_config_list_table").find("tbody").html("");
            var obj = result["result"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#security_config_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='noshow_component list_security_config_id'>" + obj[i]["id"] + "</td>" +
                    "<td class='list_security_config_name'>" + obj[i]["name"] + "</td>" +
                    "<td ><input class='list_security_config_id_value form-control' value='" + obj[i]["value"] + "'></td>" +
                    "<td ><input class='list_security_config_id_description form-control' value='" + obj[i]["description"] + "'></td>" +
                    "<td>" + "<button class='security_config_update_btn btn btn-primary'>Update</button>" + "<button class='security_config_delete btn btn-primary'>Delete</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToAllSecurityConfigTable();
            alert("Success.");
        }
    });
}

function addListenerToAllSecurityConfigTable(){
    var allSecurityConfigUpdateBtnSet = $(".security_config_update_btn");
    for(var i = 0;i < allSecurityConfigUpdateBtnSet.length;i++){
        allSecurityConfigUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.id = $(this).parents("tr").find(".list_security_config_id").text();
            modifyInfo.name = $(this).parents("tr").find(".list_security_config_name").text();
            modifyInfo.value = $(this).parents("tr").find(".list_security_config_id_value").val();
            modifyInfo.description = $(this).parents("tr").find(".list_security_config_id_description").val();
            var data = JSON.stringify(modifyInfo);
            $.ajax({
                type: "post",
                url: "/securityConfig/update",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_security_config();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
    var allSecurityConfigDeleteBtnSet = $(".security_config_delete");
    for(var i = 0;i < allSecurityConfigDeleteBtnSet.length;i++){
        allSecurityConfigDeleteBtnSet[i].onclick = function(){
            var deleteInfo = new Object();
            deleteInfo.id = $(this).parents("tr").find(".list_security_config_id").text();
            var data = JSON.stringify(deleteInfo);
            $.ajax({
                type: "post",
                url: "/securityConfig/delete",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_security_config();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

/********************************************************************/
/********************Function For Login Service**********************/

$("#login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.phoneNum = $("#login_phoneNum").val();
    loginInfo.password = $("#login_password").val();
    loginInfo.verificationCode = $("#login_verification_code").val();
    var data = JSON.stringify(loginInfo);

    $.ajax({
        type: "post",
        url: "/login",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#user_login_id").html(obj["account"].id);
                $("#user_login_token").html(obj["token"]);
            }
            $("#login_result_status").html(JSON.stringify(obj["status"]));
            $("#login_result_msg").html(obj["message"]);
            $("#login_result_account").html(JSON.stringify(obj["account"]));
            $("#login_result_token").html(JSON.stringify(obj["token"]));
        }
    });
});

/********************************************************************/
/********************Function For Register Service*******************/

$("#register_button").click(function() {
    var registerInfo = new Object();
    registerInfo.password = $("#register_password").val();
    registerInfo.gender = $("#register_gender").val();
    registerInfo.name = $("#register_name").val();
    registerInfo.documentType = $("#register_documentType").val();
    registerInfo.documentNum = $("#register_documentNum").val();
    registerInfo.phoneNum = $("#register_phoneNum").val();
    registerInfo.verificationCode = $("#register_verificationCode").val();
    var data = JSON.stringify(registerInfo);
    $.ajax({
        type: "post",
        url: "/register",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            $("#register_result_status").html(JSON.stringify(obj["status"]));
            $("#register_result_msg").html(obj["message"]);
            $("#register_result_account").html(JSON.stringify(obj["account"]));
        }
    });
});

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

/********************************************************************/
/********************Function For Order Service*******************/

$("#refresh_order_button").click(function(){
    var typeCheckBox = $(".order_type");
    alert("type check box");
    if(typeCheckBox[0].checked && typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/order/findAll");
        refresh_order("/orderOther/findAll");
    }else if(typeCheckBox[0].checked && !typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/order/findAll");
    }else if(!typeCheckBox[0].checked && typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/orderOther/findAll");
    }else{
        alert("Not Select The Order Type.");
    }
});

function refresh_order(path){
    $.ajax({
        type: "get",
        url: path,
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result["orders"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#all_order_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='all_order_id noshow_component'>" + obj[i]["id"] + "</td>" +
                    "<td>" + obj[i]["accountId"] + "</td>" +
                    "<td class='all_order_trainNum'>" + obj[i]["trainNumber"] + "</td>" +
                    "<td>" + obj[i]["from"] + "</td>" +
                    "<td>" + obj[i]["to"] + "</td>" +
                    "<td>" + mergeTwoDate(obj[i]["travelDate"],obj[i]["travelTime"]) + "</td>" +
                    "<td>" + convertNumberToHtmlOrderStatus(obj[i]["status"]) + "</td>" +
                    "<td>" + "<button class='all_order_update btn btn-primary'>Update</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToAllOrderTable();
            alert("Success.");
        }
    });
}

function addListenerToAllOrderTable(){
    var allOrderUpdateBtnSet = $(".all_order_update");
    for(var i = 0;i < allOrderUpdateBtnSet.length;i++){
        allOrderUpdateBtnSet[i].onclick = function(){
            var updateInfo = new Object();
            updateInfo.orderId = $(this).parents("tr").find(".all_order_id").text();
            updateInfo.status = $(this).parents("tr").find(".all_order_status").val();
            var data = JSON.stringify(updateInfo);
            var path = "";
            var tripType = $(this).parents("tr").find(".all_order_trainNum").text().charAt(0);
            if(tripType == 'G' || tripType == 'D'){
                path = "/order/modifyOrder";
            }else{
                path = "/orderOther/modifyOrder";
            }
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_order();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

function convertNumberToHtmlOrderStatus(number){
    var result = "<select class='all_order_status form-control' name='documentType'>";
    if(number == 0){
        result +=
            "<option selected='selected' value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 1){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option selected='selected' value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 2){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option selected='selected' value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 3){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option selected='selected' value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 4){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option selected='selected' value='4'>Cance</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 5) {
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option selected='selected' value='5'>Refunded</option>";
    }
    result += "</select>";
    return result;
}

/********************************************************************/
/********************Function For Reserve Service********************/

$("#refresh_booking_contacts_button").click(function refresh_booking_contacts(){
    var queryContactsInfo = new Object();
    queryContactsInfo.accountId = $("#user_login_id").html();
    queryContactsInfo.loginToken = $("#user_login_token").html();
    var data = JSON.stringify(queryContactsInfo);
    $.ajax({
        type: "post",
        url: "/contacts/findContacts",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            $("#contacts_booking_list_table").find("tbody").html("");
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#contacts_booking_list_table").find("tbody").append(
                    "<tr>" +
                        "<td>" + i                                                    + "</td>" +
                        "<td class='booking_contacts_contactsId' style='display:none'>" + obj[i]["id"] + "</td>" +
                        "<td class='booking_contacts_name'>" + obj[i]["name"]         + "</td>" +
                        "<td class='booking_contacts_documentType'>" + convertNumberToDocumentType(obj[i]["documentType"]) + "</td>" +
                        "<td class='booking_contacts_documentNumber'>" + obj[i]["documentNumber"] + "</td>" +
                        "<td class='booking_contacts_phoneNumber'>" + obj[i]["phoneNumber"] + "</td>" +
                        "<td>" +  "<label><input class='booking_contacts_select' name='booking_contacts' type='radio' />" + "Select" + "</label>" + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

/********************************************************************/
/********************Function For Station Service********************/

$("#station_update_button").click(function(){
    var stationInfo = new Object();
    stationInfo.id = $("#station_update_id").val();
    stationInfo.name = $("#station_update_name").val();
    var data = JSON.stringify(stationInfo);
    $.ajax({
        type: "post",
        url: "/station/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
    });
});

$("#station_query_button").click(function(){
    $.ajax({
        type: "get",
        url: "/station/query",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_station_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_station_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"]     + "</td>" +
                    "<td>" + result[i]["name"]     + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

//------For Station delete------------
// document.getElementById("station_delete_button").onclick = function post_station_delete(){
//     var stationInfo = new Object();
//     stationInfo.name = $("#station_delete_name").val();
//     var data = JSON.stringify(stationInfo);
//     $.ajax({
//         type: "post",
//         url: "/station/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#station_result").html(JSON.stringify(result));
//         }
//     });
// }

//------For Train------------
//------For Train create------------
// document.getElementById("train_create_button").onclick = function post_train_create(){
//     var trainInfo = new Object();
//     trainInfo.id = $("#train_create_id").val();
//     trainInfo.economyClass = $("#train_create_economyClass").val();
//     trainInfo.confortClass = $("#train_create_confortClass").val();
//     var data = JSON.stringify(trainInfo);
//     $.ajax({
//         type: "post",
//         url: "/train/create",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#train_result").html(JSON.stringify(result));
//         }
//     });
// }

/********************************************************************/
/********************Function For Train Service**********************/

document.getElementById("train_update_button").onclick = function post_train_update(){
    var trainInfo = new Object();
    trainInfo.id = $("#train_update_id").val();
    trainInfo.economyClass = $("#train_update_economyClass").val();
    trainInfo.confortClass = $("#train_update_confortClass").val();
    var data = JSON.stringify(trainInfo);
    $.ajax({
        type: "post",
        url: "/train/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#train_result").html(JSON.stringify(result));
        }
    });
}

document.getElementById("train_query_button").onclick = function post_train_query(){
    // var trainInfo = new Object();
    // trainInfo.id = $("#train_query_id").val();
    // var data = JSON.stringify(trainInfo);
    $.ajax({
        type: "get",
        url: "/train/query",
        // contentType: "application/json",
        dataType: "json",
        // data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_train_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_train_list_table").find("tbody").append(
                    "<tr>" +
                        "<td>" + result[i]["id"]     + "</td>" +
                        "<td>" + result[i]["confortClass"]     + "</td>" +
                        "<td>" + result[i]["economyClass"]     + "</td>" +
                    "</tr>"
                );
            }
            //$("#train_result").html(JSON.stringify(result));
        }
    });
}

//------For Train delete------------
// document.getElementById("train_delete_button").onclick = function post_train_delete(){
//     var trainInfo = new Object();
//     trainInfo.id = $("#train_delete_id").val();
//     var data = JSON.stringify(trainInfo);
//     $.ajax({
//         type: "post",
//         url: "/train/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#train_result").html(JSON.stringify(result));
//         }
//     });
// }
//------For Config------------
//------For Config create------------
// document.getElementById("config_create_button").onclick = function post_config_create(){
//     var configInfo = new Object();
//     configInfo.name = $("#config_create_name").val();
//     configInfo.value = $("#config_create_value").val();
//     configInfo.description = $("#config_create_description").val();
//     var data = JSON.stringify(configInfo);
//     $.ajax({
//         type: "post",
//         url: "/config/create",
//         contentType: "application/json",
//         dataType: "text",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#config_result").html(result);
//         }
//     });
// }


/********************************************************************/
/********************Function For Config Service*********************/

document.getElementById("config_update_button").onclick = function post_config_update(){
    var configInfo = new Object();
    configInfo.name = $("#config_update_name").val();
    configInfo.value = $("#config_update_value").val();
    configInfo.description = $("#config_update_description").val();
    var data = JSON.stringify(configInfo);
    $.ajax({
        type: "post",
        url: "/config/update",
        contentType: "application/json",
        dataType: "text",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#config_result").html(result);
        }
    });
}

//------For config query------------
document.getElementById("config_query_button").onclick = function post_config_query(){
    $.ajax({
        type: "get",
        url: "/config/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_config_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_config_list_table").find("tbody").append(
                    "<tr>" +
                        "<td>" + result[i]["name"]     + "</td>" +
                        "<td>" + result[i]["value"]     + "</td>" +
                        "<td>" + result[i]["description"]     + "</td>" +
                    "</tr>"
                );
            }
            //$("#config_result").html(result);
        }
    });
}

//------For Config delete------------
// document.getElementById("config_delete_button").onclick = function post_config_delete(){
//     var configInfo = new Object();
//     configInfo.name = $("#config_delete_name").val();
//     var data = JSON.stringify(configInfo);
//     $.ajax({
//         type: "post",
//         url: "/config/delete",
//         contentType: "application/json",
//         dataType: "text",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#config_result").html(result);
//         }
//     });
// }


/********************************************************************/
/********************Function For Travel Service**********************/

// $("#travel_create_button").click(function(){
//     var travelCreateInfo = new Object();
// //     var travelCreateInfo = {
// //         tripId : $("#travel_create_tripId").val();
// // }
//     travelCreateInfo.tripId = $("#travel_create_tripId").val();
//     travelCreateInfo.trainTypeId = $("#travel_create_trainTypeId").val();
//     travelCreateInfo.startingStation = $("#travel_create_startingStation").val();
//     travelCreateInfo.stations = $("#travel_create_stations").val();
//     travelCreateInfo.terminalStation = $("#travel_create_terminalStation").val();
//
//     travelCreateInfo.startingTime = "1970-01-01T" +  $("#travel_create_startingTime").val() +":00Z";
//     travelCreateInfo.endTime = "1970-01-01T" +  $("#travel_create_endTime").val() +":00Z";
//
//     var travelCreateData = JSON.stringify(travelCreateInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/create",
//         contentType: "application/json",
//         dataType: "text",
//         data: travelCreateData,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(result);
//         }
//     });
// });

//------For Trip retrieve------------

$("#travel_queryAll_button").click(function(){
    $.ajax({
        type: "get",
        url: "/travel/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["startingStationId"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startingTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
            //$("#travel_result").html(JSON.stringify(result));
        }
    });
});

$("#travel2_queryAll_button").click(function(){
    $.ajax({
        type: "get",
        url: "/travel2/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel2_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel2_list_table_result").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["startingStationId"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startingTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
            //$("#travel_result").html(JSON.stringify(result));
        }
    });
});

//------For Trip update------------
$("#travel_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel_update_tripId").val();
    travelInfo.trainTypeId = $("#travel_update_trainTypeId").val();
    travelInfo.startingStationId =  $("#travel_update_startingStationId").val();
    travelInfo.stationsId = $("#travel_update_stationsId").val();
    travelInfo.terminalStationId = $("#travel_update_terminalStationId").val();
    travelInfo.startingTime = convertStringToTime($("#travel_update_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#travel_update_endTime").val());
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel_result").html(JSON.stringify(result));
        },
    });
});

$("#travel2_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel2_update_tripId").val();
    travelInfo.trainTypeId = $("#travel2_update_trainTypeId").val();
    travelInfo.startingStationId =  $("#travel2_update_startingStationId").val();
    travelInfo.stationsId = $("#travel2_update_stationsId").val();
    travelInfo.terminalStationId = $("#travel2_update_terminalStationId").val();
    travelInfo.startingTime = convertStringToTime($("#travel2_update_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#travel2_update_endTime").val());
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel2/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel2_result").html(JSON.stringify(result));
        },
    });
});

//------For Trip delete------------
// $("#travel_delete_button").click(function(){
//     var travelInfo = new Object();
//     travelInfo.tripId = $("#travel_delete_tripId").val();
//     var data = JSON.stringify(travelInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(JSON.stringify(result));
//         }
//     });
// });

/********************************************************************/
/*******************Function For Travel Query************************/
$("#travel_booking_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_booking_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_booking_terminalPlace").val();
    travelQueryInfo.departureTime= $("#travel_booking_date").val();
    var travelQueryData = JSON.stringify(travelQueryInfo);
    var train_type = $("#search_select_train_type").val();
    var i = 0;
    $("#tickets_booking_list_table").find("tbody").html("");

    if(train_type == 0){
        $.ajax({
            type: "post",
            url: "/travel/query",
            contentType: "application/json",
            dataType: "json",
            data:travelQueryData,
            xhrFields: {
                withCredentials: true
            },
            success: function(result){
                if(result[0] != null){
                    var obj = result;
                    for(var j=0,l = obj.length;j < l ; i++,j++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[j]["tripId"]["type"] + obj[j]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[j]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[j]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[j]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["endTime"])          + "</td>" +
                            "<td>" + obj[j]["economyClass"]                                + "</td>" +
                            "<td>" + obj[j]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st Class Seat</option>" +
                            "<option value='3'>2st Class Seat</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }
            }
        });
        $.ajax({
            type: "post",
            url: "/travel2/query",
            contentType: "application/json",
            dataType: "json",
            data:travelQueryData,
            xhrFields: {
                withCredentials: true
            },
            success: function(result){
                if(result[0] != null){
                    var obj = result;
                    for(var j=0, l = obj.length ; j < l ;j++, i++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[j]["tripId"]["type"] + obj[j]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[j]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[j]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[j]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["endTime"])          + "</td>" +
                            "<td>" + obj[j]["economyClass"]                                + "</td>" +
                            "<td>" + obj[j]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st Class Seat</option>" +
                            "<option value='3'>2st Class Seat</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }
            }
        });

    }else if(train_type == 1){
        $.ajax({
            type: "post",
            url: "/travel/query",
            contentType: "application/json",
            dataType: "json",
            data:travelQueryData,
            xhrFields: {
                withCredentials: true
            },
            success: function(result){
                if(result[0] != null){
                    var obj = result;
                    $("#tickets_booking_list_table").find("tbody").html("");
                    for(var i = 0,l = obj.length ; i < l ; i++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[i]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[i]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["endTime"])          + "</td>" +
                            "<td>" + obj[i]["economyClass"]                                + "</td>" +
                            "<td>" + obj[i]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st Class Seat</option>" +
                            "<option value='3'>2st Class Seat</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }

            }
        });

    }else if(train_type == 2){
        $.ajax({
            type: "post",
            url: "/travel2/query",
            contentType: "application/json",
            dataType: "json",
            data:travelQueryData,
            xhrFields: {
                withCredentials: true
            },
            success: function(result){
                if(result[0] != null){
                    var obj = result;
                    $("#tickets_booking_list_table").find("tbody").html("");
                    for(var i = 0,l = obj.length ; i < l ; i++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[i]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[i]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["endTime"])          + "</td>" +
                            "<td>" + obj[i]["economyClass"]                                + "</td>" +
                            "<td>" + obj[i]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st Class Seat</option>" +
                            "<option value='3'>2st Class Seat</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }

            }
        });

    }
});

/********************************************************************/
/*****************Function For Order Service*************************/

$("#refresh_my_order_list_button").click(function(){
    var myOrdersQueryInfo = new Object();
    myOrdersQueryInfo.accountId = $("#user_login_id").html();
    myOrdersQueryInfo.loginToken = $("#user_login_token").html();
    myOrdersQueryInfo.enableStateQuery = false;
    myOrdersQueryInfo.enableTravelDateQuery = false;
    myOrdersQueryInfo.enableBoughtDateQuery = false;
    myOrdersQueryInfo.travelDateStart = null;
    myOrdersQueryInfo.travelDateEnd = null;
    myOrdersQueryInfo.boughtDateStart = null;
    myOrdersQueryInfo.boughtDateEnd = null;
    var myOrdersQueryData = JSON.stringify(myOrdersQueryInfo);
    $.ajax({
        type: "post",
        url: "/order/query",
        contentType: "application/json",
        dataType: "json",
        data:myOrdersQueryData,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#my_orders_result").html("");
            for(var i = 0; i < size;i++){
                var order = result[i];
                alert(JSON.stringify(order));
                $("#my_orders_result").append(
                    "<div class='panel panel-default'>" +
                        "<div class='panel-heading'>" +
                            "<h4 class='panel-title'>" +
                                "<a data-toggle='collapse' href='#collapse" + i + "'>" +
                                    "From:" + order['from'] + "    ----->    To:" + order['to'] +
                                "</a>" +
                            "</h4>" +
                        "</div>" +
                        "<div id='collapse" + i + "' class='panel-collapse collapse in'>" +
                            "<div class='panel-body'>" +
                                "<form role='form' class='form-horizontal'>" +
                                    "<div class='div form-group'>" +
                                        "<label class='col-sm-2 control-label'>Order ID: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + order["id"] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Bought Date: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + convertNumberToDateTimeString(order["boughtDate"]) + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Trip Id: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + order["trainNumber"] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Seat Number: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + order["seatNumber"] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Status: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + convertNumberToOrderStatus(order["status"]) + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Price: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + order["price"] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Name: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + order["contactsName"] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Document Type: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + convertNumberToDocumentType(order["documentType"]) + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>DocumentNumber: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label'>" + order["contactsDocumentNumber"] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='form-group'>" +
                                        "<label class='col-sm-2 control-label'>Operation: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='order_id control-label' style='display:none' >" + order["id"] + "</label>" +
                                            "<button class='order_cancel_btn btn btn-primary ticket_booking_button'>" + "Cancel Order" + "</button>" +
                                            "<button class='order_change_btn btn btn-primary ticket_booking_button'>" + "Change your railway ticket" + "</button>" +
                                        "</div>" +
                                    "</div>" +
                                "</form>" +
                            "</div>" +
                        "</div>" +
                    "</div>"
                );
            }
            addListenerToOrderCancel();
            addListenerToOrderChange();
        }
    });
});

function addListenerToOrderCancel(){
    var ticketCancelButtonSet = $(".order_cancel_btn");
    for(var i = 0;i < ticketCancelButtonSet.length;i++){
        ticketCancelButtonSet[i].onclick = function(){
            var orderId = $(this).parents("div").find(".order_id").text();
            alert("Order ID:" + orderId);
            //document.getElementById("order_cancel_panel").style.display = "block";
        }
    }
}

function addListenerToOrderChange(){
    var ticketChangeButtonSet = $(".order_change_btn");
    for(var i = 0;i < ticketChangeButtonSet.length;i++){
        ticketChangeButtonSet[i].onclick = function(){
            var orderId = $(this).parents("div").find(".order_id").text();
            alert("Order ID:" + orderId);
            //document.getElementById("order_change_panel").style.display = "block";
        }
    }
}

$("#order_cancel_panel_cancel").click(function(){
    $("#order_cancel_panel").css('display','none');

});

$("#order_cancel_panel_confirm").click(function(){
    alert("You click order_cancel_panel_confirm");
});

function addListenerToBookingTable(){
    var ticketBookingButtonSet = $(".ticket_booking_button");
    for(var i = 0;i < ticketBookingButtonSet.length;i++){
        ticketBookingButtonSet[i].onclick = function(){
            var tripId = $(this).parents("tr").find(".booking_tripId").text();
            var from = $(this).parents("tr").find(".booking_from").text();
            var to = $(this).parents("tr").find(".booking_to").text();
            var date = $("#travel_booking_date").val();
            var loginToken = $("#user_login_token").html();
            var accountId = $("#user_login_id").html();
            var seatType = $(this).parents("tr").find(".booking_seat_class").val();
            var contactsId = "";
            var radios = $(".booking_contacts_select");
            for (var j = 0; j < radios.length; j++) {
                if (radios[j].checked) {
                    contactsId = $(".booking_contacts_contactsId").eq(j).text();
                }
            }
            var orderTicketInfo = new Object();
            orderTicketInfo.contactsId = contactsId;
            orderTicketInfo.tripId = tripId;
            orderTicketInfo.seatType = seatType;
            orderTicketInfo.loginToken = loginToken;
            orderTicketInfo.accountId = accountId;
            orderTicketInfo.date = date;
            orderTicketInfo.from = from;
            orderTicketInfo.to = to;
            var orderTicketsData = JSON.stringify(orderTicketInfo);
            var tripType = tripId.charAt(0);
            if(tripType == 'G' || tripType == 'D'){
                path = "/preserve";
            }else{
                path = "/preserveOther";
            }
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                data: orderTicketsData,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    alert(result["message"]);
                    $("#payment_panel_heading").css('display','block');
                    $("#payment_panel_body").css('display','block');
                    $(".booking").css('display','none');


                    $("#payment_table").find("tbody").html("");
                    $("#payment_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + result[i]["orderNumber"] + "</td>" +
                        "<td>" + result[i]["tripId"] + "</td>" +
                        "<td>" + result[i]["trainTypeId"] + "</td>" +
                        "<td>" + result[i]["startingPlace"] + "</td>" +
                        "<td>" + result[i]["endPlace"] + "</td>" +
                        "<td>" + result[i]["startingTime"] + "</td>" +
                        "<td>" + result[i]["endTime"] + "</td>" +
                        "<td>" + result[i]["seatClass"] + "</td>" +
                        "<td>" + result[i]["seatNumber"] + "</td>" +
                        "<td>" + result[i]["price"] + "</td>" +
                        "<td>" + "<button class='btn btn-primary ticket_payment_button'>" + "Pay" + "</button>"  + "</td>" +
                        "</tr>"
                    );
                    addListenerToPaymentTable();
                }
            })
        }
    }
}

function addListenerToPaymentTable(){
    var ticketPaymentButtonSet = $(".ticket_payment_button");
    for(var i = 0;i < ticketPaymentButtonSet.length;i++){
        ticketPaymentButtonSet[i].onclick = function(){
            var tripId = $(this).parents("tr").find(".booking_tripId").text();
            var loginToken = $("#user_login_token").html();
            var accountId = $("#user_login_id").html();
            var contactsId = "";
            var radios = $(".booking_contacts_select");
            for (var j = 0; j < radios.length; j++) {
                if (radios[j].checked) {
                    contactsId = $(".booking_contacts_contactsId").eq(j).text();
                }
            }
            var orderTicketInfo = new Object();
            orderTicketInfo.contactsId = contactsId;
            orderTicketInfo.tripId = tripId;
            orderTicketInfo.seatType = seatType;
            orderTicketInfo.loginToken = loginToken;
            orderTicketInfo.accountId = accountId;
            orderTicketInfo.date = date;
            orderTicketInfo.from = from;
            orderTicketInfo.to = to;
            var orderTicketsData = JSON.stringify(orderTicketInfo);
            $.ajax({
                type: "post",
                url: "/inside_payment/pay",
                contentType: "application/json",
                dataType: "json",
                data: orderTicketsData,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {

                }
            })
        }
    }
}

/********************************************************************/
/********************Function For Price Service**********************/

$("#price_queryAll_button").click(function() {
    $.ajax({
        type: "get",
        url: "/price/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_price_list_table").find("tbody").html("");
            $("#price_result_talbe").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_price_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["placeA"] + "</td>" +
                    "<td>" + result[i]["placeB"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"] + "</td>" +
                    "<td>" + result[i]["seatClass"] + "</td>" +
                    "<td>" + result[i]["price"] + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

$("#price_update_button").click(function(){
    var priceUpdateInfo = new Object();
    priceUpdateInfo.placeA = $("#price_update_startingPlace").val();
    priceUpdateInfo.placeB = $("#price_update_endPlace").val();
    priceUpdateInfo.distance = $("#price_update_distance").val();
    var data = JSON.stringify(priceUpdateInfo);
   $.ajax({
       type: "post",
       url: "/price/update",
       contentType: "application/json",
       data:data,
       // dataType: "json",
       xhrFields: {
           withCredentials: true
       },
       success: function (result) {
           // $("#price_result").html(result);
       }
   });
});

/********************************************************************/
/*****************Function For Basic Information*********************/

$("#basic_information_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#basic_information_tripId").val();
    travelInfo.trainTypeId = $("#basic_information_trainTypeId").val();
    travelInfo.startingStation =  $("#basic_information_startingStation").val();
    travelInfo.stations = $("#basic_information_stations").val();
    travelInfo.terminalStation = $("#basic_information_terminalStation").val();
    travelInfo.startingTime = convertStringToTime($("#basic_information_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#basic_information_endTime").val());
    var basicInfo = new Object();
    basicInfo.trip = travelInfo;
    basicInfo.startingPlace = $("#basic_information_startingPlace").val();
    basicInfo.endPlace = $("#basic_information_endPlace").val();
    basicInfo.departureTime = $("#basic_information_departureTime").val();
    var data = JSON.stringify(basicInfo);
    $.ajax({
        type: "post",
        url: "/basic/queryForTravel",
        contentType: "application/json",
        data:data,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#query_basic_information_list_table").find("tbody").html("");
            $("#query_basic_information_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + result["status"] + "</td>" +
                "<td>" + result["percent"] + "</td>" +
                "<td>" + result["trainType"]["id"] + "</td>" +
                "<td>" + result["trainType"]["economyClass"] + "</td>" +
                "<td>" + result["trainType"]["confortClass"] + "</td>" +
                "</tr>"
            );
        }
    });
});

/********************************************************************/
/*****************Function For Ticket Information********************/

$("#ticketinfo_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#ticketinfo_tripId").val();
    travelInfo.trainTypeId = $("#ticketinfo_trainTypeId").val();
    travelInfo.startingStation =  $("#ticketinfo_startingStation").val();
    travelInfo.stations = $("#ticketinfo_stations").val();
    travelInfo.terminalStation = $("#ticketinfo_terminalStation").val();
    travelInfo.startingTime = convertStringToTime($("#ticketinfo_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#ticketinfo_endTime").val());
    var ticketInfo = new Object();
    ticketInfo.trip = travelInfo;
    ticketInfo.startingPlace = $("#ticketinfo_startingPlace").val();
    ticketInfo.endPlace = $("#ticketinfo_endPlace").val();
    ticketInfo.departureTime = $("#ticketinfo_departureTime").val();
    var data = JSON.stringify(ticketInfo);
    $.ajax({
        type: "post",
        url: "/ticketinfo/queryForTravel",
        contentType: "application/json",
        data:data,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#query_ticketinfo_list_table").find("tbody").html("");
            $("#query_ticketinfo_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + result["status"] + "</td>" +
                "<td>" + result["percent"] + "</td>" +
                "<td>" + result["trainType"]["id"] + "</td>" +
                "<td>" + result["trainType"]["economyClass"] + "</td>" +
                "<td>" + result["trainType"]["confortClass"] + "</td>" +
                "</tr>"
            );
        }
    });
});

/********************************************************************/
/********************Function For Notification Service***************/
$("#notification_send_email_button").click(function(){
    var notificationInfo = new Object();
    notificationInfo.email = $("#notification_email").val();
    notificationInfo.orderNumber = $("#notification_orderNumber").val();
    notificationInfo.username = $("#notification_username").val();
    notificationInfo.startingPlace = $("#notification_startingPlace").val();
    notificationInfo.endPlace = $("#notification_endPlace").val();
    notificationInfo.startingTime = $("#notification_startingTime").val();
    notificationInfo.date = $("#notification_date").val();
    notificationInfo.seatClass = $("#notification_seatClass").val();
    notificationInfo.seatNumber = $("#notification_seatNumber").val();
    notificationInfo.price = $("#notification_price").val();
    var data = JSON.stringify(notificationInfo);
    var type = $("#notification_type").val();

    if(type == 0){
        $.ajax({
            type: "post",
            url: "/notification/preserve_success",
            contentType: "application/json",
            data:data,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                $("#notification_result").html(result);
            }
        });
    }else if(type == 1){
        $.ajax({
            type: "post",
            url: "/notification/order_create_success",
            contentType: "application/json",
            data:data,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                $("#notification_result").html(result);
            }
        });
    }else if(type == 2){
        $.ajax({
            type: "post",
            url: "/notification/order_changed_success",
            contentType: "application/json",
            data:data,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                $("#notification_result").html(result);
            }
        });
    }
});

/********************************************************************/
/***************************Some Basic Function**********************/

function convertNumberToDocumentType(code) {
    var str = "";
    if(code == 0){
        str = "null";
    }else if(code == 1){
        str = "ID Card";
    }else if(code == 2){
        str = "Passport";
    }else{
        str = "other";
    }
    return str;
}

function convertNumberToSeatClass(code){
    var str = "";
    if(code == 2){
        str = "First Class Seat";
    }else if(code == 3){
        str = "Second Class Seat";
    }else{
        str = "other";
    }
    return str;
}

function convertNumberToOrderStatus(code){
    var str = "";
    if(code == 0){
        str = "Not Paid";
    }else if(code == 1){
        str = "Paid & Not Collected";
    }else if(code == 2){
        str = "Collected";
    }else if(code == 3){
        str = "Cancel & Rebook";
    }else if(code == 4){
        str = "Cancel";
    }else if(code == 5){
        str = "Refunded";
    }else{
        str = "other";
    }
    return str;
}

function convertNumberToDateTimeString(timeNumber){
    var str = new Date(timeNumber);
    return str.toDateString();
}

function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}

function mergeTwoDate(dateOne,dateTwo) {
    var one = new Date(dateOne);
    var two = new Date(dateTwo);
    var year = one.getFullYear();
    var month = one.getMonth();
    var day = one.getDay();
    var hour = two.getHours();
    var minute = two.getMinutes();
    var datetime = year + ":" + month + ":" + day + "  " + hour + ":" + minute;
    return datetime;
}

function convertStringToTime(string){
    var date = new Date();
    var s = string.toString();
    var index = s.indexOf(':');
    var hour = s.substring(0,index).valueOf();
    var minute = s.substring(index+1,s.length).valueOf();
    date.setHours(hour);
    date.setMinutes(minute);
    return date;
}


function convertNumberToOrderStatus(code){
    var str = "";
    if(code == 0){
        str = "Not Paid";
    }else if(code == 1){
        str = "Paid & Not Collected";
    }else if(code == 2){
        str = "Collected";
    }else if(code == 3){
        str = "Cancel & Rebook";
    }else if(code == 4){
        str = "Cancel";
    }else if(code == 5){
        str = "Refunded";
    }else{
        str = "other";
    }
    return str;
}

