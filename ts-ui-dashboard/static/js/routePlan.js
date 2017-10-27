$("#travel_min_stop_button").click(function(){
    var minStopSearchInfo = new Object();
    minStopSearchInfo.formStationName = $("#travel_min_stop_fromPlace").val();
    minStopSearchInfo.toStationName = $("#travel_min_stop_toPlace").val();
    minStopSearchInfo.travelDate = $("#travel_min_stop_date").val();
    minStopSearchInfo.num = 5;
    if(minStopSearchInfo.travelDate  == null || checkDateFormat(minStopSearchInfo.travelDate ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var minStopSearchData = JSON.stringify(minStopSearchInfo);
    alert(minStopSearchData);
    $("#tickets_min_stop_table").find("tbody").html("");
    queryForMinStopInfo(minStopSearchData,"/routePlan/minStopStations");
});

function queryForMinStopInfo(data,path) {
    $("#travel_min_stop_button").attr("disabled",true);
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
                var obj = result["results"];
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#tickets_min_stop_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["fromStationName"] + "</td>" +
                        "<td >" + obj[i]["toStationName"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" + To-Do + "</td>" +
                        "<td>" + To-Do + "</td>" +
                        "<td>" +
                        "<select class='form-control'>" +
                        "<option value='2'>1st - " + obj[i]["priceForFirstClassSeat"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForSecondClassSeat"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='noshow_component'>" + obj[i]["priceForFirstClassSeat"] + "</td>"+
                        "<td class='noshow_component'>" + obj[i]["priceForSecondClassSeat"] + "</td>"+
                        "</tr>"
                    );
                }
            }
        },
        complete: function () {
            $("#travel_min_stop_button").attr("disabled",false);
        }
    });
}