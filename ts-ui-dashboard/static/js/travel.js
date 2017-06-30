
/********************************************************************/
/********************Function For Travel Service**********************/

// $("#travel_create_button").click(function(){
//     var travelCreateInfo = new Object();
// //     var travelCreateInfo = {
// //         tripId : $("#travel_create_tripId").val();
// // }
//     travelCreateInfo.tripId = $("#travel_create_tripId").val();
//     travelCreateInfo.trainTypeId = $("#travel_create_trainTypeId").val();
//     travelCreateInfo.startingStation = $("#travel_create_startingStation").val();
//     travelCreateInfo.stations = $("#travel_create_stations").val();
//     travelCreateInfo.terminalStation = $("#travel_create_terminalStation").val();
//
//     travelCreateInfo.startingTime = "1970-01-01T" +  $("#travel_create_startingTime").val() +":00Z";
//     travelCreateInfo.endTime = "1970-01-01T" +  $("#travel_create_endTime").val() +":00Z";
//
//     var travelCreateData = JSON.stringify(travelCreateInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/create",
//         contentType: "application/json",
//         dataType: "text",
//         data: travelCreateData,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(result);
//         }
//     });
// });

//------For Trip retrieve------------

$("#travel_queryAll_button").click(function(){
    $.ajax({
        type: "get",
        url: "/travel/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["startingStationId"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startingTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
            //$("#travel_result").html(JSON.stringify(result));
        }
    });
});

$("#travel2_queryAll_button").click(function(){
    $.ajax({
        type: "get",
        url: "/travel2/queryAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var size = result.length;
            $("#query_travel2_list_table").find("tbody").html("");
            for(var i = 0;i < size;i++){
                $("#query_travel2_list_table_result").append(
                    "<tr>" +
                    "<td>" + result[i]["tripId"]["type"] + result[i]["tripId"]["number"] + "</td>" +
                    "<td>" + result[i]["trainTypeId"]     + "</td>" +
                    "<td>" + result[i]["startingStationId"]     + "</td>" +
                    "<td>" + result[i]["stationsId"]     + "</td>" +
                    "<td>" + result[i]["terminalStationId"]     + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["startingTime"]) + "</td>" +
                    "<td>" + convertNumberToTimeString(result[i]["endTime"]) + "</td>" +
                    "</tr>"
                );
            }
            //$("#travel_result").html(JSON.stringify(result));
        }
    });
});

//------For Trip update------------
$("#travel_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel_update_tripId").val();
    travelInfo.trainTypeId = $("#travel_update_trainTypeId").val();
    travelInfo.startingStationId =  $("#travel_update_startingStationId").val();
    travelInfo.stationsId = $("#travel_update_stationsId").val();
    travelInfo.terminalStationId = $("#travel_update_terminalStationId").val();
    travelInfo.startingTime = convertStringToTime($("#travel_update_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#travel_update_endTime").val());
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel_result").html(JSON.stringify(result));
        },
    });
});

$("#travel2_update_button").click(function(){
    var travelInfo = new Object();
    travelInfo.tripId = $("#travel2_update_tripId").val();
    travelInfo.trainTypeId = $("#travel2_update_trainTypeId").val();
    travelInfo.startingStationId =  $("#travel2_update_startingStationId").val();
    travelInfo.stationsId = $("#travel2_update_stationsId").val();
    travelInfo.terminalStationId = $("#travel2_update_terminalStationId").val();
    travelInfo.startingTime = convertStringToTime($("#travel2_update_startingTime").val());
    travelInfo.endTime = convertStringToTime($("#travel2_update_endTime").val());
    var data = JSON.stringify(travelInfo);
    $.ajax({
        type: "post",
        url: "/travel2/update",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            $("#travel2_result").html(JSON.stringify(result));
        },
    });
});

