
/********************************************************************/
/********************Function For Config Service*********************/

document.getElementById("config_update_button").onclick = function post_config_update(){
    var configInfo = new Object();
    configInfo.name = $("#config_update_name").val();
    configInfo.value = $("#config_update_value").val();
    configInfo.description = $("#config_update_description").val();
    var data = JSON.stringify(configInfo);
    $.ajax({
        type: "post",
        url: "/config/update",
        contentType: "application/json",
        dataType: "text",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#config_result").html(result);
        }
    });
}

//------For config query------------
document.getElementById("config_query_button").onclick = function post_config_query(){
    $.ajax({
        type: "get",
        url: "/config/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_config_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_config_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["name"]     + "</td>" +
                    "<td>" + result[i]["value"]     + "</td>" +
                    "<td>" + result[i]["description"]     + "</td>" +
                    "</tr>"
                );
            }
            //$("#config_result").html(result);
        }
    });
}

//------For Config delete------------
// document.getElementById("config_delete_button").onclick = function post_config_delete(){
//     var configInfo = new Object();
//     configInfo.name = $("#config_delete_name").val();
//     var data = JSON.stringify(configInfo);
//     $.ajax({
//         type: "post",
//         url: "/config/delete",
//         contentType: "application/json",
//         dataType: "text",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#config_result").html(result);
//         }
//     });
// }

//------For Config------------
//------For Config create------------
// document.getElementById("config_create_button").onclick = function post_config_create(){
//     var configInfo = new Object();
//     configInfo.name = $("#config_create_name").val();
//     configInfo.value = $("#config_create_value").val();
//     configInfo.description = $("#config_create_description").val();
//     var data = JSON.stringify(configInfo);
//     $.ajax({
//         type: "post",
//         url: "/config/create",
//         contentType: "application/json",
//         dataType: "text",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#config_result").html(result);
//         }
//     });
// }
