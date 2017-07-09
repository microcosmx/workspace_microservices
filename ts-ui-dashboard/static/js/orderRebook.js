
$("#single_rebook_button").click(function() {
    var singleRebookInfo = new Object();
    singleRebookInfo.orderId = $("#single_rebook_order_id").val();
    singleRebookInfo.oldTripId = $("#single_rebook_old_trip_id").val();
    singleRebookInfo.tripId = $("#single_rebook_trip_id").val();
    singleRebookInfo.seatType = $("#single_rebook_seat_type").val();
    singleRebookInfo.date = $("#single_rebook_date").val();
    var singleRebookInfoData = JSON.stringify(singleRebookInfo);
    $.ajax({
        type: "post",
        url: "/rebook/rebook",
        contentType: "application/json",
        dataType: "json",
        data: singleRebookInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if(result["status"]){
                $("#single_rebook_result").text("true");
            }else{
                $("#single_rebook_result").text(result["message"].toString());
                //if(result["message"].contains("Please pay the different money")){
                    $("#rebook_price").val(result["price"]);
                //}
            }
        }
    });
});

$("#rebook_pay_button").click(function(){
    var singleRebookInfo = new Object();
    singleRebookInfo.orderId = $("#single_rebook_order_id").val();
    singleRebookInfo.oldTripId = $("#single_rebook_old_trip_id").val();
    singleRebookInfo.tripId = $("#single_rebook_trip_id").val();
    singleRebookInfo.seatType = $("#single_rebook_seat_type").val();
    singleRebookInfo.date = $("#single_rebook_date").val();
    var singleRebookInfoData = JSON.stringify(singleRebookInfo);
    $.ajax({
        type: "post",
        url: "/rebook/payDifference",
        contentType: "application/json",
        dataType: "json",
        data: singleRebookInfoData,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {

            $("#rebook_payment_result").text(result["status"].toString());

        }
    });
});



$("#travel_rebook_cancel").click(function(){
    $("#order_rebook_panel").css('display','none');
});

$("#travel_rebook_button").click(function(){
    var travelQueryInfo = new Object();
    travelQueryInfo.startingPlace = $("#travel_rebook_startingPlace").val();
    travelQueryInfo.endPlace = $("#travel_rebook_terminalPlace").val();
    travelQueryInfo.departureTime= $("#travel_rebook_date").val();
    var travelQueryData = JSON.stringify(travelQueryInfo);
    var train_type = $("#search_rebook_train_type").val();
    var i = 0;
    $("#tickets_change_list_table").find("tbody").html("");
    if(train_type == 0){
        queryForRebookTravelInfo(travelQueryData,"/travel/query");
        queryForRebookTravelInfo(travelQueryData,"/travel2/query");
    }else if(train_type == 1){
        queryForRebookTravelInfo(travelQueryData,"/travel/query");
    }else if(train_type == 2){
        queryForRebookTravelInfo(travelQueryData,"/travel2/query");
    }
});

function queryForRebookTravelInfo(data,path) {
    $.ajax({
        type: "post",
        url: path,
        contentType: "application/json",
        dataType: "json",
        data: data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            if(result[0] != null){
                var obj = result;
                $("#tickets_change_list_table").find("tbody").html("");
                for(var i = 0,l = obj.length ; i < l ; i++){
                    $("#tickets_change_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td class='rebook_tripId'>" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td class='rebook_trainTypeId'>" + obj[i]["trainTypeId"] +  "</td>" +
                        "<td class='rebook_from'>" + obj[i]["startingStation"]                             + "</td>" +
                        "<td class='rebook_to'>" + obj[i]["terminalStation"]                             + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"])     + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"])          + "</td>" +
                        "<td>" + obj[i]["economyClass"]                                + "</td>" +
                        "<td>" + obj[i]["confortClass"]                                + "</td>" +
                        "<td>" +
                        "<select class='form-control rebook_seat_class'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td>" + "<button class='btn btn-primary ticket_rebook_button'>" + "Rebook" + "</button>"  + "</td>" +
                        "</tr>"
                    );
                }
                addListenerToRebookTable();
            }
        }
    });
}

function addListenerToRebookTable(){
    var ticketRebookButtonSet = $(".ticket_rebook_button");
    for(var i = 0;i < ticketRebookButtonSet.length;i++) {
        ticketRebookButtonSet[i].onclick = function () {
            $("#ticket_rebook_pay_panel").css('display','block');
            //Connect to rebook service, calculate the money that will pay.
        }
    }
}

$("#ticket_rebook_pay_panel_cancel").click(function(){
    $("#ticket_rebook_pay_panel").css('display','none');
});

$("#ticket_rebook_pay_panel_confirm").click(function(){
    alert("Not Complete.");
    $("#ticket_rebook_pay_panel").css('display','none');
    $("#order_rebook_panel").css('display','none');
});