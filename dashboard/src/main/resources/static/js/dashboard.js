/**
 * TODO
 */

$("#post-request").bind("click", function(){
    $.ajax({
        url: 'accounts/demo', //http://10.141.212.21:12300/demo
        datatype: 'json',
        type: 'post',
        async: false,
        data: JSON.stringify({
            username: "name",
            password: "pass"
        }),
        success: function (data) {
        	console.log("ss");
        },
        error: function () {
            alert("Something went wrong. Please, try again");
        }
    });
});

