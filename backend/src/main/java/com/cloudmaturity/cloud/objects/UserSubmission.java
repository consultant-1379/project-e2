package com.cloudmaturity.cloud.objects;

import java.util.Date;
import java.util.Map;

public class UserSubmission {
    private Map<String,  Float> answers;
    private Date dateTime;

    public UserSubmission(){
        this.dateTime = new Date();
    }

    public UserSubmission(Map<String, Float> answers){
        this.answers = answers;
        this.dateTime = new Date();
    }

    public Map<String, Float> getAnswers(){
        return answers;
    }

    public Date getDate(){
        return dateTime;
    }

    public void setDate(Date date){
        this.dateTime = date;
    }

    public void setAnswers(Map<String, Float> updatedAnswers){
        this.answers = updatedAnswers;
    }

}
