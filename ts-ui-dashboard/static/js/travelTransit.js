$("#transit_search_button").click(function(){
    var transitSearchInfo = new Object();
    transitSearchInfo.fromStationName = $("#transit_start_station").val();
    transitSearchInfo.viaStationName = $("#transit_middle_station").val();
    transitSearchInfo.toStationName = $("#transit_end_station").val();
    transitSearchInfo.travelDate = $("#transit_end_station_date").val();
    if(transitSearchInfo.travelDate  == null || checkDateFormat(transitSearchInfo.travelDate ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    transitSearchInfo.trainType = 0;
    var transitSearchData = JSON.stringify(transitSearchInfo);
    alert(transitSearchData);
    $("#transit_search_list_table_first_section_table").find("tbody").html("");
    $("#transit_search_list_table_second_section_table").find("tbody").html("");
    queryForTransitTravelInfo(transitSearchData,"/travelPlan/getTransferResult");
});

function queryForTransitTravelInfo(data,path) {
    $("#transit_search_button").attr("disabled",true);
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data: data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status = true) {
                var obj = result["firstSectionResult"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#transit_search_list_table_first_section_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] + "</td>" +
                        "<td class='booking_from'>" + obj[i]["startingStation"] + "</td>" +
                        "<td class='booking_to'>" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["economyClass"] + "</td>" +
                        "<td>" + obj[i]["confortClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control booking_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='booking_seat_price_confort noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='booking_seat_price_economy noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "</tr>"
                    );
                }
            }
            if (result.status = true) {
                var obj = result["secondSectionResult"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#transit_search_list_table_second_section_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] + "</td>" +
                        "<td class='booking_from'>" + obj[i]["startingStation"] + "</td>" +
                        "<td class='booking_to'>" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + obj[i]["economyClass"] + "</td>" +
                        "<td>" + obj[i]["confortClass"] + "</td>" +
                        "<td>" +
                        "<select class='form-control booking_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='booking_seat_price_confort noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='booking_seat_price_economy noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "</tr>"
                    );
                }
            }
        },
        complete: function () {
            $("#transit_search_button").attr("disabled",false);
        }
    });
}