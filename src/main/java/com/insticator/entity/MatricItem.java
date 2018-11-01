package com.insticator.entity;

import java.util.ArrayList;
import java.util.List;

public class MatricItem {
    private Integer itemId;
    private String itemName;
    private Integer questionId;
    private List<String>defaultChoices;

    public MatricItem(String itemName) {
        this.itemName = itemName;
        this.defaultChoices = new ArrayList<>();
    }
    public MatricItem(String itemName, int questionId){
        this(itemName);
        this.questionId = questionId;
    }
    public MatricItem(Integer itemId, String itemName, Integer questionId) {
        this(itemName, questionId);
        this.itemId = itemId;
    }
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<String> getDefaultChoices() {
        return defaultChoices;
    }

    public void setDefaultChoices(List<String> defaultChoices) {
        this.defaultChoices = defaultChoices;
    }

    @Override
    public String toString() {
        return "MatricItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", questionId=" + questionId +
                ", defaultChoices=" + defaultChoices +
                '}';
    }
}
