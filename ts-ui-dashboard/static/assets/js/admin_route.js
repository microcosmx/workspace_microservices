/**
 * Created by lwh on 2017/11/16.
 */
/*
 * 显示管理员名字
 * */
var loadBody = function () {
    var username = sessionStorage.getItem("admin_name");
    if (username == null) {
        alert("Please login first!");
        location.href = "adminlogin.html";
    }
    else {
        document.getElementById("admin_name").innerHTML = username;
    }
};

/*
 * 登出
 * */
var logout = function () {
    sessionStorage.clear();
    location.href = "adminlogin.html";
}

/*
 * 将加载数据封装为一个服务
 * */
var app = angular.module('myApp', []);
app.factory('loadDataService', function ($http, $q) {

    var service = {};

    //获取并返回数据
    service.loadRecordList = function (param) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //返回的数据对象
        var information = new Object();

        $http({
            method: "get",
            url: "/adminroute/findAll/" + param.id,
            withCredentials: true,
        }).success(function (data, status, headers, config) {
            if (data.status) {
                information.orderRecords = data.orders;
                deferred.resolve(information);
            }
            else{
                alert("Request the order list fail!" + data.message);
            }
        });

        return promise;
    };

    return service;
});

/*
 * 加载列表
 * */
app.controller('indexCtrl', function ($scope, $http,$window,loadDataService) {
    var param = {};
    param.id = sessionStorage.getItem("admin_id");

    //刷新页面
    $scope.reloadRoute = function () {
        $window.location.reload();
    };

    //首次加载显示数据
    loadDataService.loadRecordList(param).then(function (result) {
        $scope.records = result.orderRecords;
        //$scope.decodeInfo(result.orderRecords[0]);
    });

    $scope.decodeInfo = function (obj) {
        var des = "";
        for(var name in obj){
            des += name + ":" + obj[name] + ";";
        }
        alert(des);
    }
    
    //Add new route
    $scope.addNewOrder = function () {
        $('#add_prompt').modal({
            relatedTarget: this,
            onConfirm: function(e) {
                $http({
                    method: "post",
                    url: "/adminroute/addRoute",
                    withCredentials: true,
                    data:{
                        loginid: sessionStorage.getItem("admin_id"),
                        route:{
                            stations: $scope.add_order_bought_date,
                            distances: $scope.add_order_travel_date,
                            startStationId: $scope.add_order_travel_time,
                            terminalStationId: $scope.add_order_account
                        }
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status) {
                        alert(data.message);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Add the route fail!" + data.message);
                    }
                });
            },
            onCancel: function(e) {
                alert('You have canceled the operation!');
            }
        });
    }
    
    //Update exist order
    $scope.updateOrder = function (record) {
        $scope.update_route_id = record.id;
        $scope.update_route_stations = record.stations;
        $scope.update_route_distances = record.distances;
        $scope.update_route_start_station = record.startStationId;
        $scope.update_route_terminal_station = record.terminalStationId;

        $('#update_prompt').modal({
            relatedTarget: this,
            onConfirm: function(e) {
                $http({
                    method: "post",
                    url: "/adminroute/updateRoute",
                    withCredentials: true,
                    data:{
                        loginid: sessionStorage.getItem("admin_id"),
                        order:{
                            id: $scope.update_route_id,
                            boughtDate: $scope.update_route_stations,
                            travelDate: $scope.update_route_distances,
                            travelTime: $scope.update_route_start_station,
                            accountId: $scope.update_route_terminal_station
                        }
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status) {
                        alert(data.message);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Update the route fail!" + data.message);
                    }
                });
            },
            onCancel: function(e) {
                alert('You have canceled the operation!');
            }
        });
    }

    //Delete order
    $scope.deleteOrder = function(orderId,trainNumber){
        $('#delete_confirm').modal({
            relatedTarget: this,
            onConfirm: function(options) {
                $http({
                    method: "post",
                    url: "/adminroute/deleteRoute",
                    withCredentials: true,
                    data: {
                        loginid: sessionStorage.getItem("admin_id"),
                        orderId: orderId,
                        trainNumber: trainNumber
                    }
                }).success(function (data, status, headers, config) {
                    if (data.status) {
                        alert(data.message);
                        $scope.reloadRoute();
                    }
                    else{
                        alert("Delete the route fail!" + data.message);
                    }
                });
            },
            // closeOnConfirm: false,
            onCancel: function() {
                alert('You have canceled the operation!');
            }
        });
    }
});