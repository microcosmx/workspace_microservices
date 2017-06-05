/*---------------------Add By Ji Chao for ajax--------------------*/
//----For Login------

document.getElementById("login_button").onclick = function post_login(){
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
}

//------For Logout-------

document.getElementById("logout_button").onclick = function post_logout(){
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
            }
        }
    });
}

//------For Register------
document.getElementById("register_button").onclick = function post_register(){
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
}

//------For contacts----------
//------For add contacts------
document.getElementById("add_contacts_button").onclick = function post_add_contacts(){
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
}


//For query contacts
$("#refresh_contacts_button").click(function refresh_contacts(){
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
            $("#contacts_list_table").find("tbody").html("");
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#contacts_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i                                                    + "</td>" +
                    "<td>" + obj[i]["name"]                                       + "</td>" +
                    "<td>" + obj[i]["documentType"]                               + "</td>" +
                    "<td>" + obj[i]["documentNumber"]                             + "</td>" +
                    "<td>" + obj[i]["phoneNumber"]                                + "</td>" +
                    "<td>" +  "<button class=\"btn btn-primary\">Delete</button>" + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

//Reserve Tickets
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
                    "<td>" + obj[i]["name"]                                       + "</td>" +
                    "<td>" + obj[i]["documentType"]                               + "</td>" +
                    "<td>" + obj[i]["documentNumber"]                             + "</td>" +
                    "<td>" + obj[i]["phoneNumber"]                                + "</td>" +
                    "<td>" +  "<label><input class='booking_contacts_select' name='booking_contacts' type='radio' />" + "Select" + "</label>" + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

$("#travel_booking_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_booking_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_booking_terminalPlace").val();
    travelQueryInfo.departureTime= $("#travel_booking_datee").val();
    var travelQueryData = JSON.stringify(travelQueryInfo);
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
            var obj = result;
            $("#tickets_booking_list_table").find("tbody").html("");
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#tickets_booking_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                    "<td>" + obj[i]["startingStation"]                             + "</td>" +
                    "<td>" + obj[i]["terminalStation"]                             + "</td>" +
                    "<td>" + obj[i]["startingTime"]                                + "</td>" +
                    "<td>" + obj[i]["endTime"]                                     + "</td>" +
                    "<td>" + obj[i]["economyClass"]                                + "</td>" +
                    "<td>" + obj[i]["confortClass"]                                + "</td>" +
                    "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                    "</tr>"
                );
            }
            addListenerToBookingTable();
        }
    });
});

function addListenerToBookingTable(){
    alert("Enter Add Listener");
    var ticketBookingButtonSet = $(".ticket_booking_button");
    alert("Button Set Length:" + ticketBookingButtonSet.length);
    for(var i = 0;i < ticketBookingButtonSet.length;i++){
        ticketBookingButtonSet[i].index = i;
        alert("add " + i);
        ticketBookingButtonSet[i].onclick = function(){
            var table = $("#contacts_booking_list_table");
            var rows = table.find("tr");
            var col = rows[this.index + 1].children("td");
            //获取值
            alert(col[1].innerHTML);
        }
    }
}





//------For Station------------
//------For Station create------------
document.getElementById("station_create_button").onclick = function post_station_create(){
    var stationInfo = new Object();
    stationInfo.name = $("#station_create_name").val();
    var data = JSON.stringify(stationInfo);
    $.ajax({
        type: "post",
        url: "/station/create",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#station_result").html(JSON.stringify(result));
        }
    });
}

//------For Station exist------------
document.getElementById("station_exist_button").onclick = function post_station_exist(){
    var stationInfo = new Object();
    stationInfo.name = $("#station_exist_name").val();
    var data = JSON.stringify(stationInfo);
    $.ajax({
        type: "post",
        url: "/station/exist",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#station_result").html(JSON.stringify(result));
        }
    });
}

//------For Station delete------------
document.getElementById("station_delete_button").onclick = function post_station_delete(){
    var stationInfo = new Object();
    stationInfo.name = $("#station_delete_name").val();
    var data = JSON.stringify(stationInfo);
    $.ajax({
        type: "post",
        url: "/station/delete",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#station_result").html(JSON.stringify(result));
        }
    });
}


