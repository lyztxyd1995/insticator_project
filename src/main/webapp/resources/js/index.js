$(function(){
    var user = {}
    function getUser() {
        var url = '/getUser';
        $.ajax({
            url:url,
            type:"get",
            success:function(data){
                if(!data.success){
                    alert("No user information");
                    window.location.href = '/userLogin';
                } else {
                    $('#user').html('Hello, ' + data.user.username);
                    $('#point').html('Your current point is: ' + data.user.points);
                    user = data.user;
                }
            }
        })
    }
    var question = {}
    var type = ''
    var matricInputCount = 0;
    function getQuestion(){
        var url = '/getUserQuestion';
        $.ajax({
            url:url,
            type:"GET",
            success:function(data){
                if (data.success) {
                    question = data.question;
                    $('#question_content').html(question.content);
                    type = question.questionType.toLowerCase();
                    if (type === 'matric') {
                        addMatric();
                    }
                    else if(type === 'checkbox') {
                        addCheckBox();
                    }
                    else if(type === 'trivial') {
                        type = 'trivia';
                        addTrivia();
                    }
                    else if(type === 'poll') {
                        addPoll();
                    }
                } else {
                    alert(data.errMsg);
                    var main = $('#main');
                    main.html('');
                }
            }
        })
    }

    getUser();
    getQuestion();

    function addTrivia(){
        var main = $('#main');
        main.html('');
        var html = '<div>';
        for (var i = 0; i < question.choices.length; i++) {
            if (question.choices[i].answer) {
                html += '<input type="radio" name="Q" isAnswer="true">'+'&nbsp;&nbsp;' + question.choices[i].content+'<br>';
            } else {
                html += '<input type="radio" name="Q" isAnswer="false">'+'&nbsp;&nbsp;' + question.choices[i].content+'<br>';
            }
        }
        html += '</div>';
        main.html(html);
    }
    function addPoll(){
        var main = $('#main');
        main.html('');
        var html = '<div>';
        for (var i = 0; i < question.choices.length; i++) {
            html += '<input type="radio" name="Q">'+'&nbsp;&nbsp;' + question.choices[i].content+'<br>';
        }
        html += '</div>';
        main.html(html);
    }
    function addCheckBox(){
        var main = $('#main');
        main.html('');
        var html = '<div>';
        for (var i = 0; i < question.choices.length; i++) {
            html += '<input type="checkbox" name="Q">'+'&nbsp;&nbsp;' + question.choices[i].content+'<br>';
        }
        html += '</div>';
        main.html(html);
    }
    function addMatric(){
        var main = $('#main');
        main.html('');
        var rowName = question.matricItems[0].itemName;
        var colName = question.matricItems[1].itemName;
        var midName = question.matricItems[2].itemName;
        var html = '<table class="table"><thead>'+
            '<tr>'+
            '<th scope="col">' + colName +'|' + midName + '|' + rowName + '</th>';
        for (var i = 0; i < question.matricItems[0].defaultChoices.length; i++) {
            html += '<th scope="col">' + question.matricItems[0].defaultChoices[i] +'</th>'
        }
        html += '</tr></thead><tbody>';
        for (var i = 0; i < question.matricItems[1].defaultChoices.length; i++) {
            html += '<tr><th scope="row">' + question.matricItems[1].defaultChoices[i]+ '</th>';
            for (var j = 0; j < question.matricItems[0].defaultChoices.length; j++) {
                html += '<td><input type="text" id="matric_input'+matricInputCount+'"></td>';
                matricInputCount += 1;
            }
            html += '</tr>';
        }
        html += '</tbody></table>';
        main.html(html);
    }

    $(document).on("click", '#submit', function(){
        if ($('#main').html() === '') {
            alert("You've asked all the questions");
            return;
        }
        if (type === 'matric') {
            var count = 0;
            for (var i = 0; i < matricInputCount; i++) {
                if ($('#matric_input' + i).val() !== '') {
                    count += 1;
                }
            }
            if (count > 1) {
                alert('you could only select one option');
                return;
            }
            if (count == 0) {
                alert('you need select one option');
                return;
            }
            addUserPoints();
        }
        else if (type == 'trivia') {
            var radio = $("input[name='Q']:checked");
            if (radio.attr('isAnswer') === 'true') {
                addUserPoints();
            } else {
                userAddRecord();
            }
        }
        else {
            addUserPoints();
        }
        matricInputCount = 0;
    })

    function addUserPoints(){
        var data = {}
        data.username = user.username;
        data.points = user.points;
        $.ajax({
            type:"POST",
            url: "/userAddPoints",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    userAddRecord();
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }

    function userAddRecord(){
        var data = {}
        data.questionId = question.questionId;
        data.username = user.username;
        $.ajax({
            type:"POST",
            url: "/userAddRecord",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    updateUser();
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }

    function updateUser(){
        var url = '/updateUser?username=' + user.username;
        $.ajax({
            url:url,
            type:"get",
            success:function(data){
                if(!data.success){
                    alert("No user information");
                    window.location.href = '/userLogin';
                } else {
                    getQuestion();
                    $('#user').html('Hello, ' + data.user.username);
                    $('#point').html('Your current point is: ' + data.user.points);
                    user = data.user;
                }
            }
        })
    }
})