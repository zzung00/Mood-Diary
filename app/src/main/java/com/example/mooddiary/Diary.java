package com.example.mooddiary;

public class Diary {
    private long id;
    private String content;
    private int mood;
    private String date;

    public Diary(long id, String content, int mood, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.mood = mood;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getMood() {
        return mood;
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

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
