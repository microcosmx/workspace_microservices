/**
 * Created by fdse-jichao on 2017/7/4.
 */

$("#execute_order_button").click(function() {
    var executeInfo = new Object();
    executeInfo.orderId = $("#single_collect_order_id").val();
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
                $("#single_collect_order_status").html(obj["message"]);
            }else{
                $("#single_collect_order_status").html(obj["message"]);
            }
        }
    });
});;
