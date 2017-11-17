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
    requestInfo.type = getQueryString('type');
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
            alert(JSON.stringify(obj));
            // document.getElementById("voucher_id").innerText = "10000" + num;
            // document.getElementById("order_id").innerText = request["orderId"];
            // document.getElementById("travel_date").innerText = request["bought_date"];
            // document.getElementById("passenger_name").innerText = request["passenger"];
            // document.getElementById("train_number").innerText = request["train_number"];
            // document.getElementById("seat_number").innerText = request["seat"];
            // document.getElementById("start_station").innerText = request["from"];
            // document.getElementById("dest_station").innerText = request["to"];
            // document.getElementById("price").innerText = request["price"];
        },
        complete: function(){

        }
    });
}
