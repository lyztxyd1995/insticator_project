package com.insticator.dao;


import com.insticator.BaseTest;
import com.insticator.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class UserDaoTest extends BaseTest {
    @Autowired UserDao userDao;

    @Test
    public void testInsert(){
        String userId = "1111";
        String password = "123";
        User user = new User(userId, password);
        int res = userDao.insertUser(user);
        assertEquals(res,1);
    }

    @Test
    public void testDelete(){
        int res = userDao.deleteUser("1111");
        assertEquals(res, 1);
    }

    @Test
    public void testGetUserByUserName(){
        User user = userDao.getUserByUsername("1111");
        System.out.println(user);
    }
}
