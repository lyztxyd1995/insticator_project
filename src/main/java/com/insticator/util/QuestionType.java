package com.insticator.util;

public enum QuestionType {
    Trivial(0), Poll(1), CheckBox(2), Matric(3);
    private int id;
    QuestionType(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public static QuestionType getById(int id) {
        for (QuestionType q : values()) {
            if (q.id == id) {
                return q;
            }
        }
        return null;
    }
}
