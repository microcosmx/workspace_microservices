function onLoadBody(){
    //请求信息封装
    var requestInfo = new Object();
    requestInfo.orderId = getQueryString('orderId');
    requestInfo.type = getQueryString('type');
    var data = JSON.stringify(requestInfo);

    /* 获取url后的某一个query的值 */
    function getQueryString( name ) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
        var context = "";
        if (r != null)
            context = r[2];
        reg = null;
        r = null;
        return context == null || context == "" || context == "undefined" ? "" : context;
    }

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
        },
        complete: function(){

        }
    });
}