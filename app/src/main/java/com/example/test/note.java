package com.example.test;
import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Map;

public class note {
    private String docId;
    private String title;
    private String desc;
    private  int priority;
    Map<String,Boolean> tags;
    public  note(){

    }
    public  note(String title,String desc,int priority,Map<String,Boolean> tags){
        this.title= title;
        this.desc=desc;
        this.priority = priority;
        this.tags = tags;
    }
    @Exclude
    public String getDocId(){
        return  docId;
    }
    public void setDocId(String docId){
        this.docId= docId;
    }
    public String getDesc(){
        return  desc;
    }
    public String getTitle(){
        return title;
    }
    public int getPriority(){
        return  priority;
    }


    public Map<String,Boolean> getTags() {
        return tags;
    }
}