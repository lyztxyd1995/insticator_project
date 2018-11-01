package com.insticator.dao;

import com.insticator.entity.Administrator;
import org.apache.ibatis.annotations.Param;

public interface AdministratorDao {
    int insert(Administrator administrator);
    int delete(String username);
    Administrator select(@Param("username") String username);
}
