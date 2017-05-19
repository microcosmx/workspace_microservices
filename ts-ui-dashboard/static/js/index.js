/*
$(function(){
    $('form').ajaxForm({
        success: function(responseText){
            alert(responseText);
        }
    });
});
*/

var captchaChecked = false;

$(function() {
    refreshCaptcha();
    $("#captcha").on("keyup", checkCaptchaInput);
    $("#captchaImg").on("click", refreshCaptcha);
    $("#submit").on("click", goLogin);
});

function checkCaptchaInput(){
    var captchaText =$(this).val()
    if(captchaText.length <=3 ){ //验证码一般大于三位
        $("#captchaChecked").hide();
        return;
    }

    ajaxRequest("/verification/verify", {verificationCode : captchaText},
        function callback(result) {
            if(result.code == "40001"){
                if(result.data==true){
                    $("#captchaChecked").show();
                    captchaChecked = true;
                }else{
                    $("#captchaChecked").hide();
                    captchaChecked = false;
                }
            }else{
                alert(result.message);
            }
        });

    if(event.keyCode==13){
        goLogin();
    }
}

function goLogin() {
    if(!captchaChecked){
        alert("请输入正确的验证码！");
        return;
    }

    var params = $("form").serializeObject();
    params.password = md5(params.password);
    ajaxRequest("/servlet/auth/webLogin", params,
        function callback(result) {
            if(result.code == "40001"){
                alert("登录成功");
                history.go(-1);
            }
        },
        function errorCallback(){ //发生错误，刷新验证码
            refreshCaptcha();
        });
}

/*
function refreshCaptcha() {
    //重载验证码
    $('#captchaImg').attr('src', '10.141.252.21:15678/verification/generate');
}*/