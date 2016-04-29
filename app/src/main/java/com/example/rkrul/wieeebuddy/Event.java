package com.example.rkrul.wieeebuddy;

import java.util.Date;

/**
 * Created by rkrul on 4/27/2016.
 */
public class Event {
    private String name;
    private String startTime;
    private String endTime;
    private String date;
    private String description;
    private String location;

    public Event(){

    }

    public Event(String name, String startTime, String endTime, String date, String description, String location){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.description = description;
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public String getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation() {
        return location;
    }


    public String toString(){
        return name + "\n" + date + "  " + startTime + " - " + endTime + "\n" + location;
    }
}