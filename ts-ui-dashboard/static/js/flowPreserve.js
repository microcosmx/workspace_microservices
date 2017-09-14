/***********************************************************/
/******************Flow For Preserve Ticket*****************/
/**Before ***/
function setTodayDatePreserve(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd < 10){
        dd='0' + dd
    }
    if(mm < 10){
        mm = '0' + mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("travel_booking_date").setAttribute("min", today);
}


/**
 *  Flow Preserve - Step 1 - User Login
 **/
$("#flow_preserve_login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.email = $("#flow_preserve_login_email").val();
    if(loginInfo.email == null || loginInfo.email == ""){
        alert("Email Can Not Be Empty.");
        return;
    }
    if(checkEmailFormat(loginInfo.email) == false){
        alert("Email Format Wrong.");
        return;
    }
    loginInfo.password = $("#flow_preserve_login_password").val();
    if(loginInfo.password == null || loginInfo.password == ""){
        alert("Password Can Not Be Empty.");
        return;
    }
    loginInfo.verificationCode = $("#flow_preserve_login_verification_code").val();
    if(loginInfo.verificationCode == null || loginInfo.verificationCode == ""){
        alert("Verification Code Can Not Be Empty.");
        return;
    }
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
                document.cookie = "loginId=" + obj["account"].id;
                document.cookie = "loginToken=" + obj["token"];
                $("#flow_preserve_login_status").text(obj["status"]);
                $("#flow_preserve_login_msg").text(obj["message"]);
                $("#user_login_id").text(obj["account"].id);
                location.hash="anchor_flow_preserve_select_trip";
            }else{
                // setCookie("loginId", "", -1);
                // setCookie("loginToken", "", -1);
                //alert(obj["message"]);
                $("#flow_preserve_login_msg").text(obj["message"]);
            }
        }
    });
});

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function checkEmailFormat(email){
    var emailFormat = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!emailFormat.test(email)){
        return false;
    }else{
        return true;
    }
}

/**
 *  Flow Preserve - Step 2 - Query Trips
 **/

$("#travel_booking_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_booking_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_booking_terminalPlace").val();
    travelQueryInfo.departureTime = $("#travel_booking_date").val();
    if(travelQueryInfo.departureTime == null || checkDateFormat(travelQueryInfo.departureTime) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var travelQueryData = JSON.stringify(travelQueryInfo);
    var train_type = $("#search_select_train_type").val();
    var i = 0;
    $("#tickets_booking_list_table").find("tbody").html("");
    if(train_type == 0){
        queryForTravelInfo(travelQueryData,"/travel/query");
        queryForTravelInfo(travelQueryData,"/travel2/query");
    }else if(train_type == 1){
        queryForTravelInfo(travelQueryData,"/travel/query");
    }else if(train_type == 2){
        queryForTravelInfo(travelQueryData,"/travel2/query");
    }
});

function checkDateFormat(date){
    var dateFormat = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
    if(!dateFormat.test(date)){
        return false;
    }else{
        return true;
    }
}

function queryForTravelInfo(data,path) {
    $("#travel_booking_button").attr("disabled",true);
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data: data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result[0] != null) {
                var obj = result;
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#tickets_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] + "</td>" +
                        "<td class='booking_from'>" + obj[i]["startingStation"] + "</td>" +
                        "<td class='booking_to'>" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["economyClass"] + "</td>" +
                        "<td>" + obj[i]["confortClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control booking_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='booking_seat_price_confort noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='booking_seat_price_economy noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>" + "</td>" +
                        "</tr>"
                    );
                }
                addListenerToBookingTable();
            }
        },
        complete: function () {
            $("#travel_booking_button").attr("disabled",false);
        }
    });
}

function addListenerToBookingTable(){
    var ticketBookingButtonSet = $(".ticket_booking_button");
    for(var i = 0;i < ticketBookingButtonSet.length;i++){
        ticketBookingButtonSet[i].onclick = function(){
            var tripId = $(this).parents("tr").find(".booking_tripId").text();
            var from = $(this).parents("tr").find(".booking_from").text();
            var to = $(this).parents("tr").find(".booking_to").text();
            var seatType = $(this).parents("tr").find(".booking_seat_class").val();
            var date = $("#travel_booking_date").val();
            $("#ticket_confirm_from").text(from);
            $("#ticket_confirm_to").text(to);
            $("#ticket_confirm_tripId").text(tripId);
            if(seatType == 2){
                $("#ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_confort").text());
            }else if(seatType == 3){
                $("#ticket_confirm_price").text($(this).parents("tr").find(".booking_seat_price_economy").text());
            }
            $("#ticket_confirm_travel_date").text(date);
            $("#ticket_confirm_seatType").text(seatType);
            if(seatType == 2){
                $("#ticket_confirm_seatType_String").text("confort seat");
            }else if(seatType == 3){
                $("#ticket_confirm_seatType_String").text("economy seat");
            }
            refresh_booking_contacts();
            location.hash="anchor_flow_preserve_select_contacts";
        }
    }
}

