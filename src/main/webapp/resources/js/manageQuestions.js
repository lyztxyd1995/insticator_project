$(function(){
    var CHARACTER = ['A','B','C','D','E','F','G','H','I','J']
    function getAdmin() {
        var url = '/insticator/getAdmin';
        $.ajax({
            url:url,
            type:"get",
            success:function(data){
                if(!data.success){
                    alert("No administrator info");
                    window.location.href = '/insticator/admin';
                }
            }
        })
    }
    getAdmin();
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

    $(document).on("click", ".delete", function(){
            var questionId = $(this).attr('questionId');
            var r = confirm("Are you sure to delete this question?");
            if (r == true) {
                var data = {}
                data.questionId = questionId;
                $.ajax({
                    type:"POST",
                    url: "/insticator/deleteQuestion",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function(data) {
                        if (data.success) {
                            alert("successfully remove the question");
                            window.location.href = '/insticator/manageQuestions';
                        } else {
                            alert(data.errMsg);
                        }
                    }
                });
            }
    })

    $(document).on("click", ".edit", function(){
        var questionId = $(this).attr('questionId');
        $.ajax({
            url:"/insticator/editQuestion?questionId=" + questionId,
            type:"GET",
            dataType:"json",
            success: function(data){
                if (data.success) {
                    window.location.href='/insticator/toEditQuestion';
                } else {
                    alert(data.errMsg);
                }
            }
        })
    })
    function getTrivia(){
        var rows = $('#table_body');
        rows.html('');
        $.ajax({
            url:"/insticator/getQuestionByType?type=trivia",
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
                            + '<button type="button"  class="edit" questionId="'+question.questionId+'">Edit</button></td>' +
                            '<td><button type="button"  class="delete" questionId="'+question.questionId+'">Delete</button></td></tr>';
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
            url:"/insticator/getQuestionByType?type=poll",
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
                            + '<button type="button" class="edit" questionId="'+question.questionId+'">Edit</button></td>'+
                            '<td><button type="button" class="delete" questionId="'+question.questionId+'">Delete</button></td></tr>';
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
            url:"/insticator/getQuestionByType?type=checkbox",
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
                            + '<button type="button" class="edit" questionId="'+question.questionId+'">Edit</button></td>' +
                            '<td><button type="button" class="delete" questionId="'+question.questionId+'">Delete</button></td></tr>';
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
            url:"/insticator/getQuestionByType?type=matric",
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
                            + '<button type="button" class="edit" questionId="'+question.questionId+'">Edit</button></td>' +
                            '<td><button type="button" class="delete" questionId="'+question.questionId+'">Delete</button></td></tr>';
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