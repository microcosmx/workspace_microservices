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
        location.href = "login.html";
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
    location.href = "login.html";
}

/*
 * 将加载数据封装为一个服务
 * */
var app = angular.module('myApp', []);
app.factory('loadDataService', function ($http, $q) {

    var service = {};

    //获取并返回数据
    service.loadDreamList = function (param) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //返回的数据对象
        var information = new Object();

        $http({
            method: "post",
            url: "/adminorder/getAllOrders",
            withCredentials: true,
            data:{
                id: param.id
            },
            headers: {
                'Content-Type': 'application/json'
            },
            transformRequest: function (data) {
                var str = '';
                for( var i in data ) {
                    str += i + '=' + data[i] + '&';
                }
                return str.substring(0,str.length-1);
            }
        }).success(function (data, status, headers, config) {
            if (data.code == 0) {
                information.dreams = data.data;
                information.total = data.total;
                deferred.resolve(information);
            }
            else if(data.code == 20005){
                alert("The session has expired, please log in again!");
                location.href = "../../admin.html";
            }
            else{
                alert("Request the order list fail!");
                alert(data.code + data.message);
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

});