//------For Trip delete------------
// $("#travel_delete_button").click(function(){
//     var travelInfo = new Object();
//     travelInfo.tripId = $("#travel_delete_tripId").val();
//     var data = JSON.stringify(travelInfo);
//     $.ajax({
//         type: "post",
//         url: "/travel/delete",
//         contentType: "application/json",
//         dataType: "json",
//         data:data,
//         xhrFields: {
//             withCredentials: true
//         },
//         success: function(result){
//             $("#travel_result").html(JSON.stringify(result));
//         }
//     });
// });

/********************************************************************/
/*******************Function For Travel Query************************/
$("#travel_booking_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_booking_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_booking_terminalPlace").val();
    travelQueryInfo.departureTime= $("#travel_booking_date").val();
    var travelQueryData = JSON.stringify(travelQueryInfo);
    var train_type = $("#search_select_train_type").val();
    var i = 0;
    $("#tickets_booking_list_table").find("tbody").html("");

    if(train_type == 0){
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
                if(result[0] != null){
                    var obj = result;
                    for(var j=0,l = obj.length;j < l ; i++,j++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[j]["tripId"]["type"] + obj[j]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[j]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[j]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[j]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["endTime"])          + "</td>" +
                            "<td>" + obj[j]["economyClass"]                                + "</td>" +
                            "<td>" + obj[j]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }
            }
        });
        $.ajax({
            type: "post",
            url: "/travel2/query",
            contentType: "application/json",
            dataType: "json",
            data:travelQueryData,
            xhrFields: {
                withCredentials: true
            },
            success: function(result){
                if(result[0] != null){
                    var obj = result;
                    for(var j=0, l = obj.length ; j < l ;j++, i++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[j]["tripId"]["type"] + obj[j]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[j]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[j]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[j]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[j]["endTime"])          + "</td>" +
                            "<td>" + obj[j]["economyClass"]                                + "</td>" +
                            "<td>" + obj[j]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }
            }
        });

    }else if(train_type == 1){
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
                if(result[0] != null){
                    var obj = result;
                    $("#tickets_booking_list_table").find("tbody").html("");
                    for(var i = 0,l = obj.length ; i < l ; i++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[i]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[i]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["endTime"])          + "</td>" +
                            "<td>" + obj[i]["economyClass"]                                + "</td>" +
                            "<td>" + obj[i]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }
            }
        });

    }else if(train_type == 2){
        $.ajax({
            type: "post",
            url: "/travel2/query",
            contentType: "application/json",
            dataType: "json",
            data:travelQueryData,
            xhrFields: {
                withCredentials: true
            },
            success: function(result){
                if(result[0] != null){
                    var obj = result;
                    $("#tickets_booking_list_table").find("tbody").html("");
                    for(var i = 0,l = obj.length ; i < l ; i++){
                        $("#tickets_booking_list_table").find("tbody").append(
                            "<tr>" +
                            "<td>" + i + "</td>" +
                            "<td class='booking_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                            "<td class='booking_trainTypeId'>" + obj[i]["trainTypeId"] +  "</td>" +
                            "<td class='booking_from'>" + obj[i]["startingStation"]                             + "</td>" +
                            "<td class='booking_to'>" + obj[i]["terminalStation"]                             + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["startingTime"])     + "</td>" +
                            "<td>" + convertNumberToTimeString(obj[i]["endTime"])          + "</td>" +
                            "<td>" + obj[i]["economyClass"]                                + "</td>" +
                            "<td>" + obj[i]["confortClass"]                                + "</td>" +
                            "<td>" +
                            "<select class='form-control booking_seat_class'>" +
                            "<option value='2'>1st" + obj[j]["priceForConfortClass"] + "</option>" +
                            "<option value='3'>2st" + obj[j]["priceForEconomyClass"] + "</option>" +
                            "</select>" +
                            "</td>" +
                            "<td>" + "<button class='btn btn-primary ticket_booking_button'>" + "Booking" + "</button>"  + "</td>" +
                            "</tr>"
                        );
                    }
                    addListenerToBookingTable();
                }
            }
        });
    }
});



function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}

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