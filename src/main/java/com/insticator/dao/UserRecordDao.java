package com.insticator.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRecordDao {
    int insert(@Param("questionId")int questionId, @Param("username")String username);
    List<Integer>getQuestionIdByUser( @Param("username")String username);
}
