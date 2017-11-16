/**
 * Created by lwh on 2017/11/16.
 */
var controllerModule = angular.module("myApp", []);
controllerModule.controller("loginCtrl", function ($scope,$http) {
    $scope.login = function() {
        var account = $scope.username;
        var password = $scope.password;
        $http({
            method:"post",
            url: "http://api.jiemengshi.com/admin/user/login",
            withCredentials: true,
            data:{
                username: account,
                password: password
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            transformRequest: function ( data ) {
                var str = '';
                for( var i in data ) {
                    str += i + '=' + data[i] + '&';
                }
                return str.substring(0,str.length-1);
            }
        }).success(function(data, status, headers, config){
            if (data.code == 0) {
                sessionStorage.setItem("uid",data.data.id);
                sessionStorage.setItem("admin_name", data.data.username);
                sessionStorage.setItem("permissionLevel", data.data.permissionLevel);
                //$scope.decodeInfo(data.data);
                location.href = "../../admin.html";
            }else{
                alert("Wrong user name and password!");
            }
        })
    }

    $scope.decodeInfo = function (obj) {
        var des = "";
        for(var name in obj){
            des += name + ":" + obj[name] + ";";
        }
        alert(des);
    }
});