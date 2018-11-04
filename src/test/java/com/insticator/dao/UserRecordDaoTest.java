package com.insticator.dao;

import com.insticator.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserRecordDaoTest extends BaseTest {
    @Autowired
    UserRecordDao userRecordDao;

    @Test
    public void testInsert(){
        int res = userRecordDao.insert(1, "yizeliu");
        assertEquals(res,1);
    }

    @Test
    public void testgetQuestionIdByUser(){
        List<Integer>list = userRecordDao.getQuestionIdByUser("yizeliu");
        assertEquals(list.size(),1);
    }
}
