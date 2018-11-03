package com.insticator.dao;

import com.insticator.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionDao {
     int insert(Question question);
     int delete(@Param("questionId") int questionId);
     List<Question> selectAll();
     List<Question> selectFromIdx(@Param("from") int from, @Param("num") int num);
     int count();
     Question selectById(@Param("questionId") int questionId);
     int updateContent(@Param("questionId") int questionId, @Param("content")String content);
     List<Question> selectByType(@Param("type")int type);
}
