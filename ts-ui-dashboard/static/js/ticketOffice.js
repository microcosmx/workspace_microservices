
/**********************************************************************/
/********************Function For Ticket Office Service************************/
var regionList = [];
// var selectProvince = 0, selectCity = 0, selectRegion = 0;

$(function () {
    getRegionList();
});

function changeProvince(){
    // console.log("get into the province change");
    var city = document.getElementById ("office_city");
    // selectProvince = $('select#office_province').val();
    $("#office_city").empty();
    $("#office_region").empty();
    $("#office_list_table").find("tbody").html("");
    for (var i = 0, l = regionList.length; i < l; i++) {
        // console.log("get into the province loop:" + $('#office_province').find("option:selected").text());
        if(regionList[i]['province'] == $('#office_province').find("option:selected").text()){
            var opt1 = document.createElement ("option");
            opt1.value = 0;
            opt1.innerText = "-- --";
            city.appendChild(opt1);
            for(var j = 0, k = regionList[i]['cities'].length; j < k; j++){
                var opt11 = document.createElement ("option");
                opt11.value = j + 1;
                opt11.innerText = regionList[i]['cities'][j]['city'];
                city.appendChild (opt11);
                console.log(regionList[i]['cities'][j]['city']);
            }
            city.value = 0;
            // selectCity = 0;
        }
    }
}

function changeCity(){
    var region = document.getElementById ("office_region");
    // selectCity = $('select#office_city').val();
    $("#office_region").empty();
    $("#office_list_table").find("tbody").html("");
    for (var i = 0, l = regionList.length; i < l; i++) {
        if(regionList[i]['province'] == $("#office_province").find("option:selected").text()){
            for(var m = 0; m < regionList[i]['cities'].length; m++){
                if(regionList[i]['cities'][m]['city'] == $('#office_city').find("option:selected").text()){
                    var opt2 = document.createElement ("option");
                    opt2.value = 0;
                    opt2.innerText = "-- --";
                    region.appendChild(opt2);
                    for(var j = 0, k = regionList[i]['cities'][m]['regions'].length; j < k; j++){
                        var opt22 = document.createElement ("option");
                        opt22.value = j + 1;
                        opt22.innerText = regionList[i]['cities'][m]['regions'][j]['region'];
                        // console.log(regionList[i]['cities'][m]['regions'][j]['region']);
                        region.appendChild (opt22);
                    }
                    region.value = 0;
                    // selectRegion = 0;
                }
            }
        }
    }
}

function changeRegion(){
    $("#office_list_table").find("tbody").html("");
}

function getRegionList(){
    $.ajax({
        type: "get",
        url: "/office/getRegionList",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            regionList = result;
            var province = document.getElementById ("office_province");


            var opt0 = document.createElement ("option");
            opt0.value = 0;
            opt0.innerText = "-- --";
            province.appendChild (opt0);
            for (var i = 0, l = regionList.length; i < l; i++) {
                var opt00 = document.createElement ("option");
                opt00.value = i + 1;
                opt00.innerText = regionList[i]["province"];
                province.appendChild (opt00);
            }
            province.value = 0;

        },
        complete: function(){

        }
    });
}


$("#query_office_button").click(function(){
    $("#query_office_button").attr("disabled",true);
    $("#assurance_list_status").text("false");
    $.ajax({
        type: "get",
        url: "/assurance/findAll",
        contentType: "application/json",
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (obj) {
            var result = obj["assurances"];
            var size = result.length;
            $("#assurance_list_table").find("tbody").html("");
            for (var i = 0; i < size; i++) {
                $("#assurance_list_table").find("tbody").append(
                    "<tr>" +
                    "<td>" + i + "</td>" +
                    "<td class='all_assurance_id '>" + result[i]["id"] + "</td>" +
                    "<td>" +
                    "<input class='all_assurance_order_id' value='" + result[i]["orderId"] + "'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='all_assurance_type_index form-control' value='" + result[i]["typeIndex"] + "'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='all_assurance_type_name' value='" + result[i]["typeName"] + "'>" +
                    "</td>" +
                    "<td>" +
                    "<input class='all_assurance_type_price' value='" + result[i]["typePrice"] + "'>" +
                    "</td>" +
                    "<td>" + "<button class='all_assurance_update btn btn-primary'>Update</button>" + "</td>" +
                    "</tr>"
                );
            }
            addListenerToAllAssuranceTable();
            $("#office_list_status").text("true");
        },
        complete: function(){
            $("#query_office_button").attr("disabled",false);
        }
    });
});

function addListenerToAllAssuranceTable(){
    var allAssuranceUpdateBtnSet = $(".all_assurance_update");
    for(var i = 0;i < allAssuranceUpdateBtnSet.length;i++){
        allAssuranceUpdateBtnSet[i].onclick = function(){

            var assurance = new Object();
            assurance.assuranceId = $(this).parents("tr").find(".all_assurance_id").text();;
            assurance.orderId = $(this).parents("tr").find(".all_assurance_order_id").val();
            assurance.typeIndex = $(this).parents("tr").find(".all_assurance_type_index").val();

            var assuranceData = JSON.stringify(assurance);
            alert(assuranceData);

            $.ajax({
                type: "post",
                url: "/assurance/modifyAssurance",
                contentType: "application/json",
                dataType: "json",
                data:assuranceData,
                xhrFields: {
                    withCredentials: true
                },
                success: function(result){
                    var obj = result;
                    if(obj["status"] == true){
                        alert("Success");
                    }else{
                        alert("Update Fail");
                    }
                },
                complete: function(){
                }
            });

        }
    }
}

$("#create_assurance_button").click(function(){

    // var orderId = $("#assurance_order_id").val();
    var typeIndex = $("#assurance_type_index").val();

    var newAssurance = new Object();
    newAssurance.id = "";
    newAssurance.orderId = guid();
    newAssurance.typeIndex = typeIndex;

    var assuranceData = JSON.stringify(newAssurance);

    $("#create_assurance_button").attr("disabled",true);
    $("#create_assurance_status").html("false");

    $.ajax({
        type: "post",
        url: "/assurance/create",
        contentType: "application/json",
        dataType: "json",
        data: assuranceData,
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            var obj = result;
            if(obj["status"] == true){
                $("#create_assurance_create_message").html(obj["message"]);
            }else{
                $("#create_assurance_create_message").html(obj["message"]);
            }
            $("#create_assurance_status").html("true");
        },
        complete: function(){
            $("#create_assurance_button").attr("disabled",false);
        }
    });

});

function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}