package com.insticator.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MatricItemDefaultChoiceDao {
    int insert(@Param("matricItemId")int matricItemId, @Param("content")String content);
    int deleteByMatricId(@Param("matricItemId") int matricItemId);
    List<String>selectByMatricItemId(int matricItemId);
    int updateContent(@Param("matricItemId")int matricItemId, @Param("content")String content);
}
