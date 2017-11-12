function onLoadBody(){
    //请求信息封装
    // var requestInfo = new Object();
    // requestInfo.orderId = getQueryString('orderId');
    // requestInfo.type = getQueryString('type');
    // var data = JSON.stringify(requestInfo);
    //
    // /* 获取url后的某一个query的值 */
    // function getQueryString( name ) {
    //     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    //     var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
    //     var context = "";
    //     if (r != null)
    //         context = r[2];
    //     reg = null;
    //     r = null;
    //     return context == null || context == "" || context == "undefined" ? "" : context;
    // }
    //
    // //发送请求
    // $.ajax({
    //     type: "post",
    //     url: "/getVoucher",
    //     contentType: "application/json",
    //     dataType: "json",
    //     data:data,
    //     success: function(result){
    //         var obj = result;
    //         alert(JSON.stringify(obj));
    //     },
    //     complete: function(){
    //
    //     }
    // });
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
    var num = GetRandomNum(1,1000);
    document.getElementById("voucher_id").innerText = "10000" + num;
    document.getElementById("order_id").innerText = request["orderId"];
    document.getElementById("travel_date").innerText = request["bought_date"];
    document.getElementById("passenger_name").innerText = request["passenger"];
    document.getElementById("train_number").innerText = request["train_number"];
    document.getElementById("seat_number").innerText = request["seat"];
    document.getElementById("start_station").innerText = request["from"];
    document.getElementById("dest_station").innerText = request["to"];
    document.getElementById("price").innerText = request["price"];
}

function GetRandomNum(Min,Max)
{
    var Range = Max - Min;
    var Rand = Math.random();
    return(Min + Math.round(Rand * Range));
}