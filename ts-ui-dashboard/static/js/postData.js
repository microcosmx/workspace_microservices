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
/********************Function For Order Service*******************/

$("#refresh_order_button").click(function(){
    var typeCheckBox = $(".order_type");
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
                    "<td>" + obj[i]["id"] + "</td>" +
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
                $("#my_orders_result").append(
                    "<div class='panel panel-default'>" +
                        "<div class='panel-heading'>" +
                            "<h4 class='panel-title'>" +
                                  "<label>" +
                                //"<a data-toggle='collapse' href='#collapse" + i + "'>" +
                                    "From:" + order['from'] + "    ----->    To:" + order['to'] +
                                //"</a>" +
                                  "</label>" +
                            "</h4>" +
                        "</div>" +
                        "<div>" +
                        //"<div id='collapse" + i + "' class='panel-collapse collapse in'>" +
                            "<div class='panel-body'>" +
                                "<form role='form' class='form-horizontal'>" +
                                    "<div class='div form-group'>" +
                                        "<label class='col-sm-2 control-label'>Order ID: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label my_order_list_id'>" + order["id"] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='div form-group'>" +
                                        "<label class='col-sm-2 control-label'>From: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label my_order_list_from'>" + order['from'] + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='div form-group'>" +
                                        "<label class='col-sm-2 control-label'>To: </label>" +
                                        "<div class='col-sm-10'>" +
                                            "<label class='control-label my_order_list_to'>" + order['to'] + "</label>" +
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
                                            "<label class='control-label my_order_list_price'>" + order["price"] + "</label>" +
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
                                            "<button type='button' class='ticket_cancel_btn btn btn-primary'>" + "Cancel Order" + "</button>" +
                                            "<button type='button' class='order_rebook_btn btn btn-primary'>" + "Change your railway ticket" + "</button>" +
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
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
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
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
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
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
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
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
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

function addListenerToOrderCancel(){
    var ticketCancelButtonSet = $(".ticket_cancel_btn");
    for(var i = 0;i < ticketCancelButtonSet.length;i++){
        ticketCancelButtonSet[i].onclick = function(){
            var orderId = $(this).parents("form").find(".my_order_list_id").text();
            $("#ticket_cancel_order_id").text(orderId);
            var orderPrice = $(this).parents("form").find(".my_order_list_price").text();
            $("#cancel_money_refund").text(orderPrice);
            alert("Order Id:" + orderId + " Price:" + orderPrice);
        }
    }
}

function addListenerToOrderChange(){
    var ticketChangeButtonSet = $(".order_rebook_btn");
    for(var i = 0;i < ticketChangeButtonSet.length;i++){
        ticketChangeButtonSet[i].onclick = function(){
            var changeStartingPlace = $(this).parents("form").find(".my_order_list_from").text();
            var changeEndPlace = $(this).parents("to").find(".my_order_list_to").text();
            $("#travel_rebook_startingPlace").text(changeStartingPlace);
            $("#travel_rebook_terminalPlace").text(changeEndPlace);
        }
    }
}

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
            var selectContactsStatus = false;
            for (var j = 0; j < radios.length; j++) {
                if (radios[j].checked) {
                    contactsId = $(".booking_contacts_contactsId").eq(j).text();
                    selectContactsStatus = true;
                    break;
                }
            }
            if(selectContactsStatus == false){
                alert("Please select contacts.");
                return;
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
