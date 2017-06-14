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
                    "<td>" + convertNumberToDocumentType(obj[i]["documentType"]) + "</td>" +
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
document.getElementById("station_query_button").onclick = function post_station_query(){
    $.ajax({
        type: "get",
        url: "/station/query",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#station_result").html(JSON.stringify(result));
        }
    });
}

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


//------For Travel------------
//------For Trip create------------

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
                    "<td>" + result[i]["startingStation"]     + "</td>" +
                    "<td>" + result[i]["stations"]     + "</td>" +
                    "<td>" + result[i]["terminalStation"]     + "</td>" +
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
                    "<td>" + result[i]["startingStation"]     + "</td>" +
                    "<td>" + result[i]["stations"]     + "</td>" +
                    "<td>" + result[i]["terminalStation"]     + "</td>" +
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
    travelInfo.startingStation =  $("#travel_update_startingStation").val();
    travelInfo.stations = $("#travel_update_stations").val();
    travelInfo.terminalStation = $("#travel_update_terminalStation").val();
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
    travelInfo.startingStation =  $("#travel2_update_startingStation").val();
    travelInfo.stations = $("#travel2_update_stations").val();
    travelInfo.terminalStation = $("#travel2_update_terminalStation").val();
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

//------For Travel query------------




$("#travel_booking_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_booking_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_booking_terminalPlace").val();
    travelQueryInfo.departureTime= $("#travel_booking_date").val();
    var travelQueryData = JSON.stringify(travelQueryInfo);
    var train_type = $("#search_select_train_type").val();
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
                $("#tickets_booking_list_table").find("tbody").html("");
                if(result[0] != null){
                    var obj = result;
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



//-------------------For Orders------------------

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
    alert("ready to send:" + myOrdersQueryData);
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
            alert("Do Order Date:" + orderTicketsData);
            $.ajax({
                type: "post",
                url: "/preserve",
                contentType: "application/json",
                dataType: "json",
                data: orderTicketsData,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    alert(result["message"]);
                }
            })
        }
    }
}

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
    }else if(code == 1){
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


//For price service
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


//basic information
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

//Ticket information
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

