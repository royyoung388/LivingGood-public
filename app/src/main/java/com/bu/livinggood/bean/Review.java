package com.bu.livinggood.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {
    //review with time, score, text comment
    private String comment;
    private float score;
    private String time;
    private String name;

    //private
    public Review (){}

    public Review (String name,float score,String comment){
        this.name = name;
        this.comment = comment;
        this.score = score;
        this.time = setTime();
    }
    private String setTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
    public String getTime() {
        return time;
    }
}
