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
        Choice choice = new Choice();
        Choice choice1 = new Choice();
        choiceDao.insert(choice);
        choiceDao.insert(choice1);
    }

    @Test
    public void testBatchInsert(){
        Choice choice = new Choice();
        Choice choice1 = new Choice();
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
        Choice choice = new Choice();
        choiceDao.insert(choice);
        choiceDao.updateContent(choice.getChoiceId(), "new content");
        choiceDao.updateAnswer(choice.getChoiceId(), true);
    }
}
