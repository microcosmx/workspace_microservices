/**
 *  Flow Preserve - Step 1 - Refresh Your Orders
 **/

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
                    "<a data-toggle='collapse' href='#collapse" + i + "'>" +
                    "From:" + order['from'] + "    ----->    To:" + order['to'] +
                    "</a>" +
                    "</label>" +
                    "</h4>" +
                    "</div>" +
                    "<div>" +
                    "<div id='collapse" + i + "' class='panel-collapse collapse in'>" +
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
                    "<label class='noshow_component my_order_list_status'>" + order["status"] + "</label>" +
                    "<label class='control-label'>" + convertNumberToOrderStatus(order["status"]) + "</label>" +
                    addPayButtonOrNot(order["status"]) +
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
                    "<label class='order_id control-label noshow_component' >" + order["id"] + "</label>" +
                    "<label class='order_id control-label noshow_component my_order_list_accountId' >" + order["accountId"] + "</label>" +
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
            //addPayButtonListener();
        }
    });
});

function addListenerToOrderCancel(){
    var ticketCancelButtonSet = $(".ticket_cancel_btn");
    for(var i = 0;i < ticketCancelButtonSet.length;i++){
        ticketCancelButtonSet[i].onclick = function(){
            var orderStatus = $(this).parents("form").find(".my_order_list_status").text();
            if(orderStatus != 1){
                alert("Order Can Not Be Changed");
                return;
            }
            $("#ticket_cancel_panel").css('display','block');
            var orderId = $(this).parents("form").find(".my_order_list_id").text();
            $("#ticket_cancel_order_id").text(orderId);
            //var orderPrice = $(this).parents("form").find(".my_order_list_price").text();
            var cancelOrderInfo = new Object();
            cancelOrderInfo.orderId = orderId;
            var cancelOrderData = JSON.stringify(cancelOrderInfo);
            alert(cancelOrderData);
            $.ajax({
                type: "post",
                url: "/cancelCalculateRefund",
                contentType: "application/json",
                dataType: "json",
                data:cancelOrderData,
                xhrFields: {
                    withCredentials: true
                },
                success: function (result) {
                    $("#cancel_money_refund").text(result["refund"]);
                },
            });
            //$("#cancel_money_refund").text(orderPrice);
        }
    }
}

function addListenerToOrderChange(){
    var ticketChangeButtonSet = $(".order_rebook_btn");
    for(var i = 0;i < ticketChangeButtonSet.length;i++){
        ticketChangeButtonSet[i].onclick = function(){
            var changeStartingPlaceId = $(this).parents("form").find(".my_order_list_from").text();
            var changeEndPlaceId = $(this).parents("form").find(".my_order_list_to").text();
            var orderStatus = $(this).parents("form").find(".my_order_list_status").text();
            if(orderStatus != 1){
                alert("Order Can Not Be Changed");
                return;
            }
            replaceStationId(changeStartingPlaceId,changeEndPlaceId);
            $("#order_rebook_panel").css('display','block');
        }
    }
}

// function addPayButtonListener(){
//     var payOrderButtonSet = $(".pay_for_order_not_paid");
//     for(var i = 0;i < payOrderButtonSet.length;i++){
//         payOrderButtonSet[i].onclick = function(){
//             $("#preserve_pay_orderId").val($(this).parents("form").find(".my_order_list_id").text());
//             $("#preserve_pay_price").val($(this).parents("form").find(".my_order_list_price").text());
//             $("#preserve_pay_userId").val($(this).parents("form").find(".my_order_list_accountId").text());
//             $("#preserve_pay_panel").css('display','block');
//         }
//     }
// }

function addPayButtonOrNot(status){
    if(status == '0'){
        return "<button type='button' class='pay_for_order_not_paid btn btn-primary'>Pay</button>";
    }else{
        return "";
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

function convertNumberToDateTimeString(timeNumber){
    var str = new Date(timeNumber);
    return str.toDateString() + " " + str.toTimeString();
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
    }else if(code == 6){
        str = "Used";
    }else{
        str = "other";
    }
    return str;
}

function replaceStationId(stationIdOne,stationIdTwo){
    var getStationInfoOne = new Object();
    getStationInfoOne.stationId =  stationIdOne;
    var getStationInfoOneData = JSON.stringify(getStationInfoOne);
    $.ajax({
        type: "post",
        url: "/station/queryById",
        contentType: "application/json",
        dataType: "json",
        data:getStationInfoOneData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#travel_rebook_startingPlace").val(result["name"]);
        },
    });
    var getStationInfoTwo = new Object();
    getStationInfoTwo.stationId =  stationIdTwo;
    var getStationInfoTwoData = JSON.stringify(getStationInfoTwo);
    $.ajax({
        type: "post",
        url: "/station/queryById",
        contentType: "application/json",
        dataType: "json",
        data:getStationInfoTwoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#travel_rebook_terminalPlace").val(result["name"]);
        },
    });
}

/**
 *  Flow Preserve - Step 2 - Select Which Trip You want to change.
 **/

