/**
 * Created by lwh on 2017/11/16.
 */
/*
 * 显示管理员名字
 * */
// var loadBody = function () {
//     var username = sessionStorage.getItem("admin_name");
//     if (username == null) {
//         alert("Please login first!");
//         location.href = "login.html";
//     }
//     else {
//         document.getElementById("admin_name").innerHTML = username;
//     }
// };

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
            url: "http://api.jiemengshi.com/admin/feedback/list",
            withCredentials: true,
            data:{
                start: param.cur_page,
                limit: param.per_page
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
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
                alert("请重新登录");
                location.href = "../../admin.html";
            }
            else{
                alert("请求梦境列表失败");
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