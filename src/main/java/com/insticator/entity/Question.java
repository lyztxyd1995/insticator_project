package com.insticator.entity;

import com.insticator.util.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private QuestionType questionType;
    private Integer questionId;
    private String content;
    private List<Choice>choices;
    private List<MatricItem>matricItems;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<MatricItem> getMatricItems() {
        return matricItems;
    }

    public void setMatricItems(List<MatricItem> matricItems) {
        this.matricItems = matricItems;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
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
                ", choices=" + choices +
                ", matricItems=" + matricItems +
                '}';
    }
}
