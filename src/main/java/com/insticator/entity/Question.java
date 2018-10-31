package com.insticator.entity;

import com.insticator.util.QuestionType;

public class Question {
    private QuestionType questionType;
    private int questionId;
    private String content;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionType=" + questionType +
                ", questionId=" + questionId +
                ", content='" + content + '\'' +
                '}';
    }
}
