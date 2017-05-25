/*---------------------Add By Ji Chao for ajax--------------------*/
//// Creates a XMLHttpRequest object.
var req = new XMLHttpRequest();

function getXmlHttpRequest(){
    var xmlHttpRequest= "";
    if(window.XMLHttpRequest){
        xmlHttpRequest = new XMLHttpRequest();
    } else{ // IE
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlHttpRequest;
}

//----For Login------

document.getElementById("login_button").onclick = function post_login(){

    req = getXmlHttpRequest();

    var loginInfo = new Object();
    loginInfo.phoneNum = document.getElementById("login_phoneNum").value;
    loginInfo.password = document.getElementById("login_password").value;
    loginInfo.verificationCode = document.getElementById("login_verification_code").value;
    var data = JSON.stringify(loginInfo);

    var url = "/login";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_login_result;
    req.send(data);
}

function handle_login_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        var obj = JSON.parse(resultstr);
        if(obj["account"] != null){
            document.getElementById("user_login_id").innerHTML = obj["account"].id;
            document.getElementById("user_login_token").innerHTML = obj["token"];
        }
        document.getElementById("login_result_status").innerHTML = obj["status"];
        document.getElementById("login_result_msg").innerHTML = obj["message"];
        document.getElementById("login_result_account").innerHTML = JSON.stringify(obj["account"]);
        document.getElementById("login_result_token").innerHTML = obj["token"];
    }
}

//------For Logout-------

document.getElementById("logout_button").onclick = function post_logout(){

    req = getXmlHttpRequest();

    var logoutInfo = new Object();
    logoutInfo.id = document.getElementById("user_login_id").innerHTML;
    logoutInfo.token = document.getElementById("user_login_token").innerHTML;
    var data = JSON.stringify(logoutInfo);

    var url = "/logout";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_logout_result;
    req.send(data);
}

function handle_logout_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        var obj = JSON.parse(resultstr);
        if(obj["status"] == "true"){
            document.getElementById("user_login_id").innerHTML = "Not Login";
            document.getElementById("user_login_token").innerHTML = "Please Login";
        }else{
            alert(JSON.stringify(obj));
        }
    }
}

//------For Register------
document.getElementById("register_button").onclick = function post_register(){

    req = getXmlHttpRequest();

    var registerInfo = new Object();
    registerInfo.password = document.getElementById("register_password").value;
    registerInfo.gender = document.getElementById("register_gender").value;
    registerInfo.name = document.getElementById("register_name").value;
    registerInfo.documentType = document.getElementById("register_documentType").value;
    registerInfo.documentNum = document.getElementById("register_documentNum").value;
    registerInfo.phoneNum = document.getElementById("register_phoneNum").value;
    registerInfo.verificationCode = document.getElementById("register_verificationCode").value;
    var data = JSON.stringify(registerInfo);

    var url = "/register";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_register_result;
    req.send(data);
}

function handle_register_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        var obj = JSON.parse(resultstr);
        document.getElementById("register_result_status").innerHTML = obj["status"];
        document.getElementById("register_result_msg").innerHTML = obj["message"];
        document.getElementById("register_result_account").innerHTML = JSON.stringify(obj["account"]);
    }
}

//------For contacts----------
//------For add contacts------
document.getElementById("add_contacts_button").onclick = function post_add_contacts(){

    req = getXmlHttpRequest();

    var addContactsInfo = new Object();
    addContactsInfo.name = document.getElementById("add_contacts_name").value;
    addContactsInfo.documentType = document.getElementById("add_contacts_documentType").value;
    addContactsInfo.documentNumber = document.getElementById("add_contacts_documentNum").value;
    addContactsInfo.phoneNumber = document.getElementById("add_contacts_phoneNum").value;
    addContactsInfo.accountId = document.getElementById("user_login_id").innerHTML;
    addContactsInfo.loginToken = document.getElementById("user_login_token").innerHTML;
    var data = JSON.stringify(addContactsInfo);

    var url = "http://10.141.212.21/createNewContacts";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_add_contacts_result;
    req.send(data);
}

