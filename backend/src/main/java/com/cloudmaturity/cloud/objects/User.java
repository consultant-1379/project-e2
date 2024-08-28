package com.cloudmaturity.cloud.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class User {
    @Id
    private String id;

    private String email;
    private List<UserSubmission> userSubmissions;

    public User(){
        this.userSubmissions = new ArrayList<>();
    }

    public User(String email){
        this.email = email;
        this.userSubmissions = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserSubmissions(List<UserSubmission> submissions){this.userSubmissions = submissions;}

    public String getId(){return id;}
    public String getEmail(){return email;}
    public List<UserSubmission> getUserSubmissions(){return userSubmissions;}

    public void addAnswer(UserSubmission submission){this.userSubmissions.add(submission);}

    public void setEmail(String email){this.email = email;}

}
