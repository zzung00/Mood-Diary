package com.example.mooddiary;

public class Todo {
    private long id;
    private String content;
    private boolean done;
    private String date;

    public Todo(long id, String content, boolean done, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean getDone() {
        return done;
    }

    public String getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
