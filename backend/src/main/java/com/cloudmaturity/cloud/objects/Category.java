package com.cloudmaturity.cloud.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class Category {
    @Id
    private String id;
    private String name;
    private Question leadQuestion;
    private Question subQuestion;
    private Map<String, String> values;

    public Category(){}

    public Category(String name, Question leadQuestion, Question subQuestion, Map<String, String> values){
        this.name = name;
        this.leadQuestion = leadQuestion;
        this.subQuestion = subQuestion;
        this.values = values;
    }

    public String getId(){return id;}
    public String getName(){return name;}
    public Question getLeadQuestion(){return leadQuestion;}
    public Question getSubQuestion() {return subQuestion;}
    public Map<String, String> getValues(){return values;}    

    public void setId(String id) {
        this.id = id;
    }
}
