
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