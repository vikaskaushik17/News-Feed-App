package com.example.newsfeed;

public class FeedEntry {

    private String headLine;
    private String place;
    private String description;
    private String time;


    public String getHeadLine() {
        return headLine;
    }

    protected void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getPlace() {
        return place;
    }

    protected void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    protected void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "FeedEntry{" +
                "headLine=" + headLine + '\n' +
                ", place=" + place + '\n' +
                ", description=" + description + '\n' +
                ", time=" + time;
    }
}
