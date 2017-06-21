
/********************************************************************/
/********************Function For Train Service**********************/

document.getElementById("train_update_button").onclick = function post_train_update(){
    var trainInfo = new Object();
    trainInfo.id = $("#train_update_id").val();
    trainInfo.economyClass = $("#train_update_economyClass").val();
    trainInfo.confortClass = $("#train_update_confortClass").val();
    var data = JSON.stringify(trainInfo);
    $.ajax({
        type: "post",
        url: "/train/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#train_result").html(JSON.stringify(result));
        }
    });
}

document.getElementById("train_query_button").onclick = function post_train_query(){
    // var trainInfo = new Object();
    // trainInfo.id = $("#train_query_id").val();
    // var data = JSON.stringify(trainInfo);
    $.ajax({
        type: "get",
        url: "/train/query",
        // contentType: "application/json",
        dataType: "json",
        // data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_train_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_train_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"]     + "</td>" +
                    "<td>" + result[i]["confortClass"]     + "</td>" +
                    "<td>" + result[i]["economyClass"]     + "</td>" +
                    "</tr>"
                );
            }
            //$("#train_result").html(JSON.stringify(result));
        }
    });
}

//------For Train delete------------
// document.getElementById("train_delete_button").onclick = function post_train_delete(){
//     var trainInfo = new Object();
//     trainInfo.id = $("#train_delete_id").val();
//     var data = JSON.stringify(trainInfo);
//     $.ajax({
//         type: "post",
//         url: "/train/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#train_result").html(JSON.stringify(result));
//         }
//     });
// }

//------For Train------------
//------For Train create------------
// document.getElementById("train_create_button").onclick = function post_train_create(){
//     var trainInfo = new Object();
//     trainInfo.id = $("#train_create_id").val();
//     trainInfo.economyClass = $("#train_create_economyClass").val();
//     trainInfo.confortClass = $("#train_create_confortClass").val();
//     var data = JSON.stringify(trainInfo);
//     $.ajax({
//         type: "post",
//         url: "/train/create",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#train_result").html(JSON.stringify(result));
//         }
//     });
// }
