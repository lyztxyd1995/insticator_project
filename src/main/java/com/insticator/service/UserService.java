package com.insticator.service;

import com.insticator.entity.Question;
import com.insticator.entity.User;

public interface UserService {
    User checkUser(String username, String password);
    boolean addUser(String username, String password);
    boolean removeUser(String username, String password);
    Question getQuestion(String username);
    boolean addRecord(Integer questionId, String username);
    boolean updateUserPoints(String username, int points);
    User getUser(String username);
}
