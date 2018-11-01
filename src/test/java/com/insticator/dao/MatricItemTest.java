package com.insticator.dao;


import com.insticator.BaseTest;
import com.insticator.entity.MatricItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MatricItemTest extends BaseTest {
    @Autowired
    MatricItemDao matricItemDao;
    @Autowired
    MatricItemDefaultChoiceDao matricItemDefaultChoiceDao;

    @Test
    public void testInsertMatricItem(){
        MatricItem matricItem = new MatricItem("age", 5);
        matricItemDao.insert(matricItem);
    }

    @Test
    public void testInsertMatricItemChoice(){
        matricItemDefaultChoiceDao.insert(1, "Male");
        matricItemDefaultChoiceDao.insert(1, "Female");
    }

    @Test
    public void testSelectMatricItem(){
        List<MatricItem> list = matricItemDao.selectByQuestionId(5);
        assertEquals(list.size(), 2);
    }

    @Test
    public void testSelectMatrcItemChoice(){
        List<String>list = matricItemDefaultChoiceDao.selectByMatricItemId(1);
        System.out.println(list);
        assertEquals(list.size(), 2);
    }

    @Test
    public void testDelete(){
        matricItemDefaultChoiceDao.deleteByMatricId(6);
    }

    @Test
    public void testUpdateName(){
        MatricItem matricItem = new MatricItem("age", 5);
        matricItemDao.insert(matricItem);
        matricItemDao.updateName(matricItem.getItemId(), "new gender");
    }

    @Test
    public void testUpdateContent(){
        matricItemDefaultChoiceDao.insert(1,"male");
        matricItemDefaultChoiceDao.updateContent(1,"female");
    }
}
