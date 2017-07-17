
/********************************************************************/
/********************Function For Price Service**********************/

$("#price_queryAll_button").click(function() {
    $.ajax({
        type: "get",
        url: "/price/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_price_list_table").find("tbody").html("");
            $("#price_result_talbe").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_price_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["placeA"] + "</td>" +
                    "<td>" + result[i]["placeB"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"] + "</td>" +
                    "<td>" + result[i]["seatClass"] + "</td>" +
                    "<td>" + result[i]["price"] + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

$("#price_update_button").click(function(){
    var priceUpdateInfo = new Object();
    priceUpdateInfo.placeA = $("#price_update_startingPlace").val();
    if(priceUpdateInfo.placeA == null || priceUpdateInfo.placeA == ""){
        alert("Please input starting place.");
        return;
    }
    priceUpdateInfo.placeB = $("#price_update_endPlace").val();
    if(priceUpdateInfo.placeB == null || priceUpdateInfo.placeB == ""){
        alert("Please input the terminal place.");
        return;
    }
    priceUpdateInfo.distance = $("#price_update_distance").val();
    if(priceUpdateInfo.distance == null || priceUpdateInfo.distance == ""){
        alert("Please input the distance.");
        return;
    }
    var data = JSON.stringify(priceUpdateInfo);
    $.ajax({
        type: "post",
        url: "/price/update",
        contentType: "application/json",
        data:data,
        // dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            // $("#price_result").html(result);
        }
    });
});

