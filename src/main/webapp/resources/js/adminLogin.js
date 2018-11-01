$(function(){
    $('#login_submit').click(function(){
        var username = $('#username').val();
        var password = $('#password').val();
        $.ajax({
            url: '/adminLogin?username=' + username + '&password='+password,
            type:'GET',
            contentType:false,
            processData:false,
            cache:false,
            success:function(data){
                if(data.success){
                    window.location.href = '/manageQuestions';
                } else{
                    alert(data.errMsg);
                }
            }
        })
    })
})