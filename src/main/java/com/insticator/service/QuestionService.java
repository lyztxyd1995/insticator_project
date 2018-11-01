package com.insticator.service;

import com.insticator.entity.Choice;
import com.insticator.entity.MatricItem;
import com.insticator.entity.Question;
import com.insticator.util.QuestionType;

import java.util.List;

public interface QuestionService {
    boolean addQuestion(QuestionType questionType, String content, List<Choice> choiceList);
    boolean addMatric(String content, List<MatricItem>matricItemList);
    boolean deleteQuestion(int questionId);
    boolean updateContent(Question question);
    Question selectById(int id);
    List<Question>selectAll();
    List<Question>selectByPage(int pageNum, int pageSize);
    boolean addChoice(Choice choice, Question question);
    boolean addMatricItem(MatricItem matricItem, Question question);
    boolean addChoices(List<Choice>choiceList, Question question);
    boolean addMatricItems(List<MatricItem>matricItemList, Question question);
}
