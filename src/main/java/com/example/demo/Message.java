package com.example.demo;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

//private static DateTimeFormatter dtf = DateTimeFormatter.ofpattern("yyyy/MM/dd HH:mm:ss");
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min=4, max=15)
    private String title;

    @NotNull
    @Size(min=3, max=50)
    private String content;

//    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private String postedDate;

    @NotNull
    @Size(min=3, max=15)
    private String postedBy;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //Returns time and date of post
    //Can getter and setter be different types?

    public String getPostedDate() {
        Date today = Calendar.getInstance().getTime();
        String date = today.toString();
        return date;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
