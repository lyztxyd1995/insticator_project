package com.insticator.dao;

import com.insticator.BaseTest;
import com.insticator.entity.Choice;
import com.insticator.entity.Question;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChoiceDaoTest extends BaseTest {
    @Autowired
    ChoiceDao choiceDao;

    @Autowired
    QuestionDao questionDao;
    @Test
    public void testInsert(){
        Choice choice = new Choice(4, "first choice");
        Choice choice1 = new Choice(4, "second choice", true);
        choiceDao.insert(choice);
        choiceDao.insert(choice1);
    }

    @Test
    public void testBatchInsert(){
        Choice choice = new Choice(4, "third choice");
        Choice choice1 = new Choice(4, "fourth choice", true);
        List<Choice>choices = new ArrayList<>();
        choices.add(choice);
        choices.add(choice1);
        choiceDao.batchInsert(choices);
    }

    @Test
    public void testDelete(){
        int res = choiceDao.delete(4);
        assertEquals(res,1);
    }
    @Test
    public void testDeleteByQuestionId(){
        choiceDao.deleteByQuestionId(10);
    }

    @Test
    public void testUpdate(){
        Choice choice = new Choice(4, "test choice");
        choiceDao.insert(choice);
        choiceDao.updateContent(choice.getChoiceId(), "new content");
        choiceDao.updateAnswer(choice.getChoiceId(), true);
    }
}
