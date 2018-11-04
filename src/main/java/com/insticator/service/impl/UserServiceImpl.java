package com.insticator.service.impl;

import com.insticator.dao.*;
import com.insticator.entity.MatricItem;
import com.insticator.entity.Question;
import com.insticator.entity.User;
import com.insticator.service.UserService;
import com.insticator.util.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRecordDao userRecordDao;
    @Autowired
    UserDao userDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    MatricItemDao matricItemDao;
    @Autowired
    MatricItemDefaultChoiceDao matricItemDefaultChoiceDao;
    @Autowired
    ChoiceDao choiceDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    @Override
    public boolean addUser(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            return false;
        }
        user = new User(username,password);
        userDao.insertUser(user);
        return true;
    }

    @Override
    public boolean removeUser(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }
        userDao.deleteUser(username);
        return true;
    }

    @Override
    public Question getQuestion(String username) {
        List<Question>questions = questionDao.selectAll();
        List<Integer>answeredQuestions = userRecordDao.getQuestionIdByUser(username);
        Set<Integer> set = new HashSet<>();
        for (Integer i : answeredQuestions) {
            set.add(i);
        }
        List<Question>validQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (!set.contains(question.getQuestionId())) {
                validQuestions.add(question);
            }
        }
        if (validQuestions.size() == 0) {
            return null;
        }
        int rand = (int)(validQuestions.size() * Math.random());
        Question res = validQuestions.get(rand);
        if (res.getQuestionType() == QuestionType.Matric) {
            List<MatricItem>matricItems = matricItemDao.selectByQuestionId(res.getQuestionId());
            for (MatricItem matricItem : matricItems) {
                matricItem.setDefaultChoices(matricItemDefaultChoiceDao.selectByMatricItemId(matricItem.getItemId()));
            }
            res.setMatricItems(matricItems);
        } else {
            res.setChoices(choiceDao.selectByQuetionId(res.getQuestionId()));
        }
        return res;
    }

    @Override
    public boolean addRecord(Integer questionId, String username) {
       return  userRecordDao.insert(questionId,username) == 1;
    }

    @Override
    public boolean updateUserPoints(String username, int points) {
        return userDao.updatePoints(username,points) == 1;
    }

    @Override
    public User getUser(String username) {
        return userDao.getUserByUsername(username);
    }


}
