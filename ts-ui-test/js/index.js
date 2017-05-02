$(function(){
    $('form').ajaxForm({
        success: function(responseText){
            alert(responseText);
        }
    });
});