/*---------------------Add By Ji Chao for ajax--------------------*/
//// Creates a XMLHttpRequest object.
var req = new XMLHttpRequest();

function getXmlHttpRequest(){
    var xmlHttpRequest= "";
    if(window.XMLHttpRequest){
        xmlHttpRequest = new XMLHttpRequest();
    } else{ // IE
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlHttpRequest;
}

//----For Login------

document.getElementById("login_button").onclick = function post_login(){

    req = getXmlHttpRequest();

    var loginInfo = new Object();
    loginInfo.phoneNum = document.getElementById("login_phoneNum").value;
    loginInfo.password = document.getElementById("login_password").value;
    loginInfo.verificationCode = document.getElementById("login_verification_code").value;
    var data = JSON.stringify(loginInfo);

    var url = "http://10.141.212.21:12342/login";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_login_result;
    req.send(data);
}

function handle_login_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        var obj = JSON.parse(resultstr);
        document.getElementById("login_result_status").innerHTML = obj["status"];
        document.getElementById("login_result_msg").innerHTML = obj["message"];
        document.getElementById("login_result_account").innerHTML = obj["account"];
        document.getElementById("login_result_token").innerHTML = obj["token"];
    }
}

//------For Register------
document.getElementById("register_button").onclick = function post_register(){

    req = getXmlHttpRequest();

    var registerInfo = new Object();
    registerInfo.password = document.getElementById("register_password").value;
    registerInfo.gender = document.getElementById("register_gender").value;
    registerInfo.name = document.getElementById("register_name").value;
    registerInfo.documentType = document.getElementById("register_documentType").value;
    registerInfo.documentNum = document.getElementById("register_documentNum").value;
    registerInfo.phoneNum = document.getElementById("register_phoneNum").value;
    registerInfo.verificationCode = document.getElementById("register_verificationCode").value;
    var data = JSON.stringify(registerInfo);

    var url = "http://10.141.212.21:12344/register";

    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_register_result;
    req.send(data);
}

function handle_register_result(){
    if(req.readyState == 4){
        var resultstr = req.responseText;
        var obj = JSON.parse(resultstr);
        document.getElementById("register_result_status").innerHTML = obj["status"];
        document.getElementById("register_result_msg").innerHTML = obj["message"];
        document.getElementById("register_result_account").innerHTML = obj["account"];
    }
}
