
/************************************************************************/
/********************Function For Security Service***********************/
/********************Used For Security Service Single Microservice Test**/

$("#security_check_button").click(function() {
    var checkInfo = new Object();
    checkInfo.accountId = $("#security_check_account_id").val();
    var data = JSON.stringify(checkInfo);
    $.ajax({
        type: "post",
        url: "/security/check",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#security_check_status").html(obj["message"]);
            }else{
                $("#security_check_status").html(obj["message"]);
            }
        }
    });
});


$("#refresh_security_config_button").click(function() {
    refresh_security_config();
});

function refresh_security_config() {
    $.ajax({
        type: "get",
        url: "/securityConfig/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#security_config_list_table").find("tbody").html("");
            var obj = result["result"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#security_config_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='noshow_component list_security_config_id'>" + obj[i]["id"] + "</td>" +
                    "<td class='list_security_config_name'>" + obj[i]["name"] + "</td>" +
                    "<td ><input class='list_security_config_id_value form-control' value='" + obj[i]["value"] + "'></td>" +
                    "<td ><input class='list_security_config_id_description form-control' value='" + obj[i]["description"] + "'></td>" +
                    "<td>" + "<button class='security_config_update_btn btn btn-primary'>Update</button>" + "<button class='security_config_delete btn btn-primary'>Delete</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToAllSecurityConfigTable();
            alert("Success.");
        }
    });
}

function addListenerToAllSecurityConfigTable(){
    var allSecurityConfigUpdateBtnSet = $(".security_config_update_btn");
    for(var i = 0;i < allSecurityConfigUpdateBtnSet.length;i++){
        allSecurityConfigUpdateBtnSet[i].onclick = function(){
            var modifyInfo = new Object();
            modifyInfo.id = $(this).parents("tr").find(".list_security_config_id").text();
            modifyInfo.name = $(this).parents("tr").find(".list_security_config_name").text();
            modifyInfo.value = $(this).parents("tr").find(".list_security_config_id_value").val();
            modifyInfo.description = $(this).parents("tr").find(".list_security_config_id_description").val();
            var data = JSON.stringify(modifyInfo);
            $.ajax({
                type: "post",
                url: "/securityConfig/update",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_security_config();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
    var allSecurityConfigDeleteBtnSet = $(".security_config_delete");
    for(var i = 0;i < allSecurityConfigDeleteBtnSet.length;i++){
        allSecurityConfigDeleteBtnSet[i].onclick = function(){
            var deleteInfo = new Object();
            deleteInfo.id = $(this).parents("tr").find(".list_security_config_id").text();
            var data = JSON.stringify(deleteInfo);
            $.ajax({
                type: "post",
                url: "/securityConfig/delete",
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_security_config();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}


