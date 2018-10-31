package com.insticator.dao;

import com.insticator.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int insertUser(User user);
    int deleteUser(String username);
    User getUserByUsername(@Param("username") String username);
}
