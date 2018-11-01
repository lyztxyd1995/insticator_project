package com.insticator.dao;

import com.insticator.entity.Choice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChoiceDao {
    int insert(Choice choice);
    int batchInsert(List<Choice> choiceList);
    int delete(int choiceId);
    int deleteByQuestionId(int quetionId);
    List<Choice> selectByQuetionId(int questionId);
    int updateContent(@Param("choiceId")int choiceId, @Param("content")String content);
    int updateAnswer(@Param("choiceId") int choiceId, @Param("isAnswer")boolean isAnswer);
}
