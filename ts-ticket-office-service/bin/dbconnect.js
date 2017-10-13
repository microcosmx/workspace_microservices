/**
 * Created by dingding on 2017/10/13.
 */
var MongoClient = require('mongodb').MongoClient;
var DB_CONN_STR = 'mongodb://localhost:27017/test';

var insertData = function(db, callback){
    var collection =  db.collection('site');
    var data = [
        {
            "name" : "百度",
            "url" : "www.baidu.com"
        }
    ];
    collection.insert(data, function(err, result){
        if(err){
            console.log('Error: ' + err);
            return;
        }
        callback(result);
    });
};

MongoClient.connect(DB_CONN_STR, function(err, db){
    console.log("连接成功！");
    insertData(db, function(result){
        console.log(result);
        db.close();
    });
});
