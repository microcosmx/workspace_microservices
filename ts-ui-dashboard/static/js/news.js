/**
 * Created by fdse-jichao on 2017/11/12.
 */
$("#news_button").click(function(){
    $.ajax({
        type: "get",
        url: "/news-service/news",
        contentType: "application/json",
        dataType: "text",
        xhrFields: {
            withCredentials: true
        },
        success: function(result){
            alert(result)
        }
    });
});
