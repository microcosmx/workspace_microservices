function onLoadBody(){
    //请求信息封装
    var url = location.search;
    var request = new Object();
    if(url.indexOf("?") != -1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for(var i = 0;i<strs.length;i++)
        {
            request[strs[i ].split("=")[0]]=unescape(strs[ i].split("=")[1]);
        }
    }
    var requestInfo = new Object();
    requestInfo.orderId = request["orderId"];
    var tripType = request["train_number"].charAt(0);
    if(tripType == 'G' || tripType == 'D'){
        requestInfo.type = 1;
    }else{
        requestInfo.type = 0;
    }
    var data = JSON.stringify(requestInfo);

    //发送请求
    $.ajax({
        type: "post",
        url: "/getVoucher",
        contentType: "application/json",
        dataType: "json",
        data:data,
        success: function(result){
            var obj = result;
            obj = JSON.stringify(obj);
            alert(obj);
            document.getElementById("voucher_id").innerText = "10000" + obj["voucher_id"];
            document.getElementById("order_id").innerText = obj["order_id"];
            document.getElementById("travel_date").innerText = obj["travelDate"];;
            document.getElementById("passenger_name").innerText = obj["contactName"];;
            document.getElementById("train_number").innerText = obj["trainNumber"];;
            document.getElementById("seat_number").innerText = obj["seatNumber"];;
            document.getElementById("start_station").innerText = obj["startStation"];;
            document.getElementById("dest_station").innerText = obj["destStation"];;
            document.getElementById("price").innerText = obj["price"];;
        },
        complete: function(){

        }
    });
}