function handle_add_contacts_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        var obj = JSON.parse(resultstr);
        document.getElementById("add_contacts_result_status").innerHTML = obj["status"];
        document.getElementById("add_contacts_result_msg").innerHTML = obj["message"];
        document.getElementById("add_contacts_result_contacts").innerHTML = JSON.stringify(obj["contacts"]);
    }
}
//For query contacts
$("#refresh_contacts_button").click(function refresh_contacts(){

    req = getXmlHttpRequest();
    var queryContactsInfo = new Object();
    queryContactsInfo.accountId = document.getElementById("user_login_id").innerHTML;
    queryContactsInfo.loginToken = document.getElementById("user_login_token").innerHTML;
    var data = JSON.stringify(queryContactsInfo);
    var url = "http://10.141.212.21/findContacts";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_query_contacts_result;
    req.send(data);

    // $("#contacts_list_table").find("tbody").append(
    //     "<tr>" +
    //     "<td>" + "5" + "</td>" +
    //     "<td>" + "jichao" + "</td>" +
    //     "<td>" + "身份证" + "</td>" +
    //     "<td>" + "21123123" + "</td>" +
    //     "<td>" + "2122131123123" + "</td>" +
    //     "<td>" + "删除" + "</td>" +
    //     "</tr>"
    // );

});

function handle_query_contacts_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        $("#contacts_list_table").find("tbody").html("");
        var obj = JSON.parse(resultstr);

        for(var i = 0,l = obj.length ; i < l ; i++){
            $("#contacts_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + i + "</td>" +
                "<td>" + obj[i]["name"] + "</td>" +
                "<td>" + obj[i]["documentType"] + "</td>" +
                "<td>" + obj[i]["documentNumber"] + "</td>" +
                "<td>" + obj[i]["phoneNumber"] + "</td>" +
                "<td>" + "此区域未完成" + "</td>" +
                "</tr>"
            );
        }
    }
}

$("#remove_contacts_button").click(function refresh_contacts(){
    $("#contacts_list_table").find("tbody").html("");
});


//------For Station------------
//------For Station create------------
document.getElementById("station_create_button").onclick = function post_station_create(){
    req = getXmlHttpRequest();
    var StationInfo = new Object();
    StationInfo.name = document.getElementById("station_create_name").value;
    var data = JSON.stringify(StationInfo);
    var url = "http://10.141.212.21/station/create";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_station_result;
    req.send(data);
}

//------For Station exist------------
document.getElementById("station_exist_button").onclick = function post_station_exist(){
    req = getXmlHttpRequest();
    var StationInfo = new Object();
    StationInfo.name = document.getElementById("station_exist_name").value;
    var data = JSON.stringify(StationInfo);
    var url = "http://10.141.212.21/station/exist";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_station_result;
    req.send(data);
}

//------For Station delete------------
document.getElementById("station_delete_button").onclick = function post_station_delete(){
    req = getXmlHttpRequest();
    var StationInfo = new Object();
    StationInfo.name = document.getElementById("station_delete_name").value;
    var data = JSON.stringify(StationInfo);
    var url = "http://10.141.212.21/station/delete";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_station_result;
    req.send(data);
}

//handle station result
function handle_station_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        document.getElementById("station_result").innerHTML = resultstr;
    }
}


//------For Train------------
//------For Train create------------
document.getElementById("train_create_button").onclick = function post_train_create(){
    req = getXmlHttpRequest();
    var TrainInfo = new Object();
    TrainInfo.id = document.getElementById("train_create_id").value;
    TrainInfo.economyClass = document.getElementById("train_create_economyClass").value;
    TrainInfo.confortClass = document.getElementById("train_create_confortClass").value;
    var data = JSON.stringify(TrainInfo);
    var url = "http://10.141.212.21/train/create";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_train_result;
    req.send(data);
}

//------For Train update------------
document.getElementById("train_update_button").onclick = function post_train_update(){
    req = getXmlHttpRequest();
    var TrainInfo = new Object();
    TrainInfo.id = document.getElementById("train_update_id").value;
    TrainInfo.economyClass = document.getElementById("train_update_economyClass").value;
    TrainInfo.confortClass = document.getElementById("train_update_confortClass").value;
    var data = JSON.stringify(TrainInfo);
    var url = "http://10.141.212.21/train/update";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_train_result;
    req.send(data);
}

//------For Train query------------
document.getElementById("train_query_button").onclick = function post_train_query(){
    req = getXmlHttpRequest();
    var TrainInfo = new Object();
    TrainInfo.id = document.getElementById("train_query_id").value;
    var data = JSON.stringify(TrainInfo);
    var url = "http://10.141.212.21/train/retrieve";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_train_result;
    req.send(data);
}

