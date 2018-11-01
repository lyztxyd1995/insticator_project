package com.insticator.dao;

import com.insticator.entity.MatricItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MatricItemDao {
    int insert(MatricItem matricItem);
    int delete(int itemId);
    List<MatricItem>selectByQuestionId(int questionId);
    int deleteByQuestionId (int questionId);
    int updateName(@Param("itemId")int itemId, @Param("itemName")String itemName);
}
