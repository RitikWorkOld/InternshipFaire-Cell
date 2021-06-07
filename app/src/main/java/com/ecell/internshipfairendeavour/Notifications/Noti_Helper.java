package com.ecell.internshipfairendeavour.Notifications;

public class Noti_Helper {

    String title;
    String body;
    String date;
    String id;

    public Noti_Helper() {
    }

    public Noti_Helper(String title, String body, String id,  String date) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}