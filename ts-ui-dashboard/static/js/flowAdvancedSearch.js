/**Before ***/
function setTodayDateAdvancedSearch(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd < 10){
        dd='0' + dd
    }
    if(mm < 10){
        mm = '0' + mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("flow_advance_reserve_booking_date").setAttribute("min", today);
}

/**
 *  Flow Preserve - Step 1 - User Login
 **/
$("#flow_advance_reserve_login_button").click(function() {
    var loginInfo = new Object();
    loginInfo.email = $("#flow_advance_reserve_login_email").val();
    if(loginInfo.email == null || loginInfo.email == ""){
        alert("Email Can Not Be Empty.");
        return;
    }
    if(checkEmailFormat(loginInfo.email) == false){
        alert("Email Format Wrong.");
        return;
    }
    loginInfo.password = $("#flow_advance_reserve_login_password").val();
    if(loginInfo.password == null || loginInfo.password == ""){
        alert("Password Can Not Be Empty.");
        return;
    }
    loginInfo.verificationCode = $("#flow_advance_reserve_login_verification_code").val();
    if(loginInfo.verificationCode == null || loginInfo.verificationCode == ""){
        alert("Verification Code Can Not Be Empty.");
        return;
    }
    var data = JSON.stringify(loginInfo);
    $.ajax({
        type: "post",
        url: "/login",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#user_login_id").html(obj["account"].id);
                $("#user_login_token").html(obj["token"]);
                document.cookie = "loginId=" + obj["account"].id;
                document.cookie = "loginToken=" + obj["token"];
                location.hash="anchor_flow_advance_reserve_select_trip";
            }else{
                alert(obj["message"]);
            }
        }
    });
});

/**
 *  Flow Preserve - Step 2 - Search For Ticket
 **/

$("#flow_advance_reserve_booking_button").click(function() {
    var advanceSearchInfo = new Object();
    advanceSearchInfo.formStationName = $("#flow_advance_reserve_startingPlace").val();
    advanceSearchInfo.toStationName = $("#flow_advance_reserve_terminalPlace").val();
    advanceSearchInfo.travelDate = $("#flow_advance_reserve_booking_date").val();
    advanceSearchInfo.num = 5;
    if(advanceSearchInfo.travelDate  == null || checkDateFormat(advanceSearchInfo.travelDate ) == false){
        alert("Departure Date Format Wrong.");
        return;
    }
    var advanceSearchData = JSON.stringify(advanceSearchInfo);
    $("#flow_advance_reserve_booking_list_table").find("tbody").html("");
    var selectType = $("#flow_advance_reserve_select_searchType").val();
    alert(selectType);
    if(selectType == 0){
        advanceSearchForMinStopInfo(advanceSearchData,"/routePlan/minStopStations");
    }else if(selectType == 1){
        advanceSearchForCheapestInfo(advanceSearchData,"/routePlan/cheapestRoute");
    }else if(selectType == 2){
        advanceSearchForQuickestInfo(advanceSearchData,"/routePlan/quickestRoute");
    }else{
        alert("Select Search Type Wrong");
    }
})

function advanceSearchForCheapestInfo(data,path) {
    $("#flow_advance_reserve_booking_button").attr("disabled",true);
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
                var obj = result;
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#flow_advance_reserve_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["startingStation"] + "</td>" +
                        "<td >" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" +
                        "<select class='form-control'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "</tr>"
                    );
                }
            }
        },
        complete: function () {
            $("#flow_advance_reserve_booking_button").attr("disabled",false);
        }
    });
}

function advanceSearchForQuickestInfo(data,path) {
    $("#flow_advance_reserve_booking_button").attr("disabled",true);
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
                var obj = result;
                for (var i = 0, l = obj.length; i < l; i++) {
                    $("#flow_advance_reserve_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"]["type"] + obj[i]["tripId"]["number"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["startingStation"] + "</td>" +
                        "<td >" + obj[i]["terminalStation"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
                        "<td>" +
                        "<select class='form-control'>" +
                        "<option value='2'>1st - " + obj[i]["priceForConfortClass"] + "</option>" +
                        "<option value='3'>2st - " + obj[i]["priceForEconomyClass"] + "</option>" +
                        "</select>" +
                        "</td>" +
                        "<td class='noshow_component'>" + obj[i]["priceForConfortClass"] + "</td>"+
                        "<td class='noshow_component'>" + obj[i]["priceForEconomyClass"] + "</td>"+
                        "</tr>"
                    );
                }
            }
        },
        complete: function () {
            $("#flow_advance_reserve_booking_button").attr("disabled",false);
        }
    });
}

