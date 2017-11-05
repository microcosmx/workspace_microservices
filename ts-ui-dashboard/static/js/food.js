
/**********************************************************************/
/********************Function For Food Service************************/
var foodStoreListMap = null;

$("#query_food_button").click(function(){
    $("#query_food_button").attr("disabled",true);

    var data = new Object();
    data.date = $('#food_date').val();
    data.startStation = $('#food_start_station').val();
    data.endStation = $('#food_end_station').val();
    data.tripId = $('#food_trip_id').val();

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
            // console.log(result);

            var trainFoodList = result.trainFoodList;
            console.log("trainFoodList:" + trainFoodList);

            $("#train_food_list_table").find("tbody").html("");
            for(var i; trainFoodList && i < trainFoodList.length; i++){
                $("#train_food_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td>" + trainFoodList["tripId"] + "</td>" +
                    "<td>" + trainFoodList.foodList[i]['foodName'] + "</td>" +
                    "<td>" + trainFoodList.foodList[i]['price'] + "</td>" +
                    "</tr>"
                );
            }

            var stationIds = new Array();
            foodStoreListMap = result.foodStoreListMap;
            for(var key in foodStoreListMap){
                stationIds.push(key);
                var fslist = foodStoreListMap[key];
                showFoodStores(fslist);
            }
            var stationSelect = document.getElementById ("food_station_select");
            var opt1 = document.createElement ("option");
            opt1.value = 0;
            opt1.innerText = "-- --";
            stationSelect.appendChild(opt1);
            for(var k=0; k < stationIds.length; k++){
                var opt2 = document.createElement ("option");
                opt2.value = k + 1;
                opt2.innerText = stationIds[k];
                stationSelect.appendChild (opt2);
            }

        },
        complete: function(){
            $("#query_food_button").attr("disabled",false);
        }
    });

});

function changeFoodStation(){
    var station = $('#food_station_select').find("option:selected").text()
    console.log("changeFoodStation");
    var  foodStoreList = foodStoreListMap[station];
    console.log("foodStoreList" + foodStoreList);
    showFoodStores(foodStoreList);

}

function showFoodStores(list){
    console.log("showFoodStores" + list);
    $("#food_stores_of_station_list").find("tbody").html("");
    for(var j = 0; j < list.length; j++){
        $("#food_stores_of_station_list").find("tbody").append(
            "<tr>" +
            "<td>" + j + "</td>" +
            "<td>" + list[j]["storeName"] + "</td>" +
            "<td>" + list[j]['telephone'] + "</td>" +
            "<td>" + list[j]['businessTime']  + "</td>" +
            "<td>" + list[j]['deliveryFee']  + "</td>" +
            "<td>" + "<button class='show_store_foods btn btn-primary' onclick='showFoods(" + list[j]['foodList'] + ")'>Show Foods</button>" + "</td>" +
            "</tr>"
        );
    }

}

function showFoods(foodList){
    console.log("foodlist:" + foodList);
    $("#food_of_store").find("tbody").html("");
   for(var j = 0; j < foodList.length; j++){
       $("#food_of_store").find("tbody").append(
           "<tr>" +
           "<td>" + j + "</td>" +
           "<td>" + foodList[j]["foodName"] + "</td>" +
           "<td>" + foodList[j]['price'] + "</td>" +
           "</tr>"
       );
   }
}
