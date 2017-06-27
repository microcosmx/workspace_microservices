
/********************************************************************/
/*****************Function For Basic Information*********************/

$("#basic_information_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#basic_information_tripId").val();
    travelInfo.trainTypeId = $("#basic_information_trainTypeId").val();
    travelInfo.startingStation =  $("#basic_information_startingStation").val();
    travelInfo.stations = $("#basic_information_stations").val();
    travelInfo.terminalStation = $("#basic_information_terminalStation").val();
    travelInfo.startingTime = convertStringToTime($("#basic_information_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#basic_information_endTime").val());
    var basicInfo = new Object();
    basicInfo.trip = travelInfo;
    basicInfo.startingPlace = $("#basic_information_startingPlace").val();
    basicInfo.endPlace = $("#basic_information_endPlace").val();
    basicInfo.departureTime = $("#basic_information_departureTime").val();
    var data = JSON.stringify(basicInfo);
    $.ajax({
        type: "post",
        url: "/basic/queryForTravel",
        contentType: "application/json",
        data:data,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#query_basic_information_list_table").find("tbody").html("");
            $("#query_basic_information_list_table").find("tbody").append(
                "<tr>" +
                "<td>" + result["status"] + "</td>" +
                "<td>" + result["percent"] + "</td>" +
                "<td>" + result["trainType"]["id"] + "</td>" +
                "<td>" + result["trainType"]["economyClass"] + "</td>" +
                "<td>" + result["trainType"]["confortClass"] + "</td>" +
                "<td>" + result["prices"]["economyClass"] + "</td>" +
                "<td>" + result["prices"]["confortClass"] + "</td>" +
                "</tr>"
            );
        }
    });
});

function convertStringToTime(string){
    var date = new Date();
    var s = string.toString();
    var index = s.indexOf(':');
    var hour = s.substring(0,index).valueOf();
    var minute = s.substring(index+1,s.length).valueOf();
    date.setHours(hour);
    date.setMinutes(minute);
    return date;
}
