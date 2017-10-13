/**
 * Created by dingding on 2017/10/13.
 */
var MongoClient = require('mongodb').MongoClient;
var fs = require('fs');
var path = require('path');
// var DB_CONN_STR = 'mongodb://localhost:27017/test';
var DB_CONN_STR = 'mongodb://ts-ticket-office-mongo/ticket-office';

var initData = function(db, callback){
    var collection =  db.collection('office');
    if(collection.find()){
        collection.remove({});
    }
    // 读取已存在的数据
    fs.readFile(path.join(__dirname, "./office.json"), 'utf8', function (err, data) {
        data = JSON.parse( data );
        collection.insert(data, function(err, result){
            if(err){
                console.log('Error: ' + err);
                return;
            }
            callback(result);
        });
    });
};

var getAllOffices = function(db, callback){
    var collection =  db.collection('office');
    collection.find().toArray(function(err, result){
        if(err){
            console.log("Error:" + err);
            return;
        }
        callback(result);
    });
};

exports.initMongo = function(callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("initMongo连接上数据库啦！");
        initData(db, function(result){
            db.close();
            callback(result);
        });
    })
};

exports.getAll = function(callback){
    MongoClient.connect(DB_CONN_STR, function(err, db){
        console.log("getAll连接上数据库啦！");
        getAllOffices(db, function(result){
            db.close();
            callback(result);
        });
    })
};
