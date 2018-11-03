$(function(){
    var CHARACTER = ['A','B','C','D','E','F','G','H','I','J']
    function getAdmin() {
        var url = '/getAdmin';
        $.ajax({
            url:url,
            type:"get",
            success:function(data){
                if(!data.success){
                    alert("No administrator info");
                    window.location.href = '/admin';
                }
            }
        })
    }
    //getAdmin();
    getTrivia();

    $('#trivia_btn').click(function(){
        getTrivia();
        });
    $('#poll_btn').click(function(){
        getPoll();
    })
    $('#checkbox_btn').click(function(){
        getCheckBox();
    })
    $('#matric_btn').click(function(){
        getMatric();
    })
    function getTrivia(){
        var rows = $('#table_body');
        rows.html('');
        $.ajax({
            url:"/getQuestionByType?type=trivia",
            type:"get",
            dataType:"json",
            success: function (data) {
                if (data.success) {
                    var html = '';
                    for (var i = 0; i < data.questionList.length; i++) {
                        var question = data.questionList[i];
                        var tempHtml = '<tr><td>' + question.content + '</td><td>'
                            + question.questionType + '</td><td>';
                        if (question.choices != null) {
                            for (var j = 0; j < question.choices.length; j++) {
                                var choice = question.choices[j];
                                if (choice.answer) {
                                    tempHtml += '<span class="right_answer">' + CHARACTER[j]+ '.' + choice.content + '</span>&nbsp&nbsp'
                                } else {
                                    tempHtml += '<span>'+ CHARACTER[j]+ '.' + choice.content + '</span>&nbsp&nbsp'
                                }
                            }
                         }
                        tempHtml += '</td><td>'
                            + '<a href="editQuestion?id=' + question.questionId + '">Edit</a></td>' +
                            '<td><a href="deleteQuestion?id=' + question.questionId + '">Delete</a></td></tr>';
                        html += tempHtml;
                    }
                    rows.html(html);
                } else {
                    alert(data.errMsg);
                }
            }
        })
    }

    function getPoll(){
        var rows = $('#table_body');
        rows.html('');
        $.ajax({
            url:"/getQuestionByType?type=poll",
            type:"get",
            dataType:"json",
            success: function (data) {
                if (data.success) {
                    var html = '';
                    for (var i = 0; i < data.questionList.length; i++) {
                        var question = data.questionList[i];
                        var tempHtml = '<tr><td>' + question.content + '</td><td>'
                            + question.questionType + '</td><td>';
                        if (question.choices != null) {
                            for (var j = 0; j < question.choices.length; j++) {
                                var choice = question.choices[j];
                                if (choice.answer) {
                                    tempHtml += '<span class="right_answer">' + CHARACTER[j]+ '.' + choice.content + '</span>&nbsp&nbsp'
                                } else {
                                    tempHtml += '<span>'+ CHARACTER[j]+ '.' + choice.content + '</span>&nbsp&nbsp'
                                }
                            }
                        }
                        tempHtml += '</td><td>'
                            + '<a href="editQuestion?id=' + question.questionId + '">Edit</a></td>' +
                            '<td><a href="deleteQuestion?id=' + question.questionId + '">Delete</a></td></tr>';
                        html += tempHtml;
                    }
                    rows.html(html);
                } else {
                    alert(data.errMsg);
                }
            }
        })
    }

    function getCheckBox(){
        var rows = $('#table_body');
        rows.html('');
        $.ajax({
            url:"/getQuestionByType?type=checkbox",
            type:"get",
            dataType:"json",
            success: function (data) {
                if (data.success) {
                    var html = '';
                    for (var i = 0; i < data.questionList.length; i++) {
                        var question = data.questionList[i];
                        var tempHtml = '<tr><td>' + question.content + '</td><td>'
                            + question.questionType + '</td><td>';
                        if (question.choices != null) {
                            for (var j = 0; j < question.choices.length; j++) {
                                var choice = question.choices[j];
                                if (choice.answer) {
                                    tempHtml += '<span class="right_answer">' + CHARACTER[j]+ '.' + choice.content + '</span>&nbsp&nbsp'
                                } else {
                                    tempHtml += '<span>'+ CHARACTER[j]+ '.' + choice.content + '</span>&nbsp&nbsp'
                                }
                            }
                        }
                        tempHtml += '</td><td>'
                            + '<a href="editQuestion?id=' + question.questionId + '">Edit</a></td>' +
                            '<td><a href="deleteQuestion?id=' + question.questionId + '">Delete</a></td></tr>';
                        html += tempHtml;
                    }
                    rows.html(html);
                } else {
                    alert(data.errMsg);
                }
            }
        })
    }


    function getMatric(){
        var rows = $('#table_body');
        rows.html('');
        $.ajax({
            url:"/getQuestionByType?type=matric",
            type:"get",
            dataType:"json",
            success: function (data) {
                if (data.success) {
                    var html = '';
                    for (var i = 0; i < data.questionList.length; i++) {
                        var question = data.questionList[i];
                        var tempHtml = '<tr><td>' + question.content + '</td><td>'
                            + question.questionType + '</td><td>';
                        if (question.matricItems != null) {
                            for (var j = 0; j < question.matricItems.length; j++) {
                                var matricItem = question.matricItems[j];
                                tempHtml += '<span>'+ CHARACTER[j]+ '.' + matricItem.itemName + '&nbsp';
                                tempHtml += '[' + matricItem.defaultChoices + ']';
                                tempHtml +=   '</span>&nbsp&nbsp';
                            }
                        }
                        tempHtml += '</td><td>'
                            + '<a href="editQuestion?id=' + question.questionId + '">Edit</a></td>' +
                            '<td><a href="deleteQuestion?id=' + question.questionId + '">Delete</a></td></tr>';
                        html += tempHtml;
                    }
                    rows.html(html);
                } else {
                    alert(data.errMsg);
                }
            }
        })
    }
})