package com.insticator.dao;

import com.insticator.BaseTest;
import com.insticator.entity.Question;
import com.insticator.util.QuestionType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionDaoTest extends BaseTest {
    @Autowired
    QuestionDao questionDao;

    @Test
    public void testInsert(){
        Question question = new Question();
        question.setQuestionType(QuestionType.Matric);
        question.setContent("test question");
        int res = questionDao.insert(question);
        System.out.println(question.getQuestionId());
    }

    @Test
    public void testDelete(){
        int id = 21;
        int res = questionDao.delete(id);
        assertEquals(res,1);
    }

    @Test
    public void testSelectAll(){
        List<Question>list = questionDao.selectAll();
        System.out.println(list);
    }

    @Test
    public void testSelectById(){
        Question q = questionDao.selectById(22);
        System.out.println(q);
    }

    @Test
    public void testdeleteQuestion(){
        questionDao.delete(22);
    }

    @Test
    public void testUpdate(){
        Question question = new Question();
        question.setQuestionType(QuestionType.Matric);
        question.setContent("test question");
        questionDao.insert(question);
        questionDao.updateContent(question.getQuestionId(), "new content");
    }

    @Test
    public void testSelect(){
        List<Question>questions = questionDao.selectFromIdx(1,2);
        System.out.println(questions);
    }

    @Test
    public void testCount(){
        int res = questionDao.count();
        assertEquals(res, 3);
    }
}
