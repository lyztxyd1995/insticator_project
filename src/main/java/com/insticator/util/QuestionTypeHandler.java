package com.insticator.util;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionTypeHandler implements TypeHandler<QuestionType> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, QuestionType questionType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, questionType.getId());
    }

    @Override
    public QuestionType getResult(ResultSet resultSet, String s) throws SQLException {
        int id = resultSet.getInt(s);
        return QuestionType.getById(id);
    }

    @Override
    public QuestionType getResult(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(i);
        return QuestionType.getById(id);
    }

    @Override
    public QuestionType getResult(CallableStatement callableStatement, int i) throws SQLException {
        int id = callableStatement.getInt(i);
        return QuestionType.getById(id);
    }
}
