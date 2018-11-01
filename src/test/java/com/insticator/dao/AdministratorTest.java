package com.insticator.dao;

import com.insticator.BaseTest;
import com.insticator.entity.Administrator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class AdministratorTest extends BaseTest {
    @Autowired
    AdministratorDao administratorDao;

    @Test
    public void testAllFunctions(){
        Administrator administrator = new Administrator();
        administrator.setUsername("yizeLiu");
        administrator.setPassword("123");
        int res = administratorDao.insert(administrator);
        assertEquals(res,1);
        administrator = administratorDao.select("yizeLiu");
        System.out.println(administrator);
    }

}
