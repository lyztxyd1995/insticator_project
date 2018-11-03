package com.insticator.entity;

public class Choice {
    private Integer choiceId;
    private Integer questionId;
    private String content;
    private boolean isAnswer;

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
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

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "choiceId=" + choiceId +
                ", questionId=" + questionId +
                ", content='" + content + '\'' +
                ", isAnswer=" + isAnswer +
                '}';
    }
}