function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}


/**
 *  Flow Preserve - Step 3 - Query/Add Contacts
 **/

$("#refresh_booking_contacts_button").click(function(){
    refresh_booking_contacts();
})

function refresh_booking_contacts() {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#refresh_booking_contacts_button").attr("disabled",true);
    $.ajax({
        type: "get",
        url: "/contacts/findContacts",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var obj = result;
            $("#contacts_booking_list_table").find("tbody").html("");
            for (var i = 0, l = obj.length; i < l; i++) {
                $("#contacts_booking_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='booking_contacts_contactsId' style='display:none'>" + obj[i]["id"] + "</td>" +
                    "<td class='booking_contacts_name'>" + obj[i]["name"] + "</td>" +
                    "<td class='booking_contacts_documentType'>" + convertNumberToDocumentType(obj[i]["documentType"]) + "</td>" +
                    "<td class='booking_contacts_documentNumber'>" + obj[i]["documentNumber"] + "</td>" +
                    "<td class='booking_contacts_phoneNumber'>" + obj[i]["phoneNumber"] + "</td>" +
                    "<td>" + "<label><input class='booking_contacts_select' name='booking_contacts' type='radio' />" + "Select" + "</label>" + "</td>" +
                    "</tr>"
                );
            }
            $("#contacts_booking_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + obj.length + "</td>" +
                "<td class='booking_contacts_name'>" + "<input id='booking_new_contacts_name'>" + "</td>" +
                "<td>" +
                "<select id='booking_new_contacts_documentType' class='booking_contacts_documentType all form-control'>" +
                "<option value='1' selected = 'selected'>ID Card</option>" +
                "<option value='2'>Passport</option>" +
                "<option value='3'>Other</option>" +
                "</select>" +
                "</td>" +
                "<td class='booking_contacts_documentNumber'>" + "<input id='booking_new_contacts_documentNum'>" + "</td>" +
                "<td class='booking_contacts_phoneNumber'>" + "<input id='booking_new_contacts_phoneNum'>" + "</td>" +
                "<td>" + "<label><input id='booking_new_contacts_select' class='booking_contacts_select' name='booking_contacts' type='radio' />" + "Select" + "</label>" + "</td>" +
                "</tr>"
            );
        },
        complete: function(){
            $("#refresh_booking_contacts_button").attr("disabled",false);
        }
    });
}

$("#ticket_select_contacts_cancel_btn").click(function(){
    location.hash="anchor_flow_preserve_select_trip";
})

$("#ticket_select_contacts_confirm_btn").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    var contactsId = "";
    var radios = $(".booking_contacts_select");
    var selectContactsStatus = false;
    if(radios[radios.length - 1].checked){
        selectContactsStatus = true;
        preserveCreateNewContacts();
    }else{
        for (var j = 0; j < radios.length - 1; j++) {
            if (radios[j].checked) {
                contactsId = $(".booking_contacts_contactsId").eq(j).text();
                selectContactsStatus = true;
                var contactsName = $(".booking_contacts_name").eq(j).text();
                var documentType = $(".booking_contacts_documentType").eq(j).text();
                var documentNumber = $(".booking_contacts_documentNumber").eq(j).text();
                $("#ticket_confirm_contactsId").text(contactsId);
                $("#ticket_confirm_contactsName").text(contactsName);
                $("#ticket_confirm_documentType").text(documentType);
                $("#ticket_confirm_documentNumber").text(documentNumber);
                break;
            }
        }
    }
    if(selectContactsStatus == false){
        alert("Please select contacts.");
        return;
    }else{
        location.hash="anchor_flow_preserve_confirm";
    }
})

function preserveCreateNewContacts(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#ticket_select_contacts_confirm_btn").attr("disabled",true);
    var addContactsInfo = new Object();
    addContactsInfo.name = $("#booking_new_contacts_name").val();
    addContactsInfo.documentType = $("#booking_new_contacts_documentType").val();
    addContactsInfo.documentNumber = $("#booking_new_contacts_documentNum").val();
    addContactsInfo.phoneNumber = $("#booking_new_contacts_phoneNum").val();
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
            $("#ticket_confirm_contactsId").text(result["contacts"]["id"]);
            $("#ticket_confirm_contactsName").text(result["contacts"]["name"]);
            $("#ticket_confirm_documentType").text(convertNumberToDocumentType(result["contacts"]["documentType"]));
            $("#ticket_confirm_documentNumber").text(result["contacts"]["documentNumber"]);
            refresh_booking_contacts();
        },
        complete: function(){
            $("#ticket_select_contacts_confirm_btn").attr("disabled",false);
        }
    });
}

