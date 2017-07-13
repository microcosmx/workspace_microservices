
/********************************************************************/
/********************Function For Login Service**********************/
/********************Used For Login Service Single Microservice Test*/

$("#login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.email = $("#login_email").val();
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
                document.cookie = "loginId=" + obj["account"].id;
                document.cookie = "loginToken=" + obj["token"];
                $("#user_login_id").text(obj["account"].id);
            }
            $("#login_result_status").html(JSON.stringify(obj["status"]));
            $("#login_result_msg").html(obj["message"]);
            $("#login_result_account").html(JSON.stringify(obj["account"]));
            $("#login_result_token").html(JSON.stringify(obj["token"]));
        }
    });
});
