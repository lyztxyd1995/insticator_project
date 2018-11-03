package com.insticator.service;

import com.insticator.BaseTest;
import com.insticator.entity.Choice;
import com.insticator.entity.MatricItem;
import com.insticator.entity.Question;
import com.insticator.util.QuestionType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionServiceTest extends BaseTest {
    @Autowired
    QuestionService questionService;

    @Test
    public void testaddQuestion(){
        Choice choice1 = new Choice();
        Choice choice2 = new Choice();
        List<Choice>choiceList = new ArrayList<>();
        choiceList.add(choice1);
        choiceList.add(choice2);
        boolean res = questionService.addQuestion(QuestionType.CheckBox, "check box question", choiceList);
        assertEquals(res,true);
    }

    @Test
    public void testaddMatric(){
        MatricItem m1 = new MatricItem("gender");
        List<String>m1list = new ArrayList<>();
        m1list.add("Male");
        m1list.add("Female");
        m1.setDefaultChoices(m1list);
        MatricItem m2 = new MatricItem("age");
        List<String>m2list = new ArrayList<>();
        m2list.add("<18");
        m2list.add("18-25");
        m2list.add("25-30");
        m2list.add(">30");
        m2.setDefaultChoices(m2list);
        List<MatricItem>matricItems = new ArrayList<>();
        matricItems.add(m1);
        matricItems.add(m2);
        questionService.addMatric("tell us about your self", matricItems);
    }

    @Test
    public void testDelete(){
        questionService.deleteQuestion(20);
    }

    @Test
    public void testAddChoice(){
        Question question = questionService.selectById(32);
        Choice choice = new Choice();
        boolean res = questionService.addChoice(choice,question);
        assertEquals(res, true);
    }

    @Test
    public void testAddChoices(){
        Question question = questionService.selectById(33);
        Choice choice = new Choice();
        Choice choice1 = new Choice();
        List<Choice> choiceList = new ArrayList<>();
        choiceList.add(choice);
        choiceList.add(choice1);
        boolean res = questionService.addChoices(choiceList, question);
        assertEquals(res, true);
    }

    @Test
    public void testAddMatricItem(){
        Question question = questionService.selectById(34);
        MatricItem matricItem = new MatricItem("sex");
        List<String>list = new ArrayList<>();
        list.add("man");
        matricItem.setDefaultChoices(list);
        questionService.addMatricItem(matricItem, question);
    }

    @Test
    public void testSelectAll(){
        List<Question>res = questionService.selectAll();
        System.out.println(res);
    }

    @Test
    public void testSelectByPage(){
        List<Question>res = questionService.selectByPage(3,1);
        System.out.println(res);
    }

    @Test
    public void testSelectByType(){
        List<Question>res = questionService.selectByType(QuestionType.Trivial);
        System.out.println(res);
    }

}