function convertNumberToDocumentType(code) {
    var str = "";
    if (code == 0) {
        str = "null";
    } else if (code == 1) {
        str = "ID Card";
    } else if (code == 2) {
        str = "Passport";
    } else {
        str = "other";
    }
    return str
}

/**
 * Flow Preserve - Step 4 - Check Your Order Detail Information
 */

$("#ticket_confirm_cancel_btn").click(function () {
    location.hash="anchor_flow_preserve_select_contacts";
})

$("#ticket_confirm_confirm_btn").click(function () {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#ticket_confirm_confirm_btn").attr("disabled",true);
    var orderTicketInfo = new Object();
    orderTicketInfo.contactsId = $("#ticket_confirm_contactsId").text();
    orderTicketInfo.tripId = $("#ticket_confirm_tripId").text();
    orderTicketInfo.seatType = $("#ticket_confirm_seatType").text();
    orderTicketInfo.date = $("#ticket_confirm_travel_date").text();
    orderTicketInfo.from = $("#ticket_confirm_from").text();
    orderTicketInfo.to = $("#ticket_confirm_to").text();
    var orderTicketsData = JSON.stringify(orderTicketInfo);
    var tripType = orderTicketInfo.tripId.charAt(0);
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
            if(result['status'] == true){
                //$("#preserve_pay_panel").css('display','block');
                $("#preserve_pay_orderId").val(result["order"]["id"]);
                $("#preserve_pay_price").val(result["order"]["price"]);
                $("#preserve_pay_userId").val(result["order"]["accountId"]);
                $("#preserve_pay_tripId").val(result["order"]["trainNumber"]);
                location.hash="anchor_flow_preserve_pay";
            }
        },
        complete: function(){
            $("#ticket_confirm_confirm_btn").attr("disabled",false);
        }
    })
})

/**
 * Flow Preserve - Step 5 - Pay For Your Ticket
 */

$("#preserve_pay_later_button").click(function(){
    //$("#preserve_pay_panel").css('display','none');
    location.hash="anchor_flow_preserve_pay";
})

$("#preserve_pay_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#preserve_pay_button").attr("disabled",true);
    var info = new Object();
    info.orderId = $("#preserve_pay_orderId").val();
    info.tripId = $("#preserve_pay_tripId").val();
    var data = JSON.stringify(info);
    $.ajax({
        type: "post",
        url: "/inside_payment/pay",
        contentType: "application/json",
        dataType: "text",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if(result == "true"){
                $("#preserve_collect_order_id").val(info.orderId);
                //alert("Success");
                location.hash="anchor_flow_preserve_collect";
            }else{
                alert("Pay Fail. Reason Not Clear.Please check the order status before you try.");
                //alert("Some thing error");
            }
        },
        complete: function(){
            $("#preserve_pay_button").attr("disabled",false);
        }
    });
    //$("#preserve_pay_panel").css('display','none');
})

/**
 * Flow Preserve - Step 6 - Collect Your Ticket
 */

$("#preserve_collect_button").click(function() {
    $("#preserve_collect_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#preserve_collect_order_id").val();
    var data = JSON.stringify(executeInfo);
    $.ajax({
        type: "post",
        url: "/execute/collected",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#preserve_execute_order_id").val(executeInfo.orderId);
                $("#preserve_collect_order_status").html(obj["message"]);
                location.hash="anchor_flow_preserve_execute";
            }else{
                $("#preserve_collect_order_status").html(obj["message"]);
            }
        },
        complete: function(){
            $("#preserve_collect_button").attr("disabled",false);
        }
    });
});;

/**
 * Flow Preserve - Step 7 - Enter Station
 */

$("#preserve_order_button").click(function() {
    $("#preserve_order_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#preserve_execute_order_id").val();
    var data = JSON.stringify(executeInfo);
    $.ajax({
        type: "post",
        url: "/execute/execute",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#preserve_order_status").html(obj["message"]);
            }else{
                $("#preserve_order_status").html(obj["message"]);
            }
        },
        complete: function(){
            $("#preserve_order_button").attr("disabled",false);
        }
    });
});






