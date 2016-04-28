package com.example.rkrul.wieeebuddy;

import java.util.Date;

/**
 * Created by rkrul on 4/27/2016.
 */
public class Event {
    private String name;
    private int startTime;
    private int endTime;
    private String date;
    private String description;

    public Event(String name, int startTime, int endTime, String date, String description){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public int getStartTime(){
        return startTime;
    }

    public int getEndTime(){
        return endTime;
    }

    public String getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }
}