function advanceSearchForMinStopInfo(data,path) {
    $("#flow_advance_reserve_booking_button").attr("disabled",true);
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
                    $("#flow_advance_reserve_booking_list_table").find("tbody").append(
                        "<tr>" +
                        "<td>" + i + "</td>" +
                        "<td >" + obj[i]["tripId"] + "</td>" +
                        "<td >" + obj[i]["trainTypeId"] + "</td>" +
                        "<td >" + obj[i]["fromStationName"] + "</td>" +
                        "<td >" + obj[i]["toStationName"] + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["startingTime"]) + "</td>" +
                        "<td>" + convertNumberToTimeString(obj[i]["endTime"]) + "</td>" +
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
            $("#flow_advance_reserve_booking_button").attr("disabled",false);
        }
    });
}

/**
 *  Flow Preserve - Step 3 - Search For Contact and Food
 **/
function flow_advance_search_needFoodOrNot(){
    if($('#flow_advance_reserve_need_food_or_not_need_food_or_not').is(':checked')) {
        $('#flow_advance_reserve_food_preserve_select').css("display", "block");
    } else {
        $('#flow_advance_reserve_food_preserve_select').css("display", "none");
        $('#flow_advance_reserve_food_store_selected').css("display", "none");
        $('#flow_advance_reserve_train_food_selected').css("display", "none");
        $('#flow_advance_reserve_preserve_food_type').val(0);
    }
}

function flow_advance_search_changeFoodType(){
    var type = $('#flow_advance_reserve_preserve_food_type').find("option:selected").val();
    if(type == 1){
        $('#flow_advance_reserve_train_food_selected').css("display", "block");
        $('#flow_advance_reserve_food_store_selected').css("display", "none");
        $('#flow_advance_reserve_food_station_list').val(0);
    } else if(type == 2){
        $('#flow_advance_reserve_train_food_selected').css("display", "none");
        $('#flow_advance_reserve_food_store_selected').css("display", "block");
    } else {
        $('#flow_advance_reserve_train_food_selected').css("display", "none");
        $('#flow_advance_reserve_food_store_selected').css("display", "none");
    }
}

var  flow_advance_search_preserveFoodStoreListMap = null;

function flow_advance_search_initFoodSelect(tripId){
    var data = new Object();
    data.date = $('#flow_advance_reserve_booking_date').val() || "";
    data.startStation = $('#flow_advance_reserve_startingPlace').val() || "";
    data.endStation = $('#flow_advance_reserve_terminalPlace').val() || "";
    data.tripId = tripId;

    // alert(JSON.stringify(data));
    $.ajax({
        type: "post",
        url: "/food/getFood",
        contentType: "application/json",
        dataType: "json",
        data:JSON.stringify(data),
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            console.log(result);
            if(result.status){

                if(null == result.trainFoodList || result.trainFoodList.length == 0){
                    //没有
                    $('#flow_advance_reserve_train_food_option').disabled(true);
                } else {
                    var trainFoodList = result.trainFoodList[0]['foodList'];
                    console.log("trainFoodList:" );
                    console.log(trainFoodList[0]);

                    $("#flow_advance_reserve_train_food_type_list").html("");
                    $("#flow_advance_reserve_food_station_list").html("");
                    $("#flow_advance_reserve_food_stores_list").html("");
                    $("#flow_advance_reserve_food_store_food_list").html("");

                    var trainFoodSelect = document.getElementById ("flow_advance_reserve_train_food_type_list");
                    var opt1 = document.createElement ("option");
                    opt1.value = 0;
                    opt1.innerText = "-- --";
                    trainFoodSelect.appendChild(opt1);
                    for(var k = 0; k < trainFoodList.length; k++){
                        var opt2 = document.createElement ("option");
                        opt2.value = k + 1;
                        opt2.innerText = trainFoodList[k]['foodName'] + ":$" + trainFoodList[k]['price'];
                        trainFoodSelect.appendChild (opt2);
                    }
                }


                flow_advance_search_preserveFoodStoreListMap = result.foodStoreListMap;
                console.log(" flow_advance_search_preserveFoodStoreListMap:");
                console.log( flow_advance_search_preserveFoodStoreListMap);
                var foodStationSelect = document.getElementById ("flow_advance_reserve_food_station_list");
                var opt3 = document.createElement ("option");
                opt3.value = 0;
                opt3.innerText = "-- --";
                foodStationSelect.appendChild(opt1);
                var fsindex = 1;
                for(var key in flow_advance_search_preserveFoodStoreListMap){
                    var opt4 = document.createElement ("option");
                    opt4.value = fsindex;
                    fsindex++;
                    opt4.innerText = key;
                    foodStationSelect.appendChild (opt4);
                }

            } else {
                alert(result.status + ":" + result.message);
            }

        }
    });

}

