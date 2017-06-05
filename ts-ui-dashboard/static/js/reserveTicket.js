$("#travel_booking_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_booking_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_booking_terminalPlace").val();
    travelQueryInfo.departureTime= $("#travel_booking_datee").val();
    var travelQueryData = JSON.stringify(travelQueryInfo);
    $.ajax({
        type: "post",
        url: "/travel/query",
        contentType: "application/json",
        dataType: "json",
        data:travelQueryData,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            $("#tickets_booking_list_table").find("tbody").html("");
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#tickets_booking_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                    "<td>" + obj[i]["startingStation"]                             + "</td>" +
                    "<td>" + obj[i]["terminalStation"]                             + "</td>" +
                    "<td>" + obj[i]["startingTime"]                                + "</td>" +
                    "<td>" + obj[i]["endTime"]                                     + "</td>" +
                    "<td>" + obj[i]["economyClass"]                                + "</td>" +
                    "<td>" + obj[i]["confortClass"]                                + "</td>" +
                    "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                    "</tr>"
                );
            }
            addListenerToBookingTable();
        }
    });
});