//------For Train delete------------
document.getElementById("train_delete_button").onclick = function post_train_delete(){
    req = getXmlHttpRequest();
    var TrainInfo = new Object();
    TrainInfo.id = document.getElementById("train_delete_id").value;
    var data = JSON.stringify(TrainInfo);
    var url = "http://10.141.212.21/train/delete";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_train_result;
    req.send(data);
}

//handle train result
function handle_train_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        document.getElementById("train_result").innerHTML = resultstr;
    }
}


//------For Config------------
//------For Config create------------
document.getElementById("config_create_button").onclick = function post_config_create(){
    req = getXmlHttpRequest();
    var ConfigInfo = new Object();
    ConfigInfo.name = document.getElementById("config_create_name").value;
    ConfigInfo.value = document.getElementById("config_create_value").value;
    ConfigInfo.description = document.getElementById("config_create_description").value;
    var data = JSON.stringify(ConfigInfo);
    var url = "http://10.141.212.21/config/create";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_config_result;
    req.send(data);
}

//------For Config update------------
document.getElementById("config_update_button").onclick = function post_config_update(){
    req = getXmlHttpRequest();
    var ConfigInfo = new Object();
    ConfigInfo.name = document.getElementById("config_update_name").value;
    ConfigInfo.value = document.getElementById("config_update_value").value;
    ConfigInfo.description = document.getElementById("config_update_description").value;
    var data = JSON.stringify(ConfigInfo);
    var url = "http://10.141.212.21/config/update";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_config_result;
    req.send(data);
}

//------For Query query------------
document.getElementById("config_query_button").onclick = function post_config_query(){
    req = getXmlHttpRequest();
    var ConfigInfo = new Object();
    ConfigInfo.name = document.getElementById("config_query_name").value;
    var data = JSON.stringify(ConfigInfo);
    var url = "http://10.141.212.21/config/query";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_config_result;
    req.send(data);
}

//------For Config delete------------
document.getElementById("config_delete_button").onclick = function post_config_delete(){
    req = getXmlHttpRequest();
    var ConfigInfo = new Object();
    ConfigInfo.name = document.getElementById("config_delete_name").value;
    var data = JSON.stringify(ConfigInfo);
    var url = "http://10.141.212.21/config/delete";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_config_result;
    req.send(data);
}

//handle config result
function handle_config_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        document.getElementById("config_result").innerHTML = resultstr;
    }
}


//------For Travel------------
//------For Trip create------------
//------For Trip retrieve------------
//------For Trip update------------
//------For Trip delete------------
//------For Travel query------------
// document.getElementById("travel_query_button").onclick = function post_travel_query(){
//     req = getXmlHttpRequest();
//     var TravelInfo = new Object();
//     TravelInfo.startingPlace = document.getElementById("travel_query_startingPlace").value;
//     TravelInfo.endPlace = document.getElementById("travel_query_terminalPlace").value;
//     TravelInfo.departureTime = document.getElementById("travel_query_date").value;
//     var data = JSON.stringify(TravelInfo);
//     var url = "http://10.141.212.21/travel/query";
//
//     req.open("post",url,true);
//     req.withCredentials = true;
//     req.setRequestHeader("Content-Type", "application/json");
//     req.onreadystatechange = handle_travel_query_result;
//     req.send(data);
// }
//
// //handle config result
// function handle_travel_query_result(){
//     if(req.readyState == 4){
//         var resultstr = req.responseText;
//         alert(resultstr);
//     }
// }

$("#trave_query_button").click(function(){

    $.ajax({
        type: "post",
        url: "http://10.141.212.21/travel/query",
        contentType: "application/json",
        dataType: "json",
        data:{startingPlace:$("#travel_query_startingPlace").val(),
            endPlace:$("#travel_query_terminalPlace").val(),
            departureTime:$("#travel_query_date").val()},
        success: function(data, textStatus){
            alert(data);
            // $(".ajax.ajaxResult").html("");
            // $("item",data).each(function(i, domEle){
            //     $(".ajax.ajaxResult").append("<li>"+$(domEle).children("title").text()+"</li>");
            // });
        },
        async: true,
        // complete: function(XMLHttpRequest, textStatus){
        // },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

    }
);


