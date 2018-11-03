package com.insticator.util;

public enum QuestionType {
    Trivial(0), Poll(1), CheckBox(2), Matric(3);
    private static final String TRIVIAL = "trivia";
    private static final String POLL = "poll";
    private static final String CHECKBOX = "checkbox";
    private static final String MATRIC = "matric";

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
    public static QuestionType getByName(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        if (str.equals(TRIVIAL)) {
            return Trivial;
        }
        if (str.equals(POLL)) {
            return Poll;
        }
        if (str.equals(CHECKBOX)) {
            return CheckBox;
        }
        if (str.equals(MATRIC)) {
            return Matric;
        }
        return null;
    }
}
