$(function(){
    $('#register_submit').click(function(){
        var data = {}
        data.username = $('#userId').val();
        data.password = $('#password').val();
        data.password1 = $('#password1').val();

        $.ajax({
            url:'/insticator/handleUserRegister',
            type:'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    alert("finish creating account");
                    window.location.href = '/insticator/userLogin';
                } else {
                    alert(data.errMsg);
                }
            }
        })
    })
})