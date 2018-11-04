$(function(){
    $('#login_submit').click(function(){
        var username = $('#username').val();
        var password = $('#password').val();
        $.ajax({
            url: '/insticator/adminLogin?username=' + username + '&password='+password,
            type:'GET',
            contentType:false,
            processData:false,
            cache:false,
            success:function(data){
                if(data.success){
                		alert("success");
                    window.location.href = '/insticator/manageQuestions';
                } else{
                    alert(data.errMsg);
                }
            }
        })
    })

})
