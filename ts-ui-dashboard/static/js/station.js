
/********************************************************************/
/********************Function For Station Service********************/

$("#station_update_button").click(function(){
    var stationInfo = new Object();
    stationInfo.id = $("#station_update_id").val();
    stationInfo.name = $("#station_update_name").val();
    var data = JSON.stringify(stationInfo);
    $.ajax({
        type: "post",
        url: "/station/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
    });
});

$("#station_query_button").click(function(){
    $.ajax({
        type: "get",
        url: "/station/query",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_station_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_station_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"]     + "</td>" +
                    "<td>" + result[i]["name"]     + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

//------For Station delete------------
// document.getElementById("station_delete_button").onclick = function post_station_delete(){
//     var stationInfo = new Object();
//     stationInfo.name = $("#station_delete_name").val();
//     var data = JSON.stringify(stationInfo);
//     $.ajax({
//         type: "post",
//         url: "/station/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#station_result").html(JSON.stringify(result));
//         }
//     });
// }
