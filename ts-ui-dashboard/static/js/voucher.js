function onLoadBody(){
    //请求信息封装
    var requestInfo = new Object();
    requestInfo.orderId = 1;
    requestInfo.type = 0;
    var data = JSON.stringify(requestInfo);

    $.ajax({
        type: "post",
        url: "/getVoucher",
        contentType: "application/json",
        dataType: "json",
        data:data,
        success: function(result){
            var obj = result;
            alert(JSON.stringify(obj));
        },
        complete: function(){

        }
    });
}