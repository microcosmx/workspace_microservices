$("#flow_preserve_login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.phoneNum = $("#flow_preserve_login_email").val();
    loginInfo.password = $("#flow_preserve_login_password").val();
    loginInfo.verificationCode = $("#flow_preserve_login_verification_code").val();
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
                document.cookie = "loginId=" + obj["account"].id;
                document.cookie = "loginToken=" + obj["token"];
                $("#flow_preserve_login_status").text(obj["message"]);
            }else{
                setCookie(name, "", -1);
                $("#flow_preserve_login_status").text(obj["message"]);
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
