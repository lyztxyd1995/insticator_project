package com.insticator.service.impl;

import com.insticator.dao.ChoiceDao;
import com.insticator.dao.MatricItemDao;
import com.insticator.dao.MatricItemDefaultChoiceDao;
import com.insticator.dao.QuestionDao;
import com.insticator.entity.Choice;
import com.insticator.entity.MatricItem;
import com.insticator.entity.Question;
import com.insticator.service.QuestionService;
import com.insticator.util.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    MatricItemDao matricItemDao;
    @Autowired
    MatricItemDefaultChoiceDao matricItemDefaultChoiceDao;
    @Autowired
    ChoiceDao choiceDao;

    @Override
    @Transactional
    public boolean addQuestion(QuestionType questionType, String content, List<Choice> choiceList) {
        //only support adding normal question types: trivia, poll, checkbox
        if (questionType == QuestionType.Matric) {
            return false;
        }
        try {
            System.out.println("start adding a question");
            Question question = new Question();
            question.setQuestionType(questionType);
            question.setContent(content);
            System.out.println(question);
            questionDao.insert(question);
            if (choiceList != null && choiceList.size() > 0) {
                for (Choice choice : choiceList) {
                    choice.setQuestionId(question.getQuestionId());
                }
                choiceDao.batchInsert(choiceList);
            }
        } catch (Exception e){
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean addMatric(String content, List<MatricItem> matricItemList) {
        try{
            Question question = new Question();
            question.setQuestionType(QuestionType.Matric);
            question.setContent(content);
            questionDao.insert(question);
            for (MatricItem matricItem : matricItemList) {
                matricItem.setQuestionId(question.getQuestionId());
            }
            if (matricItemList != null && matricItemList.size() > 0) {
                for (MatricItem matricItem : matricItemList) {
                    matricItemDao.insert(matricItem);
                    for (String str : matricItem.getDefaultChoices()) {
                        matricItemDefaultChoiceDao.insert(matricItem.getItemId(), str);
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteQuestion(int questionId) {
        Question question = questionDao.selectById(questionId);
        if (question == null) {
            return false;
        }
        try {
            if (question.getQuestionType() == QuestionType.Matric) {
                List<MatricItem>matricItemList = matricItemDao.selectByQuestionId(question.getQuestionId());
                matricItemDao.deleteByQuestionId(questionId);
                for (MatricItem matricItem : matricItemList) {
                    matricItemDefaultChoiceDao.deleteByMatricId(matricItem.getItemId());
               }
            } else {
                choiceDao.deleteByQuestionId(questionId);
            }
            questionDao.delete(questionId);
        } catch (Exception e){
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    public boolean updateContent(Question question) {
        int res = questionDao.updateContent(question.getQuestionId(), question.getContent());
        return res == 1;
    }

    @Override
    public Question selectById(int id) {
        Question question = questionDao.selectById(id);
        if (question == null) {
            return null;
        }
        if (question.getQuestionType() == QuestionType.Matric) {
            List<MatricItem>matricItems = matricItemDao.selectByQuestionId(question.getQuestionId());
            for (MatricItem matricItem : matricItems) {
                List<String>list = matricItemDefaultChoiceDao.selectByMatricItemId(matricItem.getItemId());
                matricItem.setDefaultChoices(list);
            }
            question.setMatricItems(matricItems);
        } else {
            List<Choice>choiceList = choiceDao.selectByQuetionId(question.getQuestionId());
            question.setChoices(choiceList);
        }
        return question;
    }

    @Override
    public List<Question> selectAll() {
        List<Question>res = questionDao.selectAll();
        addAttribute(res);
        return res;
    }

    @Override
    public List<Question> selectByPage(int pageNum, int pageSize) {
        int total = questionDao.count();
        int startIdx = (pageNum - 1) * pageSize;
        if (startIdx >= total) {
            return null;
        }
        List<Question>res = questionDao.selectFromIdx(startIdx, pageSize);
        addAttribute(res);
        return res;
    }

    @Override
    public List<Question> selectByType(QuestionType questionType) {
        List<Question>res = questionDao.selectByType(questionType.getId());
        addAttribute(res);
        return res;
    }

    private void addAttribute(List<Question>questions) {
        for (Question question : questions) {
            if (question.getQuestionType() == QuestionType.Matric) {
                List<MatricItem>matricItems = matricItemDao.selectByQuestionId(question.getQuestionId());
                for (MatricItem matricItem : matricItems) {
                    List<String>items = matricItemDefaultChoiceDao.selectByMatricItemId(matricItem.getItemId());
                    matricItem.setDefaultChoices(items);
                }
                question.setMatricItems(matricItems);
            } else {
                List<Choice>choiceList = choiceDao.selectByQuetionId(question.getQuestionId());
                question.setChoices(choiceList);
            }
        }
    }

    @Override
    public boolean addChoice(Choice choice, Question question) {
       if (!validQuestion(question) || question.getQuestionType() == QuestionType.Matric) {
           return false;
       }
       if (choice == null || choice.getContent() == null) {
           return false;
       }
       choice.setQuestionId(question.getQuestionId());
       System.out.println(choice.getQuestionId());
       int res = choiceDao.insert(choice);
       return res == 1;
    }

    @Override
    public boolean addChoices(List<Choice> choiceList, Question question) {
        if (!validQuestion(question) || question.getQuestionType() == QuestionType.Matric) {
            return false;
        }
        if (choiceList == null) {
            return false;
        }
        for (Choice choice : choiceList) {
            choice.setQuestionId(question.getQuestionId());
        }
        choiceDao.batchInsert(choiceList);
        return true;
    }

    @Override
    @Transactional
    public boolean addMatricItem(MatricItem matricItem, Question question) {
        if (!validQuestion(question) || question.getQuestionType() != QuestionType.Matric){
            return false;
        }
        if (matricItem == null || matricItem.getItemName() == null) {
            return false;
        }
        try {
            matricItem.setQuestionId(question.getQuestionId());
            matricItemDao.insert(matricItem);
            if (matricItem.getDefaultChoices() != null) {
                for (String str : matricItem.getDefaultChoices()) {
                    matricItemDefaultChoiceDao.insert(matricItem.getItemId(), str);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean addMatricItems(List<MatricItem> matricItemList, Question question) {
        if (!validQuestion(question) || question.getQuestionType() != QuestionType.Matric){
            return false;
        }
        if (matricItemList == null) {
            return false;
        }
        for (MatricItem matricItem : matricItemList) {
            matricItem.setQuestionId(question.getQuestionId());
        }
        for (MatricItem matricItem : matricItemList) {
            matricItemDao.insert(matricItem);
            if (matricItem.getDefaultChoices() != null) {
                for (String str : matricItem.getDefaultChoices()) {
                    matricItemDefaultChoiceDao.insert(matricItem.getItemId(), str);
                }
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void updateQuetion(Question question) {
        try {
            deleteQuestion(question.getQuestionId());
            System.out.println("finish deleting");
            if (question.getQuestionType() == QuestionType.Matric){
                addMatric(question.getContent(), question.getMatricItems());
            } else {
                System.out.println("start adding a new question");
                System.out.println(question);
                addQuestion(question.getQuestionType(), question.getContent(), question.getChoices());
                System.out.println("finish adding a new question");
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean validQuestion(Question question) {
        if (question == null) {
            return false;
        }
        if (question.getQuestionId() == null) {
            return false;
        }
        Question q = questionDao.selectById(question.getQuestionId());
        if (q == null) {
            return false;
        }
        return true;
    }



}
