
/********************************************************************/
/********************Function For Ticket Execute Service*******************/

$("#execute_order_button").click(function() {
    var executeInfo = new Object();
    executeInfo.orderId = $("#execute_order_id").val();
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
                $("#execute_order_status").html(obj["message"]);
            }else{
                $("#execute_order_status").html(obj["message"]);
            }
        }
    });
});;

