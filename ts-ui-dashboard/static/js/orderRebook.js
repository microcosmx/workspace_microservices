
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



