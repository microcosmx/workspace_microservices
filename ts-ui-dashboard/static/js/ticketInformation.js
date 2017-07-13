
/****************************************************************************/
/*****************Function For Ticket Information****************************/
/*****************For Ticket Information Service Single Microservice Test****/

$("#ticketinfo_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#ticketinfo_tripId").val();
    travelInfo.trainTypeId = $("#ticketinfo_trainTypeId").val();
    travelInfo.startingStation =  $("#ticketinfo_startingStation").val();
    travelInfo.stations = $("#ticketinfo_stations").val();
    travelInfo.terminalStation = $("#ticketinfo_terminalStation").val();
    travelInfo.startingTime = convertStringToTime($("#ticketinfo_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#ticketinfo_endTime").val());
    var ticketInfo = new Object();
    ticketInfo.trip = travelInfo;
    ticketInfo.startingPlace = $("#ticketinfo_startingPlace").val();
    ticketInfo.endPlace = $("#ticketinfo_endPlace").val();
    ticketInfo.departureTime = $("#ticketinfo_departureTime").val();
    var data = JSON.stringify(ticketInfo);
    $.ajax({
        type: "post",
        url: "/ticketinfo/queryForTravel",
        contentType: "application/json",
        data:data,
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#query_ticketinfo_list_table").find("tbody").html("");
            $("#query_ticketinfo_list_table").find("tbody").append(
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