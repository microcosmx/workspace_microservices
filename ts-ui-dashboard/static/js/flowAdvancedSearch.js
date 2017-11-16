/**Before ***/
function setTodayDateAdvancedSearch(){
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
    document.getElementById("flow_advance_reserve_booking_date").setAttribute("min", today);
}

/**
 *  Flow Preserve - Step 1 - User Login
 **/
$("#flow_advance_reserve_login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.email = $("#flow_advance_reserve_login_email").val();
    if(loginInfo.email == null || loginInfo.email == ""){
        alert("Email Can Not Be Empty.");
        return;
    }
    if(checkEmailFormat(loginInfo.email) == false){
        alert("Email Format Wrong.");
        return;
    }
    loginInfo.password = $("#flow_advance_reserve_login_password").val();
    if(loginInfo.password == null || loginInfo.password == ""){
        alert("Password Can Not Be Empty.");
        return;
    }
    loginInfo.verificationCode = $("#flow_advance_reserve_login_verification_code").val();
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
                location.hash="anchor_flow_advance_reserve_select_trip";
            }else{
                alert(obj["message"]);
            }
        }
    });
});


/**
 * Flow Preserve - Step 5 - Pay For Your Ticket
 */

$("#flow_advance_reserve_pay_later_button").click(function(){
    location.hash="anchor_flow_advance_reserve_pay";
})

$("#flow_advance_reserve_pay_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#flow_advance_reserve_pay_button").attr("disabled",true);
    var info = new Object();
    info.orderId = $("#flow_advance_reserve_pay_orderId").val();
    info.tripId = $("#flow_advance_reserve_pay_tripId").val();
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
                $("#flow_advanced_reserve_collect_order_id").val(info.orderId);
                location.hash="anchor_flow_advance_reserve_collect";
            }else{
                alert("Pay Fail. Reason Not Clear.Please check the order status before you try.");
            }
        },
        complete: function(){
            $("#flow_advance_reserve_pay_button").attr("disabled",false);
        }
    });
})



/**
 * Flow Preserve - Step 6 - Collect Your Ticket
 */

$("#flow_advanced_reserve_collect_button").click(function() {
    $("#flow_advanced_reserve_collect_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#flow_advanced_reserve_collect_order_id").val();
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
                $("#advanced_reserve_execute_order_id").val(executeInfo.orderId);
                location.hash="anchor_flow_advance_reserve_execute";
            }else{
                alert(obj["message"]);
            }
        },
        complete: function(){
            $("#flow_advanced_reserve_collect_button").attr("disabled",false);
        }
    });
});;

/**
 * Flow Preserve - Step 7 - Enter Station
 */

$("#flow_advanced_reserve_execute_order_button").click(function() {
    $("#flow_advanced_reserve_execute_order_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#advanced_reserve_execute_order_id").val();
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
                alert(obj["message"]);
            }else{
                alert(obj["message"]);
            }
        },
        complete: function(){
            $("#flow_advanced_reserve_execute_order_button").attr("disabled",false);
        }
    });
});
