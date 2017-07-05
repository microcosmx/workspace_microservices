
/********************************************************************/
/********************Function For SSO Service************************/

$("#refresh_account_button").click(function() {
    refresh_account();
});

function refresh_account() {
    $.ajax({
        type: "get",
        url: "/account/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#account_list_table").find("tbody").html("");
            var obj = result["accountArrayList"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='sso_account_id'>" + obj[i]["id"] + "</td>" +
                    "<td ><input class='sso_account_email form-control' value='" + obj[i]["email"] + "'></td>" +
                    "<td ><input class='sso_account_password form-control' value='" + obj[i]["password"] + "'></td>" +
                    "<td>" + "<button class='account_update_btn btn btn-primary'>Update</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToSsoAccountTable();
            alert("Success.");
        }
    });
}

function addListenerToSsoAccountTable(){
    var accountUpdateBtnSet = $(".account_update_btn");
    for(var i = 0;i < accountUpdateBtnSet .length;i++){
        accountUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.accountId = $(this).parents("tr").find(".sso_account_id").text();
            modifyInfo.newEmail = $(this).parents("tr").find(".sso_account_email").val();
            modifyInfo.newPassword = $(this).parents("tr").find(".sso_account_password").val();
            var data = JSON.stringify(modifyInfo);
            $.ajax({
                type: "post",
                url: "/account/modify",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_account();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

$("#refresh_login_account_button").click(function() {
    refresh_login_account();
});

function refresh_login_account() {
    $.ajax({
        type: "get",
        url: "/account/findAllLogin",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#login_account_list_table").find("tbody").html("");
            var obj = result["loginAccountList"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#login_account_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='account_kick_id'>" + obj[i]["accountId"] + "</td>" +
                    "<td class='account_kick_token'>" + obj[i]["loginToken"] + "</td>" +
                    "<td>" +  "<button class='account_kick_off_btn btn btn-primary'>Kick Off</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToSsoLoginAccountTable();
            alert("Success.");
        }
    });
}

function addListenerToSsoLoginAccountTable(){
    var accountKickOffBtnSet = $(".account_kick_off_btn");
    for(var i = 0;i < accountKickOffBtnSet.length;i++){
        accountKickOffBtnSet[i].onclick = function(){
            var logoutInfo = new Object();
            logoutInfo.id = $(this).parents("tr").find(".account_kick_id").text();
            logoutInfo.token = $(this).parents("tr").find(".account_kick_token").text();
            var data = JSON.stringify(logoutInfo);
            $.ajax({
                type: "post",
                url: "/logout",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_login_account();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

$("#logout_button").click(function() {
    var logoutInfo = new Object();
    logoutInfo.id = $("#user_login_id").html();
    logoutInfo.token = $("#user_login_token").html();
    var data = JSON.stringify(logoutInfo);
    $.ajax({
        type: "post",
        url: "/logout",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result["status"] == true){
                $("#user_login_id").html("Not Login");
                $("#user_login_token").html("Please Login");
            }else if(result["message"] == "Not Login"){
                $("#user_login_id").html("Not Login");
                $("#user_login_token").html("Please Login");
            }
        }
    });
});
