//----------------Switch from many tags
//For toggle pages
$("#microservice_page").click(function(){
    $("#microservices").css('display','block');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','none');
});

$("#flow_one_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','block');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','none');
});

$("#flow_two_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','block');
    $("#flow_three").css('display','none');
});

$("#flow_three_page").click(function(){
    $("#microservices").css('display','none');
    $("#flow_one").css('display','none');
    $("#flow_two").css('display','none');
    $("#flow_three").css('display','block');
});

/********************************************************************/
/********************Function For Order Service*******************/

$("#refresh_order_button").click(function(){
    var typeCheckBox = $(".order_type");
    if(typeCheckBox[0].checked && typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/order/findAll");
        refresh_order("/orderOther/findAll");
    }else if(typeCheckBox[0].checked && !typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/order/findAll");
    }else if(!typeCheckBox[0].checked && typeCheckBox[1].checked){
        $("#all_order_table").find("tbody").html("");
        refresh_order("/orderOther/findAll");
    }else{
        alert("Not Select The Order Type.");
    }
});

function refresh_order(path){
    $.ajax({
        type: "get",
        url: path,
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result["orders"];
            for(var i = 0,l = obj.length ; i < l ; i++){
                $("#all_order_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='all_order_id noshow_component'>" + obj[i]["id"] + "</td>" +
                    "<td>" + obj[i]["id"] + "</td>" +
                    "<td class='all_order_trainNum'>" + obj[i]["trainNumber"] + "</td>" +
                    "<td>" + obj[i]["from"] + "</td>" +
                    "<td>" + obj[i]["to"] + "</td>" +
                    "<td>" + mergeTwoDate(obj[i]["travelDate"],obj[i]["travelTime"]) + "</td>" +
                    "<td>" + convertNumberToHtmlOrderStatus(obj[i]["status"]) + "</td>" +
                    "<td>" + "<button class='all_order_update btn btn-primary'>Update</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToAllOrderTable();
            alert("Success.");
        }
    });
}

function addListenerToAllOrderTable(){
    var allOrderUpdateBtnSet = $(".all_order_update");
    for(var i = 0;i < allOrderUpdateBtnSet.length;i++){
        allOrderUpdateBtnSet[i].onclick = function(){
            var updateInfo = new Object();
            updateInfo.orderId = $(this).parents("tr").find(".all_order_id").text();
            updateInfo.status = $(this).parents("tr").find(".all_order_status").val();
            var data = JSON.stringify(updateInfo);
            var path = "";
            var tripType = $(this).parents("tr").find(".all_order_trainNum").text().charAt(0);
            if(tripType == 'G' || tripType == 'D'){
                path = "/order/modifyOrderStatus";
            }else{
                path = "/orderOther/modifyOrderStatus";
            }
            $.ajax({
                type: "post",
                url: path,
                contentType: "application/json",
                dataType: "json",
                data:data,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    if(result["status"] == true){
                        refresh_order();
                        alert("Success.");
                    }else{
                        alert(result["message"]);
                    }
                }
            });
        }
    }
}

function convertNumberToHtmlOrderStatus(number){
    var result = "<select class='all_order_status form-control' name='documentType'>";
    if(number == 0){
        result +=
            "<option selected='selected' value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 1){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option selected='selected' value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 2){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option selected='selected' value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 3){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option selected='selected' value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 4){
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option selected='selected' value='4'>Cancel</option>" +
            "<option value='5'>Refunded</option>";
    }else if(number == 5) {
        result +=
            "<option value='0'>Not Paid</option>" +
            "<option value='1'>Paid & Not Collected</option>" +
            "<option value='2'>Collected</option>" +
            "<option value='3'>Cancel & Rebook</option>" +
            "<option value='4'>Cance</option>" +
            "<option selected='selected' value='5'>Refunded</option>";
    }
    result += "</select>";
    return result;
}

// function addListenerToPaymentTable(){
//     var ticketPaymentButtonSet = $(".ticket_payment_button");
//     for(var i = 0;i < ticketPaymentButtonSet.length;i++){
//         ticketPaymentButtonSet[i].onclick = function(){
//             var tripId = $(this).parents("tr").find(".booking_tripId").text();
//             var loginToken = $("#user_login_token").html();
//             var accountId = $("#user_login_id").html();
//             var contactsId = "";
//             var radios = $(".booking_contacts_select");
//             for (var j = 0; j < radios.length; j++) {
//                 if (radios[j].checked) {
//                     contactsId = $(".booking_contacts_contactsId").eq(j).text();
//                 }
//             }
//             var orderTicketInfo = new Object();
//             orderTicketInfo.contactsId = contactsId;
//             orderTicketInfo.tripId = tripId;
//             orderTicketInfo.seatType = seatType;
//             orderTicketInfo.loginToken = loginToken;
//             orderTicketInfo.accountId = accountId;
//             orderTicketInfo.date = date;
//             orderTicketInfo.from = from;
//             orderTicketInfo.to = to;
//             var orderTicketsData = JSON.stringify(orderTicketInfo);
//             $.ajax({
//                 type: "post",
//                 url: "/inside_payment/pay",
//                 contentType: "application/json",
//                 dataType: "json",
//                 data: orderTicketsData,
//                 xhrFields: {
//                     withCredentials: true
//                 },
//                 success: function (result) {
//
//                 }
//             })
//         }
//     }
// }

/********************************************************************/
/***************************Some Basic Function**********************/

function convertNumberToDocumentType(code) {
    var str = "";
    if(code == 0){
        str = "null";
    }else if(code == 1){
        str = "ID Card";
    }else if(code == 2){
        str = "Passport";
    }else{
        str = "other";
    }
    return str;
}

function convertNumberToSeatClass(code){
    var str = "";
    if(code == 2){
        str = "First Class Seat";
    }else if(code == 3){
        str = "Second Class Seat";
    }else{
        str = "other";
    }
    return str;
}



function convertNumberToTimeString(timeNumber) {
    var str = new Date(timeNumber);
    var newStr = str.getHours() + ":" + str.getMinutes() + "";
    return newStr;
}

function mergeTwoDate(dateOne,dateTwo) {
    var one = new Date(dateOne);
    var two = new Date(dateTwo);
    // var year = one.getFullYear();
    // var month = one.getMonth();
    // var day = one.getDay();
    // var hour = two.getHours();
    // var minute = two.getMinutes();
    // ..var datetime = year + ":" + month + ":" + day + "  " + hour + ":" + minute;
    return one.toDateString() + " " + two.toTimeString();
}

function convertStringToTime(string){
    var date = new Date();
    var s = string.toString();
    var index = s.indexOf(':');
    var hour = s.substring(0,index).valueOf();
    var minute = s.substring(index+1,s.length).valueOf();
    date.setHours(hour);
    date.setMinutes(minute);
    return date;
}