//------For Train------------
//------For Train create------------
document.getElementById("train_create_button").onclick = function post_train_create(){
    var trainInfo = new Object();
    trainInfo.id = $("#train_create_id").val();
    trainInfo.economyClass = $("#train_create_economyClass").val();
    trainInfo.confortClass = $("#train_create_confortClass").val();
    var data = JSON.stringify(trainInfo);
    $.ajax({
        type: "post",
        url: "/train/create",
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

//------For Train update------------
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

//------For Train query------------
document.getElementById("train_query_button").onclick = function post_train_query(){
    var trainInfo = new Object();
    trainInfo.id = $("#train_query_id").val();
    var data = JSON.stringify(trainInfo);
    $.ajax({
        type: "post",
        url: "/train/retrieve",
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

//------For Train delete------------
document.getElementById("train_delete_button").onclick = function post_train_delete(){
    var trainInfo = new Object();
    trainInfo.id = $("#train_delete_id").val();
    var data = JSON.stringify(trainInfo);
    $.ajax({
        type: "post",
        url: "/train/delete",
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
//------For Config------------
//------For Config create------------
document.getElementById("config_create_button").onclick = function post_config_create(){
    var configInfo = new Object();
    configInfo.name = $("#config_create_name").val();
    configInfo.value = $("#config_create_value").val();
    configInfo.description = $("#config_create_description").val();
    var data = JSON.stringify(configInfo);
    $.ajax({
        type: "post",
        url: "/config/create",
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

//------For Config update------------
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
    var configInfo = new Object();
    configInfo.name = $("#config_query_name").val();
    var data = JSON.stringify(configInfo);
    $.ajax({
        type: "post",
        url: "/config/query",
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

//------For Config delete------------
document.getElementById("config_delete_button").onclick = function post_config_delete(){
    var configInfo = new Object();
    configInfo.name = $("#config_delete_name").val();
    var data = JSON.stringify(configInfo);
    $.ajax({
        type: "post",
        url: "/config/delete",
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


//------For Travel------------
//------For Trip create------------

$("#travel_create_button").click(function(){
    var travelCreateInfo = new Object();
    travelCreateInfo.tripId = $("#travel_create_tripId").val();
    travelCreateInfo.trainTypeId = $("#travel_create_trainTypeId").val();
    travelCreateInfo.startingStation = $("#travel_create_startingStation").val();
    travelCreateInfo.stations = $("#travel_create_stations").val();
    travelCreateInfo.terminalStation = $("#travel_create_terminalStation").val();

    travelCreateInfo.startingTime = "1970-01-01T" +  $("#travel_create_startingTime").val() +":00Z";
    travelCreateInfo.endTime = "1970-01-01T" +  $("#travel_create_endTime").val() +":00Z";

    var travelCreateData = JSON.stringify(travelCreateInfo);
    $.ajax({
        type: "post",
        url: "/travel/create",
        contentType: "application/json",
        dataType: "text",
        data: travelCreateData,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel_result").html(result);
        }
    });
});

//------For Trip retrieve------------

$("#travel_retrieve_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel_delete_tripId").val();
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel/retrieve",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel_result").html(JSON.stringify(result));
        }
    });
});

//------For Trip update------------

$("#travel_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel_update_tripId").val();
    travelInfo.trainTypeId = $("#travel_update_trainTypeId").val;
    travelInfo.startingStation =  $("#travel_update_startingStation").val;
    travelInfo.stations = $("#travel_update_stations").val ;
    travelInfo.terminalStation = $("#travel_update_terminalStation").val;
    travelInfo.startingTime = $("#travel_update_startingTime").val;
    travelInfo.endTime = $("#travel_update_endTime").val;
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

//------For Trip delete------------

$("#travel_delete_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel_delete_tripId").val();
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel/delete",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel_result").html(JSON.stringify(result));
        }
    });
});

//------For Travel query------------

$("#travel_query_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_query_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_query_terminalPlace").val();
    travelQueryInfo.departureTime= $("#travel_query_date").val();
    var travelQueryData = JSON.stringify(travelQueryInfo);
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
            var obj = result;
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#query_tickets_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                    "<td>" + obj[i]["startingStation"]                             + "</td>" +
                    "<td>" + obj[i]["terminalStation"]                             + "</td>" +
                    "<td>" + obj[i]["startingTime"]                                + "</td>" +
                    "<td>" + obj[i]["endTime"]                                     + "</td>" +
                    "<td>" + obj[i]["economyClass"]                                + "</td>" +
                    "<td>" + obj[i]["confortClass"]                                + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

