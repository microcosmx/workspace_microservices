var app = angular.module("myApp",[]);

app.factory('loadDataService', function ($http, $q) {

    var service = {};

    //获取并返回数据
    service.loadRecordList = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $http({
            method: "get",
            url: "http://localhost:5001/getFileTree",
            withCredentials: true,
        }).success(function (data, status, headers, config) {
            if (data) {
                deferred.resolve(data);
            }
            else{
                alert("Request the order list fail!" + data.message);
            }
        });

        return promise;
    };

    return service;
});

app.controller('indexCtrl', function ($scope, $http,$window,loadDataService) {

    //刷新页面
    $scope.reloadRoute = function () {
        $window.location.reload();
    };

    //首次加载显示数据
    loadDataService.loadRecordList().then(function (result) {
        // console.log(JSON.stringify(result));
        $('#fileTree').tree({
            dataSource:function(options, callback) {
                // 模拟异步加载
                //options.products一定要！不然会无限循环套用
                callback({data: options.products || result});
            },
            multiSelect: false,
            cacheItems: true,
            folderSelect: false
        });
    });

    $('#fileTree').on('selected.tree.amui', function (event, data) {
        console.log(data);
        // console.log(event);
        // alert( data.target.title);
        $scope.testName = data.target.title;

    });

    $scope.startTest = function(){
        $http({
            method: "post",
            url: "http://localhost:5001/test",
            data:{testString:"SimpleTest"},
            withCredentials: true
        }).success(function (data, status, headers, config) {
            console.log(data);
            $scope.results = data;
        });
    }

    // $scope.decodeInfo = function (obj) {
    //     var des = "";
    //     for(var name in obj){
    //         des += name + ":" + obj[name] + ";";
    //     }
    //     alert(des);
    // };

    //Add new order
    // $scope.addNewOrder = function () {
    //     $('#add_prompt').modal({
    //         relatedTarget: this,
    //         onConfirm: function(e) {
    //             $http({
    //                 method: "post",
    //                 url: "/adminorder/addOrder",
    //                 withCredentials: true,
    //                 data:{
    //                     loginid: sessionStorage.getItem("admin_id"),
    //                     order:{
    //                         boughtDate: $scope.add_order_bought_date,
    //                         travelDate: $scope.add_order_travel_date,
    //                         travelTime: $scope.add_order_travel_time,
    //                         accountId: $scope.add_order_account,
    //                         contactsName: $scope.add_order_passenger,
    //                         documentType: $scope.add_order_document_type,
    //                         contactsDocumentNumber: $scope.add_order_document_number,
    //                         trainNumber: $scope.add_order_train_number,
    //                         coachNumber: $scope.add_order_coach_number,
    //                         seatClass: $scope.add_order_seat_class,
    //                         seatNumber: $scope.add_order_seat_number,
    //                         from: $scope.add_order_from,
    //                         to: $scope.add_order_to,
    //                         status: $scope.add_order_status,
    //                         price: $scope.add_order_price
    //                     }
    //                 }
    //             }).success(function (data, status, headers, config) {
    //                 if (data.status) {
    //                     alert(data.message);
    //                     $scope.reloadRoute();
    //                 }
    //                 else{
    //                     alert("Request the order list fail!" + data.message);
    //                 }
    //             });
    //         },
    //         onCancel: function(e) {
    //             alert('You have canceled the operation!');
    //         }
    //     });
    // }


});