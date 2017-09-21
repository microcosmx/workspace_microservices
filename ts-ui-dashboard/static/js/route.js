
/**********************************************************************/
/********************Function For Route Service************************/
$("#refresh_route_button").click(function(){

});

$("#create_route_button").click(function(){

    var startId = $("#route_start_id").val();
    var terminalId = $("#route_terminal_id").val();
    var passById = $("#route_pass_station_id").val();
    var passByDistace = $("#route_pass_distance_id").val();
    var stations = passById.split(",");
    var distances = passByDistace.split(",");

    var newRoute = new Object();
    newRoute.startStationId = startId;
    newRoute.terminalStationId = terminalId;
    newRoute.stations = stations;
    newRoute.distances = distances;
    var routeData = JSON.stringify(newRoute);
    var createRouteObject = new Object();
    createRouteObject.route = routeData;
    var data = JSON.stringify(createRouteObject);
    alert(data);

    $("#create_route_button").attr("disabled",true);
    $("#create_route_status").html("false");

    $.ajax({
        type: "post",
        url: "/route/createAndModify",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            // if(obj["status"] == true){
            //     $("#security_check_message").html(obj["message"]);
            // }else{
            //     $("#security_check_message").html(obj["message"]);
            // }
            // $("#security_check_status").html("true");
        },
        complete: function(){
            $("#security_check_button").attr("disabled",false);
        }
    });

});