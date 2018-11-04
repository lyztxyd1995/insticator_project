package com.insticator.service;

import com.insticator.BaseTest;
import com.insticator.entity.Question;
import com.insticator.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends BaseTest {
    @Autowired
    UserService userService;
    @Test
    public void testCheckUser(){
        boolean res = userService.addUser("yizeliu", "123");
        assertEquals(res, true);
        User user = userService.checkUser("yizeliu", "123");
        assertEquals(user.getUsername(), "yizeliu");
        assertEquals(user.getPassword(), "123");
        user = userService.checkUser("yizeliu", "1");
        assertEquals(user, null);
        user = userService.checkUser("123", "123");
        assertEquals(user, null);
        res = userService.removeUser("yizeliu", "12");
        assertEquals(res, false);
        res = userService.removeUser("yizeliu", "123");
        assertEquals(res, true);
    }

    @Test
    public void testGetQuestion() {
        Question question = userService.getQuestion("yizeliu");
        System.out.println(question);
    }

}
