/**
 * Created by chaoj on 2017/5/11.
 */

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

document.getElementById("login_button").onclick = function post_login(){

    req = getXmlHttpRequest();

    var loginInfo = new Object();
    loginInfo.phoneNum = document.getElementById("login_phoneNum").value;
    loginInfo.password = document.getElementById("login_password").value;
    loginInfo.verificationCode = document.getElementById("login_verification_code").value;

    var url = "http://10.141.212.21:12342/login";
    var data = JSON.stringify(loginInfo);
    alert(data);
    req.open("post",url,true);
    req.withCredentials = true;
    req.setRequestHeader("Content-Type", "application/json");
    req.onreadystatechange = handle_login_result;
    req.send(data);
}



function handle_login_result(){
    if(req.readyState==4){
        var resultstr = req.responseText;
        var obj = JSON.parse(resultstr);
        var status = obj["status"];
        if(status == "true"){

        }else{

        }
    }
}

