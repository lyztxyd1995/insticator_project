$(function(){
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
    var CHARACTER = ['A','B','C','D','E','F','G','H','I','J']
    //curTpye is used to record the state, could be trivia, poll, checkbox or matric
    var curType = 'trivia';
    var choiceNum = 2;
    var firstItemNum = 2;
    var secondItemNum = 2;
    refreshQuestionItems();
    $(document).on("click", "#remove_choice", function(){
        if (choiceNum <= 2) {
            alert("The number of choices should be at least 2");
            return;
        }
        var id = '#choice' + choiceNum;
        $(id).closest("li").remove();
        choiceNum -= 1;
    })
    $(document).on("click", "#add_choice", function(){
        var choice_list = $('#choice_list');
        if (curType === 'checkbox') {
            if (choiceNum >= 10) {
                alert("The number of choices could be at most 10");
                return;
            }
        } else {
            if (choiceNum >= 4) {
                alert("The number of choices could be at most 4");
                return;
            }
        }
        choiceNum += 1;
        var html = '<li class="choice_item">' +
            '<div class="form-group">' +
            '<label for="choice'+ choiceNum + '">Choice '+ CHARACTER[choiceNum - 1] + '</label>' +
            '<input type="text" class="form-control" id="choice'+ choiceNum +'">';
        html += '<label for="isAnswer' + choiceNum +'">Is Answer</label>&nbsp;' +
            '<select id="isAnswer'+choiceNum +'">' +
            '<option>Yes</option><option selected="selected">No</option> </select></div></li>';
        var choice_list = $('#choice_list');
        choice_list.append(html);
    })
    $(document).on("click", '#first_item_add', function(){
        if (firstItemNum >= 10) {
            alert('number of default items could not be larger than 10');
            return;
        }
        firstItemNum += 1;
        var matric_item1_ul = $('#matric_item1_ul');
        var html = '<li><label for="first_item'+firstItemNum+'">Item '+CHARACTER[firstItemNum - 1]+'</label>' +
            '<input type="text" class="form-control" id="first_item'+ firstItemNum +'"></li>';
        matric_item1_ul.append(html);
    })
    $(document).on("click", '#first_item_remove', function(){
        if (firstItemNum <= 2) {
            alert('number of default items could not be less than 2');
            return;
        }
        var id = '#first_item' + firstItemNum;
        $(id).closest("li").remove();
        firstItemNum -= 1;
    })

    $(document).on("click", '#second_item_add', function(){
        if (secondItemNum >= 10) {
            alert('number of default items could not be larger than 10');
            return;
        }
        secondItemNum += 1;
        var matric_item2_ul = $('#matric_item2_ul');
        var html = '<li><label for="second_item'+secondItemNum+'">Item '+CHARACTER[secondItemNum - 1]+'</label>' +
            '<input type="text" class="form-control" id="second_item'+ secondItemNum +'"></li>';
        matric_item2_ul.append(html);
    })
    $(document).on("click", '#second_item_remove', function(){
        if (secondItemNum <= 2) {
            alert('number of default items could not be less than 2');
            return;
        }
        var id = '#second_item' + secondItemNum;
        $(id).closest("li").remove();
        secondItemNum -= 1;
    })
    $(document).on("click", '#save_btn', function(){
        if (curType === 'matric') {
            addMaricQuestion();
        } else {
            addNormalQuestion();
        }
    })
    function addNormalQuestion(){
        var content = $('#content').val();
        var choiceList = []
        for (var i = 1; i <= choiceNum; i++) {
            if ($('#choice' + i).val() === ""){
                alert('choice content must not be empty');
                return;
            }
            choiceList.push({
                'content': $('#choice' + i).val(),
                'isAnswer': $('#isAnswer' + i + ' :selected').text()
            });
        }
        if (content === "") {
            alert("question content could not be empty");
            return;
        }
        if (choiceList.length < 2) {
            alert("the number of choices must be larger than 2");
            return;
        }
        var data = {}
        data.content = content;
        data.choices = choiceList;
        data.questionType = curType;
        $.ajax({
            type:"POST",
            url: "/insticator/handleAddQuestion",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    alert("Add a new question successfully");
                    window.location.href = '/insticator/manageQuestions';
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }
    function addMaricQuestion(){
        var content = $('#content').val();
        if (content === '') {
            alert("question content could not be empty");
            return;
        }
        var maricItemList = []
        var choices1 = [];
        for (var i = 1; i <= firstItemNum; i++) {
            if ($('#first_item' + i).val() === '') {
                alert('choice item could not be empty');
                return;
            }
            choices1.push($('#first_item' + i).val());
        }
        var matricItem1 = {};
        matricItem1.content = $('#matric_item1').val();
        if (matricItem1.content === ''){
            alert('matric description could not be empty');
            return;
        }
        matricItem1.defaultChoices = choices1;
        maricItemList.push(matricItem1);

        var choices2 = [];
        for (var i = 1; i <= secondItemNum; i++) {
            if ($('#second_item' + i).val() === '') {
                alert('choice item could not be empty');
                return;
            }
            choices2.push($('#second_item' + i).val());
        }
        var matricItem2 = {};
        matricItem2.content = $('#matric_item2').val();
        if (matricItem2.content === ''){
            alert('matric description could not be empty');
            return;
        }
        matricItem2.defaultChoices = choices2;
        maricItemList.push(matricItem2);

        var matricItem3 = {};
        matricItem3.content = $('#matric_item3').val();
        if (matricItem3.content === ''){
            alert('matric description could not be empty');
            return;
        }
        matricItem3.defaultChoices = null;
        maricItemList.push(matricItem3);

        var data = {}
        data.maricItemList = maricItemList;
        data.content = content;
        data.questionType = curType;
        console.log(data);
        $.ajax({
            type:"POST",
            url: "/insticator/handleAddQuestion",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    alert("Add a new question successfully");
                    window.location.href = '/insticator/manageQuestions';
                } else {
                    alert(data.errMsg);
                }
            }
        });
    }
    function refreshQuestionItems(){
        var question_items = $('#question_items');
        question_items.html('<div class="form-group">' +
            '                <button class="btn btn-success" id="add_choice" type=\'button\'>Add choice item</button> &nbsp;&nbsp;&nbsp;' +
            '                <button class="btn btn-danger" id="remove_choice" type=\'button\'>Remove choice item</button>' +
            '            </div>' +
            '            <ul id="choice_list" class="form-group">' +
            '                <li class="choice_item">' +
            '                    <div class="form-group">' +
            '                        <label for="choice1">Choice A</label>' +
            '                        <input type="text" class="form-control" id="choice1">' +
            '                        <label for="isAnswer1">Is Answer</label>&nbsp;' +
            '                        <select id="isAnswer1">' +
            '                            <option>Yes</option>' +
            '                            <option selected="selected">No</option>' +
            '                        </select>' +
            '                    </div>' +
            '                </li>' +
            '                <li class="choice_item">' +
            '                    <div class="form-group">' +
            '                        <label for="choice2">Choice B</label>' +
            '                        <input type="text" class="form-control" id="choice2">' +
            '                        <label for="isAnswer2">Is Answer</label>' +
            '                        <select id="isAnswer2">' +
            '                            <option>Yes</option>' +
            '                            <option selected="selected">No</option>' +
            '                        </select>' +
            '                    </div>' +
            '                </li>' +
            '</ul>')
        choiceNum = 2;
    }

    function refreshMatric(){
        var question_items = $('#question_items');
        question_items.html('<div class="form-group">' +
            '                <label for="matric_item1">Matric Item one (with default items)</label>' +
            '                <input type="text" class="form-control" id="matric_item1">' +
            '                <div class="form-group">' +
            '                    <button type="button" class="btn btn-success" id="first_item_add">Add default items</button>' +
            '                    <button type="button" class="btn btn-danger" id="first_item_remove">remove default items</button>' +
            '                </div>' +
            '                <ul id="matric_item1_ul">' +
            '                    <li>' +
            '                        <label for="first_item1">Item A</label>' +
            '                        <input type="text" class="form-control" id="first_item1">' +
            '                    </li>' +
            '                    <li>' +
            '                        <label for="first_item2">Item B</label>' +
            '                        <input type="text" class="form-control" id="first_item2">' +
            '                    </li>' +
            '                </ul>' +
            '            </div>' +
            '            <div class="form-group">' +
            '                <label for="matric_item2">Matric Item two (with default choices)</label>' +
            '                <input type="text" class="form-control" id="matric_item2">' +
            '                <div class="form-group">' +
            '                    <button type="button" class="btn btn-success" id="second_item_add">Add default choices</button>' +
            '                    <button type="button" class="btn btn-danger" id="second_item_remove">remove default choices</button>' +
            '                </div>' +
            '                <ul id="matric_item2_ul">' +
            '                    <li>' +
            '                        <label for="second_item1">Item A</label>' +
            '                        <input type="text" class="form-control" id="second_item1">' +
            '                    </li>' +
            '                    <li>' +
            '                        <label for="second_item2">Item B</label>' +
            '                        <input type="text" class="form-control" id="second_item2">' +
            '                    </li>' +
            '                </ul>' +
            '            </div>' +
            '            <div class="form-group">\n' +
            '                <label for="matric_item3">Matric Item three (No default choices, let users fill in)</label>' +
            '                <input type="text" class="form-control" id="matric_item3">' +
            '            </div>');
            firstItemNum = 2;
            secondItemNum = 2;
    }
    $('#type').change(function(){
        curType = $('#type :selected').text();
        if (curType !== 'matric') {
            refreshQuestionItems();
        } else {
            refreshMatric();
        }
    })
})