/**
 * Created by chaoj on 2017/7/3.
 */
$("#single_cancel_button").click(function(){
    document.cookie="loginToken=admin";
    var cancelOrderInfo = new Object();
    cancelOrderInfo.orderId =  $("#single_cancel_order_id").val();
    var cancelOrderInfoData = JSON.stringify(cancelOrderInfo);
    $.ajax({
        type: "post",
        url: "/cancelOrder",
        contentType: "application/json",
        dataType: "json",
        data: cancelOrderInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#single_cancel_order_status").text(result["message"]);
        }
    });
});

$("#single_cancel_refund_button").click(function(){
    var cancelOrderInfo = new Object();
    cancelOrderInfo.orderId =  $("#single_cancel_order_id").val();
    var cancelOrderInfoData = JSON.stringify(cancelOrderInfo);
    $.ajax({
        type: "post",
        url: "/cancelCalculateRefund",
        contentType: "application/json",
        dataType: "json",
        data: cancelOrderInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#single_cancel_refund_status").text(result["refund"]);
        }
    });
});

$("#ticket_cancel_panel_cancel").click(function(){
    $("#ticket_cancel_panel").css('display','none');
});

$("#ticket_cancel_panel_confirm").click(function(){
    var cancelOrderInfo = new Object();
    cancelOrderInfo.orderId =  $("#ticket_cancel_order_id").text();
    var cancelOrderInfoData = JSON.stringify(cancelOrderInfo);
    $.ajax({
        type: "post",
        url: "/cancelOrder",
        contentType: "application/json",
        dataType: "json",
        data: cancelOrderInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            alert(result["message"]);
        }
    });
});