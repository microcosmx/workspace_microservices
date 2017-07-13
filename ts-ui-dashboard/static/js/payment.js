/***********************************************************************/
/********************Function For Payment Service***********************/
/********************Used For Payment Service Single Microservice Test**/

$("#payment_query_button").click(function(){
    $.ajax({
        type: "get",
        url: "/payment/query",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            var size = result.length;
            $("#query_payment_list_table").find("tbody").html("");
            // $("#payment_result_table").css('height','200px');
            for (var i = 0; i < size; i++) {
                $("#query_payment_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + result[i]["id"] + "</td>" +
                    "<td>" + result[i]["orderId"] + "</td>" +
                    "<td>" + result[i]["userId"] + "</td>" +
                    "<td>" + result[i]["price"] + "</td>" +
                    "</tr>"
                );
            }
        }
    });
});

$("#payment_pay_button").click(function(){
    var info = new Object();
    info.orderId = $("#payment_orderId").val();
    info.price = $("#payment_price").val();
    info.userId = $("#payment_userId").val();
    var data = JSON.stringify(info);
    $.ajax({
        type: "post",
        url: "/payment/pay",
        contentType: "application/json",
        data:data,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            $("#payment_result").html(result.toString());
        }
    });
});