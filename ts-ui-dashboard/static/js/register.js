/*************************************************************************/
/********************Function For Register Service************************/
/********************Used for Register Service Single Microservice Test***/

$("#register_button").click(function() {
    var registerInfo = new Object();
    registerInfo.password = $("#register_password").val();
    registerInfo.gender = $("#register_gender").val();
    registerInfo.name = $("#register_name").val();
    registerInfo.documentType = $("#register_documentType").val();
    registerInfo.documentNum = $("#register_documentNum").val();
    registerInfo.email = $("#register_email").val();
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
});

