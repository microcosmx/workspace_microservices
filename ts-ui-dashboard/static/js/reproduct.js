/**
 * Created by Administrator on 2017/8/7.
 */
$("#reproduct_ticket_confirm_error_btn").click(function () {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#reproduct_ticket_confirm_error_btn").attr("disabled",true);
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
        path = "/reproduction/reproduct";
    }else{
        path = "/reproduction/reproductOther";
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
        error: function(result){
            alert(result.toString());
        },
        complete: function(){
            $("#reproduct_ticket_confirm_error_btn").attr("disabled",false);
        }
    })
})

// $("#reproduct_ticket_confirm_correct_btn").click(function () {
//     if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
//         alert("Please Login");
//     }
//     $("#reproduct_ticket_confirm_correct_btn").attr("disabled",true);
//     var orderTicketInfo = new Object();
//     orderTicketInfo.contactsId = $("#ticket_confirm_contactsId").text();
//     orderTicketInfo.tripId = $("#ticket_confirm_tripId").text();
//     orderTicketInfo.seatType = $("#ticket_confirm_seatType").text();
//     orderTicketInfo.date = $("#ticket_confirm_travel_date").text();
//     orderTicketInfo.from = $("#ticket_confirm_from").text();
//     orderTicketInfo.to = $("#ticket_confirm_to").text();
//     var orderTicketsData = JSON.stringify(orderTicketInfo);
//     var tripType = orderTicketInfo.tripId.charAt(0);
//     if(tripType == 'G' || tripType == 'D'){
//         path = "/reproduction/reproductCorrect";
//     }else{
//         path = "/reproduction/reproductOtherCorrect";
//     }
//     $.ajax({
//         type: "post",
//         url: path,
//         contentType: "application/json",
//         dataType: "json",
//         data: orderTicketsData,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function (result) {
//             alert(result["message"]);
//             if(result['status'] == true){
//                 //$("#preserve_pay_panel").css('display','block');
//                 $("#preserve_pay_orderId").val(result["order"]["id"]);
//                 $("#preserve_pay_price").val(result["order"]["price"]);
//                 $("#preserve_pay_userId").val(result["order"]["accountId"]);
//                 $("#preserve_pay_tripId").val(result["order"]["trainNumber"]);
//                 location.hash="anchor_flow_preserve_pay";
//             }
//         },
//         complete: function(){
//             $("#reproduct_ticket_confirm_correct_btn").attr("disabled",false);
//         }
//     })
// })

$("#reproduct_long_connection_btn").click(function () {
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#reproduct_long_connection_btn").attr("disabled",true);
    path = "/order/longConnection";

    $.ajax({
        type: "get",
        url: path,
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            alert(result.toString());
        },
        complete: function(){
            $("#reproduct_long_connection_btn").attr("disabled",false);
        }
    })
})
