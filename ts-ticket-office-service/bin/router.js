var express = require('express');
var router = express.Router();

var db = require('./db');

router.get('/', function(req, res, next) {
    res.send("welcome to ts-ticket-office-service");
});

// router.get('/init', function(req, res, next) {
//     db.initMongo(function(result){
//         res.set({'Content-Type':'text/json','Encodeing':'utf8'});
//         result = JSON.stringify(result);
//         console.log("initResult=" + result);
//         res.end(result);
//     });
// });

router.get('/getAll', function(req, res, next) {
    db.getAll(function(result){
        res.set({'Content-Type':'text/json','Encodeing':'utf8'});
        result = JSON.stringify(result);
        console.log("getAll=" + result);
        res.end(result);
    });
});

router.get('/getOfficeByRegion', function(req, res, next) {


});

module.exports = router;