function flow_advance_search_preserveChangeFoodStation(){
    var station = $('#flow_advance_reserve_food_station_list').find("option:selected").text();
    var  foodStoreList = flow_advance_search_preserveFoodStoreListMap[station];

    var foodStoreSelect = document.getElementById ("flow_advance_reserve_food_stores_list");
    foodStoreSelect.innerHTML = "";
    var opt5 = document.createElement ("option");
    opt5.value = 0;
    opt5.innerText = "-- --";
    foodStoreSelect.appendChild(opt5);
    for(var j = 0; j < foodStoreList.length; j++){
        var opt6 = document.createElement ("option");
        opt6.value = j + 1;
        opt6.innerText = foodStoreList[j]["storeName"];
        foodStoreSelect.appendChild (opt6);
    }
}

function flow_advance_search_preserveChangeFoodStore(){
    var station = $('#flow_advance_reserve_food_station_list').find("option:selected").text();
    var storeIndex = parseInt($('#flow_advance_reserve_food_stores_list').find("option:selected").val());
    console.log("storeIndex=" + storeIndex);
    var  foodList = flow_advance_search_preserveFoodStoreListMap[station][storeIndex-1]['foodList'];
    console.log("preserveChangeFoodStore: foodList: ");
    console.log(foodList);

    var foodStoreFoodSelect = document.getElementById ("flow_advance_reserve_food_store_food_list");
    foodStoreFoodSelect.innerHTML = "";
    var opt5 = document.createElement ("option");
    opt5.value = 0;
    opt5.innerText = "-- --";
    foodStoreFoodSelect.appendChild(opt5);
    for(var j = 0; j < foodList.length; j++){
        var opt6 = document.createElement ("option");
        opt6.value = j+1;
        opt6.innerText = foodList[j]['foodName'] +":$" + foodList[j]['price'];
        foodStoreFoodSelect.appendChild (opt6);
    }
}


/**
 *  Flow Preserve - Step 4 - Confirm Your Order
 **/


/**
 * Flow Preserve - Step 5 - Pay For Your Ticket
 */

$("#flow_advance_reserve_pay_later_button").click(function(){
    location.hash="anchor_flow_advance_reserve_pay";
})

$("#flow_advance_reserve_pay_button").click(function(){
    if(getCookie("loginId").length < 1 || getCookie("loginToken").length < 1){
        alert("Please Login");
    }
    $("#flow_advance_reserve_pay_button").attr("disabled",true);
    var info = new Object();
    info.orderId = $("#flow_advance_reserve_pay_orderId").val();
    info.tripId = $("#flow_advance_reserve_pay_tripId").val();
    var data = JSON.stringify(info);
    $.ajax({
        type: "post",
        url: "/inside_payment/pay",
        contentType: "application/json",
        dataType: "text",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if(result == "true"){
                $("#flow_advanced_reserve_collect_order_id").val(info.orderId);
                location.hash="anchor_flow_advance_reserve_collect";
            }else{
                alert("Pay Fail. Reason Not Clear.Please check the order status before you try.");
            }
        },
        complete: function(){
            $("#flow_advance_reserve_pay_button").attr("disabled",false);
        }
    });
})



/**
 * Flow Preserve - Step 6 - Collect Your Ticket
 */

$("#flow_advanced_reserve_collect_button").click(function() {
    $("#flow_advanced_reserve_collect_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#flow_advanced_reserve_collect_order_id").val();
    var data = JSON.stringify(executeInfo);
    $.ajax({
        type: "post",
        url: "/execute/collected",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#advanced_reserve_execute_order_id").val(executeInfo.orderId);
                location.hash="anchor_flow_advance_reserve_execute";
            }else{
                alert(obj["message"]);
            }
        },
        complete: function(){
            $("#flow_advanced_reserve_collect_button").attr("disabled",false);
        }
    });
});;

/**
 * Flow Preserve - Step 7 - Enter Station
 */

$("#flow_advanced_reserve_execute_order_button").click(function() {
    $("#flow_advanced_reserve_execute_order_button").attr("disabled",true);
    var executeInfo = new Object();
    executeInfo.orderId = $("#advanced_reserve_execute_order_id").val();
    var data = JSON.stringify(executeInfo);
    $.ajax({
        type: "post",
        url: "/execute/execute",
        contentType: "application/json",
        dataType: "json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                alert(obj["message"]);
            }else{
                alert(obj["message"]);
            }
        },
        complete: function(){
            $("#flow_advanced_reserve_execute_order_button").attr("disabled",false);
        }
    });
});
