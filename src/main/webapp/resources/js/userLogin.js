$(function(){
    $('#login_submit').click(function(){
        var username = $('#userId').val();
        var password = $('#password').val();
        $.ajax({
            url: '/handleUserLogin?username=' + username + '&password='+password,
            type:'GET',
            success:function(data){
                if(data.success){
                    window.location.href = '/index';
                } else{
                    alert(data.errMsg);
                }
            }
        })
    })
})