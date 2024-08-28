package com.cloudmaturity.cloud.objects;

public class Question {

    private final String text;
    private final String[] answers;
    private final Boolean isLeadQuestion;


    public Question(String text, String[] answers, Boolean isLeadQuestion){
        this.text = text;
        this.answers = answers;
        this.isLeadQuestion = isLeadQuestion;
    }

    public String getText(){return text;}
    public String[] getAnswers(){return answers;}
    public Boolean isLeadQuestion(){return isLeadQuestion;}
}
