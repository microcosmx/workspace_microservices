var postLoginObject;
//创建Ajax引擎对象
function getXmlHttpRequest() {
    var xmlHttpRequest = "";
    if(window.XMLHttpRequest){
        xmlHttpRequest = new XMLHttpRequest();
    } else {
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlHttpRequest;
}

function post_login() {
    postLoginObject = getXmlHttpRequest();
    var phone_number = document.getElementById("login_phone_number").value;
    var password = document.getElementById("login_password").value;
    var url = "http://localhost:12345/login";
    var data = "{\"password\":\"" + password + "\",\"phoneNum\":\"" + phone_number + "\"}";
    postLoginObject.open("post",url,true);
    postLoginObject.setRequestHeader("Content-Type","application/json; charset=UTF-8");
    postLoginObject.setRequestHeader("Origin","http://api.bob.com")
    postLoginObject.onreadystatechange = handle_login;
    postLoginObject.send(data);
}

function handle_login() {
    if(postLoginObject.readyState == 4){
        var resultstr = postLoginObject.responseText;
        if(resultstr.length < 10){
            alert("Login Fail");
        }else{
            var obj = JSON.parse(resultstr);
            var status = obj["password"];
            alert("Login Success. Welcome, " + obj["name"]);
            window.location.href="../../ticketclient/html/main_page.html";
        }
    }
}
