$("#get_distance_button").click(function(){
    var info = new Object();
    info.tripId = $("#get_distance_trip_id").val();
    info.fromStaionName = $("#get_distance_from").val();
    info.toStationName = $("#get_distance_to").val();
    var data = JSON.stringify(info);
    $("#get_distance_button").attr("disabled",true);
    $.ajax({
        type: "post",
        url: "/travel/getDistance",
        contentType: "application/json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            alert(result["distance"]);
            var route = result["route"];
            var stations = route["stations"];
            var distances = route["distances"];
            for(var i = 0,l = stations.length ; i < l ; i++){
                $("#get_distance_route_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td>" + stations[i] + "</td>" +
                    "<td>" + distances[i] + "</td>" +
                    "</tr>"
                );
            }

        },
        complete: function(){
            $("#get_distance_button").attr("disabled",false);
        }
    });
});